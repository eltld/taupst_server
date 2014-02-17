package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.taupst.dao.PullDao;

@Repository("pullDao")
public class PullDaoImpl extends BaseDao implements PullDao {

	@Override
	public boolean update(String pull_id, String user_id, String channel_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("update pull_info set ");
		sql.append("user_id=?,channel_id=? where pull_id=? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(user_id);
		params.add(channel_id);
		params.add(pull_id);
		boolean flag = false;
		try {
			this.jdbcUtils.getConnection();
			flag = this.jdbcUtils.updateByPreparedStatement(sql.toString(), params);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

}
