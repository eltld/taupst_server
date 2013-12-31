package com.taupst.service;

import java.util.List;
import java.util.Map;

import com.taupst.util.Page;

public interface TaskMesService {

	List<Map<String, Object>> getTaskMesList(String task_id, Page page);

}
