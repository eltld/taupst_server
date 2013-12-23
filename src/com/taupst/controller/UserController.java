package com.taupst.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.User;
import com.taupst.service.UserService;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.sync.Sysn;
import com.taupst.util.sync.SysnFac;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class.getName());
	
	@Resource(name = "userService")
	private UserService userService;
	
	private boolean bool;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login(User user, HttpServletResponse response) {

		// 判断该用户在数据库中是否存在
		boolean isExist = userService.isUserExist(user.getStudent_id(),
				user.getSchool());

		Map<String, String> returnMap = null;
		// 该用户在数据库中存在
		if (isExist == true) {
			log.debug("login from database ...");
			returnMap = userService.login(user.getStudent_id(), user.getPwd(),
					user.getSchool());
		} else {
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
					// 该用户在教务系统中的信息保存到数据库中
					log.debug("save user ...");
					if (userService.saveUserInfo(stuInfo)) {
						log.debug("save user succeed");
						returnMap = userService.login(user.getStudent_id(),
								user.getPwd(), user.getSchool());
					} else {
						log.debug("save user failed");
						returnMap = new HashMap<String, String>();
						returnMap.put("isLogined", "false");
					}
				} else {
					log.debug("login from net failed");
					returnMap = new HashMap<String, String>();
					returnMap.put("isLogined", "false");
				}
			} catch (IOException e) {
				log.debug("login from net failed in catch");
				e.printStackTrace();
				returnMap = new HashMap<String, String>();
				returnMap.put("isLogined", "false");
			}

		}
		String jsonString = Object2JsonUtil.Object2Json(returnMap);
		return jsonString;
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(String users_id, HttpServletResponse response) {
		User user = userService.getUserById(users_id);

		String jsonString = Object2JsonUtil.Object2Json(user);

		return jsonString;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public String getUserInfo(User user, HttpServletResponse response) {
		
		
		
		
		
		boolean isUpdata = userService.update(user);
		
		String jsonString = Object2JsonUtil.Object2Json(isUpdata);

		return jsonString;
	}
	
	
	/*//验证邮箱是否合法
		public boolean isEmail(String email){
			if(email == null || email == ""){
				return true;
			} else {
				String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
				bool = Pattern.compile(str).matcher(email).find();
				if(bool == true){
					return true;
				} else {
					return false;
				}
			}
		}

		//判断电话号码是否为true
		public boolean isPhone(String phone){
			if(phone == null || phone == ""){
				return true;
			}else {			
				String str = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
				bool = Pattern.compile(str).matcher(phone).find();
				if(bool == true){
					return true;
				} else {
					return false;
				}
			}
		}*/
	
	
	
}