package com.taupst.dao;

import java.util.List;
import java.util.Map;

public interface NewsDao {

	List<Map<String, Object>> list(String users_id, String news_id, int type);

	Map<String, Object> tmList(String source);

	Map<String, Object> signObject(String source);

	Map<String, Object> aftetTaskInfo(String source);

}
