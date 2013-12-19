package test.util;

import test.service.TaskMesService;
import test.service.TaskService;
import test.service.UserService;
import test.service.impl.TaskMesServiceImpl;
import test.service.impl.TaskServiceImpl;
import test.service.impl.UserServiceImpl;

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
