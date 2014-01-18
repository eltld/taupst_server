package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.RankingDao;
import com.taupst.util.FinalVariable;

@Repository("rankingDao")
public class RankingDaoImpl extends BaseDao implements RankingDao {

	@Override
	public List<Map<String, Object>> list(String school, Integer type) {

		StringBuilder sql = new StringBuilder();

		if (type == 1) {
			sql.append("SELECT ");
			sql.append("r.users_id,u.username,u.photo,u.sex,r.total_praise ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("r.users_id = u.users_id AND u.school=?");
			sql.append("ORDER BY ");
			sql.append("r.total_praise DESC,");
			sql.append("r.last_praise_date ASC ");
			sql.append("LIMIT ? ");

		} else if (type == 2) {
			sql.append("SELECT ");
			sql.append("r.users_id,u.username,u.photo,u.sex,r.month_praise ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("r.users_id = u.users_id AND u.school=?");
			sql.append("ORDER BY ");
			sql.append("r.month_praise DESC,");
			sql.append("r.last_praise_date ASC ");
			sql.append("LIMIT ? ");
		}
		List<Object> params = new ArrayList<Object>();
		params.add(school);
		params.add(FinalVariable.PAGE_SIZE);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql.toString(), params);
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> m = list.get(i);
					m.put("pm", i + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Map<String, Object> getRankByUserId(String users_id, String school,
			int type) {

		StringBuilder sql = new StringBuilder();

		if (type == 1) {
			sql.append("SELECT ");
			sql.append("u.users_id,u.photo,u.username,u.sex,r.total_praise,(");
			sql.append("SELECT ");
			sql.append("count(*) ");
			sql.append("FROM ");
			sql.append("rankinglist r1,users_info u1 ");
			sql.append("WHERE ");
			sql.append("u1.users_id = r1.users_id AND u1.school=? AND (");
			sql.append("r1.total_praise > r.total_praise OR (");
			sql.append("r1.total_praise = r.total_praise AND r1.last_praise_date < r.last_praise_date)) ");
			sql.append(")+1 AS pm ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("u.users_id = r.users_id AND r.users_id=? AND u.school=?");
		}else if(type == 2){
			sql.append("SELECT ");
			sql.append("u.users_id,u.photo,u.username,u.sex,r.month_praise,(");
			sql.append("SELECT ");
			sql.append("count(*) ");
			sql.append("FROM ");
			sql.append("rankinglist r1,users_info u1 ");
			sql.append("WHERE ");
			sql.append("u1.users_id = r1.users_id AND u1.school=? AND (");
			sql.append("r1.month_praise > r.month_praise OR (");
			sql.append("r1.month_praise = r.month_praise AND r1.last_praise_date < r.last_praise_date)) ");
			sql.append(")+1 AS pm ");
			sql.append("FROM ");
			sql.append("rankinglist r,users_info u ");
			sql.append("WHERE ");
			sql.append("u.users_id = r.users_id AND r.users_id=? AND u.school=?");
		}
		List<Object> params = new ArrayList<Object>();
		params.add(school);
		params.add(users_id);
		params.add(school);

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
