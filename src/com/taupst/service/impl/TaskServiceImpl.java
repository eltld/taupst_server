package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taupst.dao.TaskDao;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.service.TaskService;
import com.taupst.util.Page;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Resource(name = "taskDao")
	private TaskDao taskDao;

	@Override
	public List<Map<String, Object>> getTaskList(Page page) {
		return this.taskDao.getTaskList(page);
	}

	@Override
	public List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,int type) {
		return this.taskDao.getTaskList(conditions,type);
	}

}
