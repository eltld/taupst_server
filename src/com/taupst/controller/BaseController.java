package com.taupst.controller;

import javax.annotation.Resource;

import com.taupst.service.SignService;
import com.taupst.service.TaskMesService;
import com.taupst.service.TaskService;
import com.taupst.service.UserService;
import com.taupst.util.MethodUtil;

public abstract class BaseController {
	
	public static MethodUtil util = MethodUtil.getInstance();
	
	@Resource(name="userService")
	protected UserService userService;
	
	@Resource(name="taskService")
	protected TaskService taskService;
	
	@Resource(name="taskMesService")
	protected TaskMesService taskMesService;
	
	@Resource(name="signService")
	protected SignService signService;
}
