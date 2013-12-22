package com.taupst.util;

import com.taupst.dao.TaskDao;
import com.taupst.dao.TaskMesDao;
import com.taupst.dao.UserDao;
import com.taupst.dao.impl.TaskDaoImpl;
import com.taupst.dao.impl.TaskMesDaoImpl;
import com.taupst.dao.impl.UserDaoImpl;

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
