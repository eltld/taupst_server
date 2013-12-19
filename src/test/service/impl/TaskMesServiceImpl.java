package test.service.impl;

import java.util.List;
import java.util.Map;

import test.dao.TaskMesDao;
import test.service.TaskMesService;
import test.util.DaoFactory;
import test.util.Page;

public class TaskMesServiceImpl implements TaskMesService {

	private TaskMesDao taskMesDao = DaoFactory.getTaskMesDao();

	@Override
	public List<Map<String, Object>> getTaskMesList(String task_id,Page page) {
		return this.taskMesDao.getTaskMesList(task_id,page);
	}

}
