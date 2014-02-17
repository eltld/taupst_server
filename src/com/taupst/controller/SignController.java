package com.taupst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.News;
import com.taupst.model.Sign;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;

@Controller
@RequestMapping(value = "/data/sign", produces = "application/json;charset=UTF-8")
public class SignController extends BaseController{

	/**
	 * 
	 * @param sign
	 * @param request
	 * @param response
	 * @return <br>
	 * 		0.表示报名成功 <br>
	 * 		1.表示已经报过名了 <br>
	 * 		2.表示数据库异常<br>
	 * 		3.表示不能报名自己发布的任务<br>
	 * 		4.表示任务已经过期了<br>
	 * 		5.表示任务已经完成了<br>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String signTask(Sign sign, String task_user, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		String sign_id = util.getUUID();
		sign.setSign_id(sign_id);
		sign.setSign_time(util.getDate(0, null));
		sign.setUsers_id((String)user.get("users_id"));
		sign.setIsexe(1);

		News news = new News();
		news.setNews_id(util.getUUID());
		news.setType(3);
		news.setSend((String)user.get("users_id"));
		news.setReceive(task_user);
		news.setContent(user.get("username") + "报名了你的任务!");
		news.setSource(sign_id);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		/* 	0.表示可接任务 
		 * 	1.表示已经接过的任务 
		 * 	2.表示数据库异常或数据库中没有这条任务 
		 * 	3.表示任务是当前用户发布的
		 * 	4.表示任务已经过期了
		 * 	5.表示任务已经完成了
		 */
		int flag = signService.isSign(sign);

		switch (flag) {
		case 0:
			returnMap = signService.save(sign,news);
			break;
		case 1:
			//returnMap = signService.save(sign,news);
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
		/*case 4:// 任务已经过期！
			returnMap.put("state", 4);
			returnMap.put("success", false);
			returnMap.put("msg", "亲，该任务已经过期了！");
			break;*/
		case 5:// 任务已经完成！
			returnMap.put("state", 5);
			returnMap.put("success", false);
			returnMap.put("msg", "亲，该任务已经完成了！");
			break;
		}

		return Object2JsonUtil.Object2Json(returnMap);

	}

	
	/**
	 * 
	 * @param sign
	 * @param request
	 * @param response
	 * @return
	 * 		0.表示可接任务 <br>
	 * 		1.表示已经接过的任务 <br>
	 * 		2.表示数据库异常或数据库中没有这条任务 <br>
	 * 		3.表示任务是当前用户发布的<br>
	 * 		4.表示任务已经过期了<br>
	 * 		5.表示任务已经完成了<br>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/issign", method = RequestMethod.GET)
	@ResponseBody
	public String isSign(Sign sign, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		sign.setUsers_id((String)user.get("users_id"));

		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 0，表示不存在 1,表示存在 2，表示数据库异常,3.表示任务是当前用户发布的
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
		/*case 4:// 表示任务已经过期了！
			returnMap.put("state", 4);
			break;*/
		case 5:// 表示任务已经完成了！
			returnMap.put("state", 5);
			break;
		}

		return Object2JsonUtil.Object2Json(returnMap);

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ce", method = RequestMethod.GET)
	@ResponseBody
	public String SignCE(String sign_id,String reply, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		Map<String, Object> user = signService.getUserBySignId(sign_id);
		Map<String, Object> u = (Map<String, Object>) SessionUtil.getUser(request);
		
		News news = new News();
		
		news.setNews_id(util.getUUID());
		news.setReceive((String)user.get("users_id"));
		news.setSend((String)u.get("users_id"));
		news.setSource(sign_id);
		news.setType(2);
		news.setContent("你在任务中被"+ u.get("username") +"选择为执行者!");
		
		String c_time = util.getDate(0, null);
		
		boolean flag = signService.ChooseExe(sign_id,c_time,reply,news);
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		if(flag == true){
			reMap.put("success", true);
			reMap.put("state", 0);
			reMap.put("msg", "成功！");
		}else{
			reMap.put("success", false);
			reMap.put("state", 1);
			reMap.put("msg", "失败！");
		}
		return Object2JsonUtil.Object2Json(reMap);
	}
	
	@RequestMapping(value = "/signlist", method = RequestMethod.GET)
	@ResponseBody
	public String signList(Sign sign,String type, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String,Object>> list = this.signService.list(sign.getTask_id(),type);
		return Object2JsonUtil.Object2Json(list);
	}
	
}
	
