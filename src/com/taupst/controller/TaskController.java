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

	@RequestMapping(value = "/taskList", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList(String index, TaskQueryConditions conditions,
			HttpServletResponse response) {

		Page page = new Page();
		page.setPageNo(Integer.parseInt(index));
		page.setPageSize(10);

		List<Map<String, Object>> taskList = taskService.getTaskList(
				conditions, page);

		String jsonString = Object2JsonUtil.Object2Json(taskList);

		return jsonString;

	}
	
}