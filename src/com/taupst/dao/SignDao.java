package com.taupst.dao;

import java.util.Map;

import com.taupst.model.Sign;

public interface SignDao {

	Map<String, Object> save(Sign sign);

	int isSign(Sign sign);

}
