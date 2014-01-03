package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.util.Page;

public interface TaskMesDao {

	List<Map<String, Object>> getTaskMesList(String task_id, Page page);

	List<Map<String, Object>> getTMList(String task_id, String tm_id, int type);

}
