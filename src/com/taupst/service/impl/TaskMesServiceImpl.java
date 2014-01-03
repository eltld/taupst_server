package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taupst.dao.TaskMesDao;
import com.taupst.service.TaskMesService;
import com.taupst.util.Page;

@Service("taskMesService")
public class TaskMesServiceImpl implements TaskMesService {
	@Resource(name="taskMesDao")
	private TaskMesDao taskMesDao;

	@Override
	public List<Map<String, Object>> getTaskMesList(String task_id,Page page) {
		return this.taskMesDao.getTaskMesList(task_id,page);
	}

	@Override
	public List<Map<String, Object>> getTMList(String task_id,String tm_id,int type) {
		return this.taskMesDao.getTMList(task_id,tm_id,type);
	}

}
