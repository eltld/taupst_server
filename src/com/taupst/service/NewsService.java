package com.taupst.service;

import java.util.List;
import java.util.Map;

public interface NewsService {

	List<Map<String, Object>> list(String users_id, String news_id, int type);

	Map<String, Object> tmList(String source);

	Map<String, Object> signObject(String source);

	Map<String, Object> signInfo(String source);

	Map<String, Object> aftetTaskInfo(String source);
	
}
