package com.taupst.service;

import java.util.List;
import java.util.Map;

import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.util.Page;

public interface TaskService {

	List<Map<String, Object>> getTaskList(Page page);

	List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,
			Page page);

}
