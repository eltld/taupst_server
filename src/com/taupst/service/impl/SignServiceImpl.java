package com.taupst.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taupst.model.News;
import com.taupst.model.Sign;
import com.taupst.service.SignService;


@Service("signService")
public class SignServiceImpl extends BaseService implements SignService {
	
	@Override
	public Map<String, Object> save(Sign sign, News news) {
		return this.signDao.save(sign,news);
	}

	@Override
	public int isSign(Sign sign) {
		return this.signDao.isSign(sign);
	}

	@Override
	public Map<String, Object> getUserBySignId(String sign_id) {
		return this.signDao.getUserBySignId(sign_id);
	}

	@Override
	public boolean ChooseExe(String sign_id, String c_time, String reply, News news) {
		return this.signDao.ChooseExe(sign_id, c_time, reply, news);
	}

	@Override
	public List<Map<String, Object>> list(String task_id, String type) {
		return this.signDao.list(task_id, type);
	}

	@Override
	public int isSign(String task_id, String users_id) {
		return this.signDao.isSign(task_id, users_id);
	}
	
}
