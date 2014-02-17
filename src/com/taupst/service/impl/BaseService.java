package com.taupst.service.impl;

import javax.annotation.Resource;

import com.taupst.dao.NewsDao;
import com.taupst.dao.PullDao;
import com.taupst.dao.RankingDao;
import com.taupst.dao.SignDao;
import com.taupst.dao.TaskDao;
import com.taupst.dao.TaskMesDao;
import com.taupst.dao.UserDao;

public abstract class BaseService {
	@Resource(name="userDao")
	protected UserDao userDao;
	
	@Resource(name = "taskDao")
	protected TaskDao taskDao;
	
	@Resource(name="taskMesDao")
	protected TaskMesDao taskMesDao;
	
	@Resource(name="signDao")
	protected SignDao signDao;
	
	@Resource(name="rankingDao")
	protected RankingDao rankingDao;

	@Resource(name="newsDao")
	protected NewsDao newsDao;
	
	@Resource(name="pullDao")
	protected PullDao pullDao;
}
