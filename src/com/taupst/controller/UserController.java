package com.taupst.controller;

import java.io.File;
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

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.request.DeleteObjectRequest;
import com.taupst.model.User;
import com.taupst.threadtask.SendNews;
import com.taupst.util.CodeFactory;
import com.taupst.util.CodeUtil;
import com.taupst.util.FinalVariable;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.ReflectUtil;
import com.taupst.util.SessionUtil;
import com.taupst.util.sync.Sysn;
import com.taupst.util.sync.SysnFac;

@Controller
@RequestMapping(value = "/data/user", produces = "application/json;charset=UTF-8")
public class UserController extends BaseController {

	private static Logger log = Logger
			.getLogger(UserController.class.getName());

	private boolean bool;

	@RequestMapping(value = "/issysn", method = RequestMethod.GET)
	@ResponseBody
	public String isSysn(User user, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();

		// 判断该用户在数据库中是否存在
		int isExist = userService.isUserExist(user.getStudent_id(),
				user.getSchool());
		// 0,表示存在 1，表示不存在 ，2，表示数据库异常
		switch (isExist) {
		case 0:
			map.put("state", "0");
			map.put("msg", "存在");
			break;
		case 1:
			String codeUrl = CodeFactory.getCode(Integer.parseInt(user
					.getSchool()));
			if (codeUrl != null) {
				String fileName = user.getSchool() + user.getStudent_id()
						+ ".jpg";
				boolean flag = CodeUtil.downloadImage(codeUrl, fileName,
						request);
				if (flag) {
					map.put("state", "1");
					map.put("msg", "用户不存在,code下载成功");
					map.put("mcode", fileName);
				} else {
					map.put("state", "3");
					map.put("msg", "用户不存在，code下载失败");
				}
			} else {
				map.put("state", "1");
				map.put("msg", "用户不存在,无验证码");
			}
			break;
		case 2:
			map.put("state", "2");
			map.put("msg", "网络异常");
			break;
		}
		return Object2JsonUtil.Object2Json(map);
	}

	/**
	 * 
	 * @param user
	 * @param txtSecretCode
	 * @param cookie
	 * @param request
	 * @param response
	 * @return state <br>
	 *         没有:成功<br>
	 *         1:请登录教务系统完成教师评价后在登录!!<br>
	 *         2:网络异常<br>
	 *         3:验证码不正确！！<br>
	 *         4:用户名不存在或未按照要求参加教学活动！！<br>
	 *         5:密码错误！！<br>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login(User user, String code, String issysn,
			HttpServletRequest request, HttpServletResponse response) {

		// 1表示用户名或密码错误，2表示数据库异常

		// 判断该用户在数据库中是否存在
		/*
		 * int isExist = userService.isUserExist(user.getStudent_id(),
		 * user.getSchool());
		 */

		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 该用户在数据库中存在,即已经同步过
		if (issysn.equals("1")) {

			log.debug("login from database ...");

			returnMap = userService.login(user.getStudent_id(), user.getPwd(),
					user.getSchool());

			Map<String, Object> u = (Map<String, Object>) returnMap.get("user");
			SessionUtil.setUser(request, u);
			returnMap.remove("user");
			// 还未同步过
		} else if (issysn.equals("2")) {

			// 该用户在数据库中不存在，到教务系统中获取用户信息保存到数据库中
			Sysn sysn = SysnFac.getConn(user.getSchool(), user.getStudent_id(),
					user.getPwd(), code);
			Map<String, String> stuInfo = null;
			try {

				log.debug("login from net ...");

				stuInfo = sysn.login(request);
				// 删除验证码，同步完成后
				String fileName = user.getSchool() + user.getStudent_id()
						+ ".jpg";
				File file = new File(request.getSession().getServletContext()
						.getRealPath("/image/code")
						+ "/" + fileName);
				if (file.exists()) {
					file.delete();
				}

				// 该用户在教务系统中登录失败，表示用户名或密码错误。
				if (stuInfo.get("isLogined").equals("true")) {

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

						Map<String, Object> u = (Map<String, Object>) returnMap
								.get("user");
						SessionUtil.setUser(request, u);
						returnMap.remove("user");

					} else {

						log.debug("save user failed");

						returnMap.put("isLogined", "false");
						returnMap.put("state", "2"); // 2 表示数据库异常
					}
				} else {

					log.debug("login from net failed");
					returnMap.putAll(stuInfo);
					// returnMap.put("isLogined", "false");
					// returnMap.put("state", "1"); // 1 用户名密码错误
				}
			} catch (IOException e) {

				log.debug("login from net failed in catch");

				e.printStackTrace();
				returnMap.put("isLogined", "false");
				returnMap.put("state", "2"); // 2 表示数据库异常
			}

		}/*
		 * else if (isExist == 2) { returnMap.put("isLogined", "false");
		 * returnMap.put("state", "2"); // 2 表示数据库异常 }
		 */

		return Object2JsonUtil.Object2Json(returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(User user, HttpServletRequest request,
			HttpServletResponse response) {

		String users_id = user.getUsers_id();
		Map<String, Object> u = new HashMap<String, Object>();
		if (users_id != null && !users_id.equals("")) {
			u = userService.getUserById(users_id);
		} else {
			u = (Map<String, Object>) SessionUtil.getUser(request);
		}

		return Object2JsonUtil.Object2Json(u);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public void updateUserInfo(User user, HttpServletRequest request,
			HttpServletResponse response) {

		ReflectUtil reflectUtil = new ReflectUtil();
		Map<String, Object> map = reflectUtil.getFieldAndValue(user);

		Map<String, Object> u = (Map<String, Object>) SessionUtil
				.getUser(request);

		user.setUsers_id((String) u.get("users_id"));

		// boolean f = userService.update(user,map);

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

		// 修改头像前，先删除旧头像
		String user_photo = user.getPhoto();
		if (user_photo != null) {
			String u_photo = (String) u.get("photo");
			if (u_photo != null && !u_photo.equals("")) {
				if (!u_photo.equals(FinalVariable.PHOTO_BOY)
						&& !u_photo.equals(FinalVariable.PHOTO_GRIL))
					this.deletePhoto("/photo/" + u_photo);
			}
		}

		// 将用户数据插入到数据库中
		if (userService.update(user, map) == true) {
			util.toJsonMsg(response, 0, "修改成功！");

			u = userService.getUserById((String) u.get("users_id"));
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

	}

	@RequestMapping(value = "/testThread", method = RequestMethod.GET)
	@ResponseBody
	public void testThread() {
		for (int i = 0; i < 10; i++) {
			taskExecutor.execute(new SendNews("name_" + (i + 1)));
		}
		System.out.println("do others... ...");
	}

	private boolean deletePhoto(String path) {
		boolean flag = false;
		try {
			log.debug("===========" + UserController.class.getName()
					+ " :delete photo begin ===========");
			BCSCredentials credentials = new BCSCredentials(
					FinalVariable.BAE_USERNAME, FinalVariable.BAE_PASSWORD);
			BaiduBCS baiduBCS = new BaiduBCS(credentials, FinalVariable.BCSHOST);
			DeleteObjectRequest objectRequest = new DeleteObjectRequest(
					FinalVariable.BUCKET, path);
			baiduBCS.deleteObject(objectRequest);
			log.debug("===========" + UserController.class.getName()
					+ " :delete photo succeed ===========");
			flag = true;
		} catch (Exception e) {
			log.debug("===========" + UserController.class.getName() + " :"
					+ e.getMessage() + "===========");
			flag = false;
		}
		return flag;
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

		// ReflectUtil reflectUtil = new ReflectUtil();
		// User user = new User();
		// user.setUsername("hd");
		// user.setSchool("00001");
		// Map<String, Object> map = reflectUtil.getFieldAndValue(user);

		// String[] fileName = (String[]) map.get("fileName");
		// Object[] useFileValue = (Object[]) map.get("fileValue");

		//UserController userController = new UserController();

		//System.out.println(userController.util.getUUID());

		/*
		 * long l_1 = System.currentTimeMillis();
		 * 
		 * for (int i = 0; i < 100000; i++) {
		 * 
		 * } long l_2 = System.currentTimeMillis();
		 * 
		 * 
		 * System.out.println(l_1); System.out.println(l_2);
		 */
		// String str = userController.util.getDate(4, null, 1389589100419l);

		// System.out.println(str);
	}

}