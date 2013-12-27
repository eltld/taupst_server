package com.taupst.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.service.TaskService;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.Page;

@Controller
@RequestMapping(value = "/task", produces = "application/json;charset=UTF-8")
public class TaskController {

	@Resource(name = "taskService")
	private TaskService taskService;

	@RequestMapping(value = "/taskList2Up", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Up(TaskQueryConditions conditions,
			HttpServletResponse response) {

		int type = 2;//表示向上拉，获取比当前id时间更旧的
		List<Map<String, Object>> taskList = taskService.getTaskList(conditions,type);

		String jsonString = Object2JsonUtil.Object2Json(taskList);

		return jsonString;

	}
	
	@RequestMapping(value = "/taskList2Down", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Down(TaskQueryConditions conditions,
			HttpServletResponse response) {
		String task_id = conditions.getTask_id();
		int type = 0;//取出最新的20条
		if(task_id != null && !task_id.equals("")){
			type = 1;//表示向下拉，获取比当前id时间更新的
		}
		List<Map<String, Object>> taskList = taskService.getTaskList(conditions,type);
		
		String jsonString = Object2JsonUtil.Object2Json(taskList);
		
		return jsonString;
		
	}
}