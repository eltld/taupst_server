package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.util.Page;

public interface TaskMesDao {

	List<Map<String, Object>> getTaskMesList(String task_id, Page page);

}
