package test.util;

import test.dao.TaskDao;
import test.dao.TaskMesDao;
import test.dao.UserDao;
import test.dao.impl.TaskDaoImpl;
import test.dao.impl.TaskMesDaoImpl;
import test.dao.impl.UserDaoImpl;

public class DaoFactory {
	private static UserDao userDao = new UserDaoImpl();

	private static TaskDao taskDao = new TaskDaoImpl();

	private static TaskMesDao taskMesDao = new TaskMesDaoImpl();

	public static TaskDao getTaskDao() {
		return taskDao;
	}

	public static TaskMesDao getTaskMesDao() {
		return taskMesDao;
	}

	public static UserDao getUserDao() {
		return userDao;
	}

}
