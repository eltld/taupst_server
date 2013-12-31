package com.taupst.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.Sign;
import com.taupst.model.User;
import com.taupst.service.SignService;
import com.taupst.util.MethodUtil;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;


@Controller
@RequestMapping(value = "/data/sign", produces = "application/json;charset=UTF-8")
public class SignController {
	
	
	private static MethodUtil util = new MethodUtil();
	
	@Resource(name="signService")
	private SignService signService;
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String signTask(Sign sign, HttpServletRequest request,
			HttpServletResponse response) {

		User user = (User) SessionUtil.getUser(request);

		sign.setSign_id(util.getUUID());
		sign.setSign_time(util.getDate(0, null));
		sign.setUsers_id(user.getUsers_id());
		sign.setIsexe(1);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 0，表示不存在  1,表示存在  2，表示数据库异常,3.当前用户发布的任务！
		int flag = signService.isSign(sign);
		
		switch (flag) {
		case 0:
			returnMap = signService.save(sign);
			break;
		case 1:
			returnMap.put("state", 1);
			returnMap.put("success", false);
			returnMap.put("msg", "亲，你已经报过名了！");
			break;
		case 2:
			returnMap.put("state", 2);
			returnMap.put("success", false);
			returnMap.put("msg", "亲，网络超时！");
			break;
		case 3:// 当前用户发布的任务！
			returnMap.put("state", 3);
			returnMap.put("success", false);
			returnMap.put("msg", "亲，不能报名自己发布的任务！");
			break;
		}
		
		return Object2JsonUtil.Object2Json(returnMap);

	}
	
	@RequestMapping(value = "/issign", method = RequestMethod.GET)
	@ResponseBody
	public String isSign(Sign sign, HttpServletRequest request,
			HttpServletResponse response) {

		User user = (User) SessionUtil.getUser(request);

		sign.setUsers_id(user.getUsers_id());

		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// 0，表示不存在  1,表示存在  2，表示数据库异常,3.表示任务是当前用户发布的
		int flag = signService.isSign(sign);
		
		switch (flag) {
		case 0:// 还没报名
			returnMap.put("state", 0);
			break;
		case 1:// 已经报过名
			returnMap.put("state", 1);
			break;
		case 2:// 网络超时！
			returnMap.put("state", 2);
			break;
		case 3:// 当前用户发布的任务！
			returnMap.put("state", 3);
			break;
		}
		
		return Object2JsonUtil.Object2Json(returnMap);

	}
	
}
