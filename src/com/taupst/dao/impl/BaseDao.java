package com.taupst.dao.impl;

import javax.annotation.Resource;

import com.taupst.util.JdbcUtils;

public abstract class BaseDao {
	
	@Resource(name = "jdbcUtils")
	protected JdbcUtils jdbcUtils;
	
}
