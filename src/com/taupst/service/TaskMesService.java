package com.taupst.service;

import java.util.List;
import java.util.Map;

public interface TaskMesService {

	List<Map<String, Object>> getTMList(String task_id, String tm_id, int type);

}
