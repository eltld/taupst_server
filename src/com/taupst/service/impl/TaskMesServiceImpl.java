package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.service.TaskMesService;

@Service("taskMesService")
public class TaskMesServiceImpl extends BaseService implements TaskMesService {

	@Override
	public List<Map<String, Object>> getTMList(String task_id,String tm_id,int type) {
		return this.taskMesDao.getTMList(task_id,tm_id,type);
	}

}
