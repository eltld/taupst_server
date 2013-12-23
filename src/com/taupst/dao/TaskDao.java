package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.util.Page;

public interface TaskDao {

	List<Map<String, Object>> getTaskList(Page page);

	List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,
			Page page);

}
