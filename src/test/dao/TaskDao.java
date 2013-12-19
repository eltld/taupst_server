package test.dao;

import java.util.List;
import java.util.Map;

import test.model.TaskQueryConditions;
import test.util.Page;

public interface TaskDao {

	List<Map<String, Object>> getTaskList(Page page);

	List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,
			Page page);

}
