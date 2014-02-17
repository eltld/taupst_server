package com.taupst.dao;

import java.util.List;
import java.util.Map;

import com.taupst.model.News;
import com.taupst.model.Sign;

public interface SignDao {

	Map<String, Object> save(Sign sign, News news);

	int isSign(Sign sign);

	int isSign(String task_id, String users_id);

	Map<String, Object> getUserBySignId(String sign_id);

	boolean ChooseExe(String sign_id, String c_time, String reply, News news);

	Map<String, Object> signInfo(String source);

	List<Map<String, Object>> list(String task_id, String type);

}
