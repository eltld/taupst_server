package com.taupst.util;

import com.taupst.service.TaskMesService;
import com.taupst.service.TaskService;
import com.taupst.service.UserService;
import com.taupst.service.impl.TaskMesServiceImpl;
import com.taupst.service.impl.TaskServiceImpl;
import com.taupst.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static UserService userService = new UserServiceImpl();

	private static TaskService taskService = new TaskServiceImpl();

	private static TaskMesService taskMesService = new TaskMesServiceImpl();

	public static UserService getUserService() {
		return userService;
	}

	public static TaskService getTaskService() {
		return taskService;
	}

	public static TaskMesService getTaskMesService() {
		return taskMesService;
	}

}
