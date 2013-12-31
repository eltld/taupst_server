package com.taupst.service;

import java.util.List;
import java.util.Map;

import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;

public interface TaskService {


	List<Map<String, Object>> getTaskList(TaskQueryConditions conditions, int type);

	Map<String, Object> getTaskById(String task_id);

	Map<String, Object> save(Task task);


}
