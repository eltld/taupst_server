package com.taupst.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.util.Object2JsonUtil;
import com.taupst.util.SessionUtil;

@Controller
@RequestMapping(value = "/data/task", produces = "application/json;charset=UTF-8")
public class TaskController extends BaseController{

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/taskList2Up", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Up(TaskQueryConditions conditions,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		conditions.setSchool((String)user.get("school"));

		int type = 2;// 表示向上拉，获取比当前id时间更旧的

		return Object2JsonUtil.Object2Json(taskService.getTaskList(conditions,
				type));

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/taskList2Down", method = RequestMethod.GET)
	@ResponseBody
	public String getTaskList2Down(TaskQueryConditions conditions,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		conditions.setSchool((String)user.get("school"));

		String task_id = conditions.getTask_id();
		int type = 0;// 取出最新的20条
		if (task_id != null && !task_id.equals("")) {
			type = 1;// 表示向下拉，获取比当前id时间更新的
		}

		return Object2JsonUtil.Object2Json(taskService.getTaskList(conditions,
				type));

	}
	
	/**
	 * 
	 * @param task
	 * @param request
	 * @param response
	 * @return  
	 * 		0.表示任务发布成功
	 * 		1.表示任务发布失败，存在错误数据
	 * 		2.表示网络超时
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public String saveTask(Task task, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		Map<String, Object> user = (Map<String, Object>) SessionUtil.getUser(request);

		task.setUser_id((String)user.get("users_id"));
		task.setTask_id(util.getUUID());
		task.setTask_state(1);
		task.setRelease_time(util.getDate(0, null));
		
		
		String title = task.getTitle();
		String content = task.getContent();
		Integer level = task.getTask_level();
		String end_of_time = task.getEnd_of_time();
		String rewards = task.getRewards();
		
		if(title == null || title.trim().equals("")){
			returnMap.put("msg", "亲，标题还没填写！");
			returnMap.put("state", 1);
			returnMap.put("success", false);
			return Object2JsonUtil.Object2Json(returnMap);
		}else if(content == null || content.trim().equals("")){
			returnMap.put("msg", "亲，内容还没填写！");
			returnMap.put("state", 1);
			returnMap.put("success", false);
			return Object2JsonUtil.Object2Json(returnMap);
		}else if(end_of_time == null || end_of_time.trim().equals("")){
			returnMap.put("msg", "亲，任务截止时间还没选！");
			returnMap.put("state", 1);
			returnMap.put("success", false);
			return Object2JsonUtil.Object2Json(returnMap);
		}else if(rewards == null || rewards.trim().equals("")){
			returnMap.put("msg", "亲，报酬还没填！");
			returnMap.put("state", 1);
			returnMap.put("success", false);
			return Object2JsonUtil.Object2Json(returnMap);
		}else if(level == null){
			returnMap.put("msg", "亲，任务等级还没填！");
			returnMap.put("state", 1);
			returnMap.put("success", false);
			return Object2JsonUtil.Object2Json(returnMap);
		}
		
		returnMap = taskService.save(task);
		
		return Object2JsonUtil.Object2Json(returnMap);

	}

}