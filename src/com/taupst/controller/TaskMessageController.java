package com.taupst.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.TaskMessage;
import com.taupst.util.Object2JsonUtil;

@Controller
@RequestMapping(value = "/data/taskmsg", produces = "application/json;charset=UTF-8")
public class TaskMessageController extends BaseController{


	@RequestMapping(value = "/taskMsgList2Down", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Down(TaskMessage tm, HttpServletRequest request,
			HttpServletResponse response) {

		//User user = (User) SessionUtil.getUser(request);

		String tm_id = tm.getMessage_id();
		String task_id = tm.getTask_id();

		int type = 0;// 取出最新的20条
		if (tm_id != null && !tm_id.equals("")) {
			type = 1;// 表示向下拉，获取比当前id时间更新的
		}

		List<Map<String, Object>> tmList = taskMesService.getTMList(task_id,tm_id,type);
		
		return Object2JsonUtil.Object2Json(tmList);

	}
	
	@RequestMapping(value = "/taskMsgList2Up", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Up(TaskMessage tm, HttpServletRequest request,
			HttpServletResponse response) {

		//User user = (User) SessionUtil.getUser(request);

		String tm_id = tm.getMessage_id();
		String task_id = tm.getTask_id();

		int type = 0;// 取出最新的20条
		if (tm_id != null && !tm_id.equals("")) {
			type = 2;// 表示向下拉，获取比当前id时间更新的
		}

		List<Map<String, Object>> tmList = taskMesService.getTMList(task_id,tm_id,type);
		
		return Object2JsonUtil.Object2Json(tmList);

	}

}