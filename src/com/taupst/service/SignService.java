package com.taupst.service;

import java.util.List;
import java.util.Map;

import com.taupst.model.News;
import com.taupst.model.Sign;

public interface SignService {

	Map<String, Object> save(Sign sign, News news);

	int isSign(Sign sign);

	int isSign(String task_id, String users_id);

	Map<String, Object> getUserBySignId(String sign_id);

	boolean ChooseExe(String sign_id, String c_time, String reply, News news);

	List<Map<String, Object>> list(String task_id, String type);

}
