package com.taupst.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.service.TaskMesService;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.Page;

@Controller
@RequestMapping(value = "/taskmsg", produces = "application/json;charset=UTF-8")
public class TaskMessageController {

	@Resource(name = "taskMesService")
	private TaskMesService taskMesService;
	
	@RequestMapping(value = "/taskMsgList", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList(String index, String task_id,
			HttpServletResponse response) {
		
		Page page = new Page();
		page.setPageNo(Integer.parseInt(index));
		page.setPageSize(10);
		
		List<Map<String,Object>> tm = taskMesService.getTaskMesList(task_id,page);
		
		String jsonString = Object2JsonUtil.Object2Json(tm);
		
		return jsonString;
		
	}

}