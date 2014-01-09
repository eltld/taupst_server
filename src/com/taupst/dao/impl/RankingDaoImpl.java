package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.RankingDao;
import com.taupst.util.FinalVariable;

@Repository("rankingDao")
public class RankingDaoImpl extends BaseDao implements RankingDao {

	@Override
	public List<Map<String, Object>> list(String school,Integer type) {

		StringBuilder sql = new StringBuilder();

		if (type == 1) {
			sql.append("SELECT ");
			sql.append("r.ranking_id,u.users_id,u.username,r.total_praise ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("r.users_id = u.users_id AND u.school=?");
			sql.append("ORDER BY ");
			sql.append("r.total_praise DESC ");
			sql.append("LIMIT ? ");
		}else if (type == 2) {
			sql.append("SELECT ");
			sql.append("r.ranking_id,u.users_id,u.username,r.month_praise ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("r.users_id = u.users_id AND u.school=?");
			sql.append("ORDER BY ");
			sql.append("r.month_praise DESC ");
			sql.append("LIMIT ? ");
		}
		List<Object> params = new ArrayList<Object>();
		params.add(school);
		params.add(FinalVariable.PAGE_SIZE);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
