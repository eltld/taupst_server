package test.dao;

import java.util.List;
import java.util.Map;

import test.util.Page;

public interface TaskMesDao {

	List<Map<String, Object>> getTaskMesList(String task_id, Page page);

}
