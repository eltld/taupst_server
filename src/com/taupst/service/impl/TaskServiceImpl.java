package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taupst.dao.TaskDao;
import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Resource(name = "taskDao")
	private TaskDao taskDao;


	@Override
	public List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,int type) {
		return this.taskDao.getTaskList(conditions,type);
	}

	@Override
	public Map<String, Object> getTaskById(String task_id) {
		return this.taskDao.getTaskById(task_id);
	}

	@Override
	public Map<String, Object> save(Task task) {
		return this.taskDao.save(task);
	}


}
