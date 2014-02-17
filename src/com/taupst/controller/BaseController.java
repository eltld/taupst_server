package com.taupst.controller;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.taupst.service.NewsService;
import com.taupst.service.PullService;
import com.taupst.service.RankingService;
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
	
	@Resource(name="rankingService")
	protected RankingService rankingService;
	
	@Resource(name="newsService")
	protected NewsService newsService;
	
	@Resource(name="pullService")
	protected PullService pullService;
	
	@Resource(name="taskExecutor")
	protected ThreadPoolTaskExecutor taskExecutor;
}
