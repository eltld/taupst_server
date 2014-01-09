package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.model.TaskMessage;

public interface TaskMesDao {

	List<Map<String, Object>> getTMList(String task_id, String tm_id, int type);

	int save(TaskMessage tm);

}
