package com.taupst.service;

import java.util.Map;

import com.taupst.model.Sign;

public interface SignService {

	Map<String, Object> save(Sign sign);

	int isSign(Sign sign);

}
