package com.taupst.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.News;
import com.taupst.model.TaskMessage;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;

@Controller
@RequestMapping(value = "/data/taskmsg", produces = "application/json;charset=UTF-8")
public class TaskMessageController extends BaseController {

	@RequestMapping(value = "/taskMsgList2Down", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskMesList2Down(TaskMessage tm,
			HttpServletResponse response) {

		String tm_id = tm.getMessage_id();
		String task_id = tm.getTask_id();

		int type = 0;// 取出最新的20条
		if (tm_id != null && !tm_id.equals("")) {
			type = 1;// 表示向下拉，获取比当前id时间更新的
		}

		return Object2JsonUtil.Object2Json(taskMesService.getTMList(task_id,
				tm_id, type));

	}

	@RequestMapping(value = "/taskMsgList2Up", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskMesList2Up(TaskMessage tm, HttpServletResponse response) {

		String tm_id = tm.getMessage_id();
		String task_id = tm.getTask_id();

		int type = 0;// 取出最新的20条
		if (tm_id != null && !tm_id.equals("")) {
			type = 2;// 表示向下拉，获取比当前id时间更新的
		}

		return Object2JsonUtil.Object2Json(taskMesService.getTMList(task_id,
				tm_id, type));

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskMesSave(TaskMessage tm, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		String message_id = util.getUUID();
		String users_id = (String) user.get("users_id");
		String to_user = tm.getTo_user();
		String root_id = tm.getRoot_id();
		String message_time = util.getDate(0, null);
		
		News news = new News();
		news.setNews_id(util.getUUID());
		news.setType(1);
		news.setReceive(to_user);
		news.setSend(users_id);
		news.setSource(message_id);
		news.setContent(user.get("username") + "回复了你的留言!");
		
		// 判断是否根留言,是的话，to_user和root_id都赋值为"-1"
		if (root_id == null) {
			//to_user = "-1";
			root_id = "-1";
			tm.setTo_user(to_user);
			tm.setRoot_id(root_id);
			news.setContent(user.get("username") + "给你的任务留言了!");
		}

		tm.setMessage_id(message_id);
		tm.setUsers_id(users_id);
		tm.setMessage_time(message_time);
		int flag = taskMesService.save(tm,news);

		Map<String, Object> map = new HashMap<String, Object>();

		if (flag == 0) {
			map.put("state", 0);
			map.put("success", true);
			map.put("msg", "发布成功！");
			map.put("message_id", message_id);
		} else {
			map.put("state", 1);
			map.put("success", false);
			map.put("msg", "发布失败！");
		}
		return Object2JsonUtil.Object2Json(map);
	}

}