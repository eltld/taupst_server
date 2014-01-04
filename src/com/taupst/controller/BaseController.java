package com.taupst.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taupst.service.SignService;
import com.taupst.service.TaskMesService;
import com.taupst.service.TaskService;
import com.taupst.service.UserService;
import com.taupst.util.MethodUtil;


@Controller
@RequestMapping(value = "/data/sign", produces = "application/json;charset=UTF-8")
public abstract class BaseController {
	
	protected static MethodUtil util = MethodUtil.getInstance();
	
	@Resource(name="userService")
	protected UserService userService;
	
	@Resource(name="taskService")
	protected TaskService taskService;
	
	@Resource(name="taskMesService")
	protected TaskMesService taskMesService;
	
	@Resource(name="signService")
	protected SignService signService;
}
