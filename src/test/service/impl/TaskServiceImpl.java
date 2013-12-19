package test.service.impl;

import java.util.List;
import java.util.Map;

import test.dao.TaskDao;
import test.model.TaskQueryConditions;
import test.service.TaskService;
import test.util.DaoFactory;
import test.util.Page;

public class TaskServiceImpl implements TaskService{
	
	private TaskDao taskDao = DaoFactory.getTaskDao();

	@Override
	public List<Map<String, Object>> getTaskList(Page page) {
		return this.taskDao.getTaskList(page);
	}

	@Override
	public List<Map<String, Object>> getTaskList(
			TaskQueryConditions conditions, Page page) {
		return this.taskDao.getTaskList(conditions, page);
	}
	
}
