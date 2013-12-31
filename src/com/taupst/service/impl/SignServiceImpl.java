package com.taupst.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taupst.dao.SignDao;
import com.taupst.model.Sign;
import com.taupst.service.SignService;


@Service("signService")
public class SignServiceImpl implements SignService {
	
	@Resource(name="signDao")
	private SignDao signDao;

	@Override
	public Map<String, Object> save(Sign sign) {
		return this.signDao.save(sign);
	}

	@Override
	public int isSign(Sign sign) {
		return this.signDao.isSign(sign);
	}
	
}
