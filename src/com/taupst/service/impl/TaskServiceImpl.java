package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.service.TaskService;

@Service("taskService")
public class TaskServiceImpl extends BaseService implements TaskService {

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

	@Override
	public boolean updateTaskState() {
		return this.taskDao.updateTaskState();
	}

	@Override
	public Map<String, Object> getTaskInfoById(String task_id) {
		return this.taskDao.getTaskInfoById(task_id);
	}

	@Override
	public boolean finishTask(Map<String, Object> user, String task_id,
			String sign_id, String prise, String users_id, String msg) {
		return this.taskDao.finishTask(user, task_id, sign_id, prise, users_id, msg);
	}

	@Override
	public boolean finishTask(String task_id, String f_time) {
		return this.taskDao.finishTask(task_id, f_time);
	}


}
