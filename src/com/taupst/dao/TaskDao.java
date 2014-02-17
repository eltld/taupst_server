package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;

public interface TaskDao {

	List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,
			int type);

	Map<String, Object> getTaskById(String task_id);

	Map<String, Object> save(Task task);

	boolean updateTaskState();

	Map<String, Object> getTaskInfoById(String task_id);

	boolean finishTask(Map<String, Object> user, String task_id, String sign_id, String prise,
			String users_id, String msg);

	boolean finishTask(String task_id, String f_time);

}
