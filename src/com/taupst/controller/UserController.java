package com.taupst.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.User;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.ReflectUtil;
import com.taupst.util.SessionUtil;
import com.taupst.util.sync.Sysn;
import com.taupst.util.sync.SysnFac;

@Controller
@RequestMapping(value = "/data/user", produces = "application/json;charset=UTF-8")
public class UserController extends BaseController{

	private static Logger log = Logger
			.getLogger(UserController.class.getName());

	private boolean bool;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login(User user, HttpServletRequest request,
			HttpServletResponse response) {

		// 1表示用户名或密码错误，2表示数据库异常

		// 判断该用户在数据库中是否存在
		int isExist = userService.isUserExist(user.getStudent_id(),
				user.getSchool());

		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 该用户在数据库中存在
		if (isExist == 0) {

			log.debug("login from database ...");

			returnMap = userService.login(user.getStudent_id(), user.getPwd(),
					user.getSchool());

			User u = (User) returnMap.get("user");
			SessionUtil.setUser(request, u);
			returnMap.remove("user");

		} else if (isExist == 1) {
			// 该用户在数据库中不存在，到教务系统中获取用户信息保存到数据库中
			Sysn sysn = SysnFac.getConn(user.getSchool(), user.getStudent_id(),
					user.getPwd());
			Map<String, String> stuInfo = null;
			try {

				log.debug("login from net ...");

				stuInfo = sysn.login();
				// 该用户在教务系统中登录失败，表示用户名或密码错误。
				if (stuInfo.get("isLoginSuccess").equals("true")) {

					log.debug("login from net succeed");

					stuInfo.put("pwd", user.getPwd());
					stuInfo.put("school", user.getSchool());
					stuInfo.put("users_id", util.getUUID());
					// 该用户在教务系统中的信息保存到数据库中

					log.debug("save user ...");

					if (userService.saveUserInfo(stuInfo)) {

						log.debug("save user succeed");

						returnMap = userService.login(user.getStudent_id(),
								user.getPwd(), user.getSchool());

						User u = (User) returnMap.get("user");
						SessionUtil.setUser(request, u);
						returnMap.remove("user");

					} else {

						log.debug("save user failed");

						returnMap.put("isLogined", "false");
						returnMap.put("state", "2"); // 2 表示数据库异常
					}
				} else {

					log.debug("login from net failed");

					returnMap.put("isLogined", "false");
					returnMap.put("state", "1"); // 1 用户名密码错误
				}
			} catch (IOException e) {

				log.debug("login from net failed in catch");

				e.printStackTrace();
				returnMap.put("isLogined", "false");
				returnMap.put("state", "2"); // 2 表示数据库异常
			}

		} else if (isExist == 2) {
			returnMap.put("isLogined", "false");
			returnMap.put("state", "2"); // 2 表示数据库异常
		}

		return Object2JsonUtil.Object2Json(returnMap);
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(User user, HttpServletRequest request,
			HttpServletResponse response) {

		String users_id = user.getUsers_id();
		
		if (users_id != null && !users_id.equals("")) {
			user = userService.getUserById(users_id);
		} else {
			user = (User) SessionUtil.getUser(request);
		}

		return Object2JsonUtil.Object2Json(user);
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	@ResponseBody
	public void Exit(HttpServletRequest request, HttpServletResponse response) {

		try {
			SessionUtil.removeSessionAll(request.getSession());
			util.toJsonMsg(response, 0, null);
		} catch (Exception e) {
			util.toJsonMsg(response, 1, null);
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public void updateUserInfo(User user, HttpServletRequest request,
			HttpServletResponse response) {
		
		ReflectUtil reflectUtil = new ReflectUtil();
		Map<String, Object> map = reflectUtil.getFieldAndValue(user);
		
		User u = (User) SessionUtil.getUser(request);
		
		user.setUsers_id(u.getUsers_id());
		
		//boolean f = userService.update(user,map);
		
		// 判断昵称是否合法
		if (this.isUserName(user.getUsername()) != true) {
			util.toJsonMsg(response, 1, "昵称错误，请重新填写！");
			return;
		}
		// 判断电话号码是否合法
		if (this.isPhone(user.getPhone()) != true) {
			util.toJsonMsg(response, 1, "电话号码不正确！");
			return;
		}
		// 判断qq号码是否合法
		if (this.isQq(user.getQq()) != true) {
			util.toJsonMsg(response, 1, "QQ号码不正确！");
			return;
		}
		// 判断邮箱是否合法
		if (this.isEmail(user.getEmail()) != true) {
			util.toJsonMsg(response, 1, "邮箱格式不正确！");
			return;
		}

		

		// 将用户数据插入到数据库中
		if (userService.update(user,map) == true) {
			util.toJsonMsg(response, 0, "修改成功！");
			u = userService.getUserById(u.getUsers_id());
			SessionUtil.setUser(request, u);
			return;
		} else {
			util.toJsonMsg(response, 2, "网络超时！");
			return;
		}

	}

	@RequestMapping(value = "/updateSignature", method = RequestMethod.GET)
	@ResponseBody
	public void getUserSignature(User user, HttpServletRequest request,
			HttpServletResponse response) {

		User u = (User) SessionUtil.getUser(request);

		u.setSignature(user.getSignature());

		// 将用户数据插入到数据库中
		if (userService.updateSignature(u) == true) {
			util.toJsonMsg(response, 0, "修改成功！");
			SessionUtil.setUser(request, u);
			return;
		} else {
			util.toJsonMsg(response, 1, "网络超时！");
			return;
		}
	}

	// 验证邮箱是否合法
	public boolean isEmail(String email) {
		if (email == null || email == "") {
			return true;
		} else {
			String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
			bool = Pattern.compile(str).matcher(email).find();
			if (bool == true) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 判断电话号码是否为true
	public boolean isPhone(String phone) {
		if (phone == null || phone == "") {
			return true;
		} else {
			String str = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
			bool = Pattern.compile(str).matcher(phone).find();
			if (bool == true) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 判断QQ号码是否为true
	public boolean isQq(String qq) {
		if (qq == null || qq == "") {
			return true;
		} else {
			String str = "^\\d{6,10}$";
			bool = Pattern.compile(str).matcher(qq).find();
			if (bool == true) {
				return true;
			} else {
				return false;
			}
		}
	}

	// 判断username是否为true
	public boolean isUserName(String username) {
		// 允许出现的字符
		String str = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(\\&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§";

		if (username == null || username.trim().equals("")) {
			return true;
		} else {
			// 判断是否包含特殊字符 true是包含，false不包含
			bool = Pattern.compile(str).matcher(username).find();
			if (bool == true) {
				return false;
			} else {
				if (username.length() > 0 && username.length() < 20) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public static void main(String[] args) {
		
		ReflectUtil reflectUtil = new ReflectUtil();
		User user = new User();
		user.setUsername("hd");
		user.setSchool("00001");
		Map<String, Object> map = reflectUtil.getFieldAndValue(user);
		
		//String[] fileName = (String[]) map.get("fileName");
		//Object[] useFileValue = (Object[]) map.get("fileValue");
		
		System.out.println(map);
	}

	
}