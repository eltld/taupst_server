package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.NewsDao;
import com.taupst.util.FinalVariable;
import com.taupst.util.MethodUtil;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDao implements NewsDao {

	@Override
	public List<Map<String, Object>> list(String users_id, String news_id,
			int type) {
		MethodUtil util = MethodUtil.getInstance();

		List<Map<String, Object>> newsMapList = new ArrayList<Map<String, Object>>();

		if (news_id != null && !news_id.equals("")) {
			news_id = news_id.substring(0, 17);
		}

		List<Object> params = new ArrayList<Object>();

		params.add(users_id);
		params.add(users_id);

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("u.photo, u.username,u.sex,u.department,u.special,u.classname,u.grade, n.content, n.news_id, n.send, n.type, n.source ");
		sql.append("FROM ");
		sql.append("users_info u, news n ");
		sql.append("WHERE ");
		sql.append("u.users_id = n.send AND n.receive=? AND n.send<>?");
		if (type == 1) {
			sql.append("AND SUBSTRING(n.news_id FROM 1 FOR 17) > ? ");
			params.add(news_id);
		} else if (type == 2) {
			sql.append("AND SUBSTRING(n.news_id FROM 1 FOR 17) < ? ");
			params.add(news_id);
		}
		sql.append("ORDER BY news_id DESC ");
		sql.append("LIMIT ? ");
		params.add(FinalVariable.PAGE_SIZE);

		try {
			jdbcUtils.getConnection();
			newsMapList = jdbcUtils.findMoreResult(sql.toString(), params);
			for (Map<String, Object> newsMap : newsMapList) {
				String n_id = (String) newsMap.get("news_id");
				n_id = n_id.substring(0, 17);
				String time = util.changeDateFormat(n_id);
				newsMap.put("time", time);
			}

			// for (Map<String, Object> newsMap : newsMapList) {
			// Integer tp = (Integer) newsMap.get("type");
			// String source = (String) newsMap.get("source");
			// List<Object> params_list = new ArrayList<Object>();
			// StringBuilder sql_list = new StringBuilder();
			// switch (tp) {
			// case 1:
			// sql_list.append("SELECT ");
			// sql_list.append("tm.task_id,tm.message_content,tm.message_time ");
			// sql_list.append("FROM ");
			// sql_list.append("task_message tm ");
			// sql_list.append("WHERE ");
			// sql_list.append("tm.message_id=? ");
			// break;
			// case 2:
			//
			// break;
			// case 3:
			// sql_list.append("SELECT ");
			// sql_list.append("s.task_id,s.sign_time,s.open_mes,s.message ");
			// sql_list.append("FROM ");
			// sql_list.append("sign s ");
			// sql_list.append("WHERE ");
			// sql_list.append("s.sign_id=? ");
			// break;
			// }
			// params_list.add(source);
			// Map<String, Object> news =
			// jdbcUtils.findSimpleResult(sql_list.toString(), params_list);
			// newsMap.put("detailed", news);
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsMapList;
	}

	@Override
	public Map<String, Object> tmList(String source) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("tm.message_id,tm.task_id,tm.users_id AS tm_users_id,tm.message_content,tm.message_time,tm.root_id,");
		sql.append("t.title,u.username,t.users_id AS t_users_id ");
		sql.append("FROM ");
		sql.append("task_message tm,task t,users_info u ");
		sql.append("WHERE ");
		sql.append("tm.message_id=? ");
		sql.append("AND tm.task_id = t.task_id AND t.users_id = u.users_id ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(source);
		
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql.toString(), params);
			
			List<Map<String, Object>> tmList = null;
			String root_id = (String) map.get("root_id");
			String message_id = (String) map.get("message_id");
			if(!root_id.equals("-1")){
				StringBuilder sql_list = new StringBuilder();
				List<Object> params_list = new ArrayList<Object>();
				sql_list.append("SELECT ");
				sql_list.append("t1.photo,t1.username AS reply,t1.users_id AS reply_id,t1.message_id,t1.task_id,");
				sql_list.append("t1.message_content,t1.message_time,u.username AS replied,u.users_id AS replied_id ");
				sql_list.append("FROM (");
				sql_list.append("SELECT ");
				sql_list.append("u.photo,u.username,tm.users_id,tm.message_id,");
				sql_list.append("tm.task_id,tm.message_content,tm.message_time,tm.to_user ");
				sql_list.append("FROM ");
				sql_list.append("users_info u,task_message tm ");
				sql_list.append("WHERE ");
				sql_list.append("u.users_id = tm.users_id ");
				sql_list.append("AND tm.task_id=? AND (tm.root_id=? OR tm.message_id=?) ");
				sql_list.append("ORDER BY ");
				sql_list.append("tm.message_time ASC ) t1,users_info u ");
				sql_list.append("WHERE ");
				sql_list.append("t1.to_user = u.users_id ");
				sql_list.append("ORDER BY ");
				sql_list.append("t1.message_time ASC ");
				params_list.add(map.get("task_id"));
				params_list.add(root_id);
				params_list.add(root_id);
				
				tmList = jdbcUtils.findMoreResult(sql_list.toString(), params_list);
			}else if(root_id.equals("-1")){
				StringBuilder sql_list = new StringBuilder();
				List<Object> params_list = new ArrayList<Object>();
				sql_list.append("SELECT ");
				sql_list.append("t1.photo,t1.username AS reply,t1.users_id AS reply_id,t1.message_id,t1.task_id,");
				sql_list.append("t1.message_content,t1.message_time,u.username AS replied,u.users_id AS replied_id ");
				sql_list.append("FROM (");
				sql_list.append("SELECT ");
				sql_list.append("u.photo,u.username,tm.users_id,tm.message_id,");
				sql_list.append("tm.task_id,tm.message_content,tm.message_time,tm.to_user ");
				sql_list.append("FROM ");
				sql_list.append("users_info u,task_message tm ");
				sql_list.append("WHERE ");
				sql_list.append("u.users_id = tm.users_id ");
				sql_list.append("AND tm.task_id=? AND (tm.root_id=? OR tm.message_id=?) ");
				sql_list.append("ORDER BY ");
				sql_list.append("tm.message_time ASC ) t1,users_info u ");
				sql_list.append("WHERE ");
				sql_list.append("t1.to_user = u.users_id ");
				sql_list.append("ORDER BY ");
				sql_list.append("t1.message_time ASC ");
				params_list.add(map.get("task_id"));
				params_list.add(message_id);
				params_list.add(message_id);
				
				tmList = jdbcUtils.findMoreResult(sql_list.toString(), params_list);
				
			}
			
			if(tmList != null){
				map.put("tmList", tmList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> signObject(String source) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT ");
		sql.append("s.sign_id,s.task_id,s.users_id,s.message,s.open_mes,s.sign_time,s.isexe,");
		sql.append("t.title,u.username,u.photo,u.qq,u.email,u.phone ");
		sql.append("FROM ");
		sql.append("sign s,users_info u,task t ");
		sql.append("WHERE ");
		sql.append("s.users_id = u.users_id AND s.task_id = t.task_id AND s.sign_id=?");
		params.add(source);
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql.toString(), params);
			
			String open_mes = (String) map.get("open_mes");
			String phone = open_mes.substring(0,1);
			String qq = open_mes.substring(1,2);
			String email = open_mes.substring(2);
			if(phone.equals("0")){
				map.remove("phone");
			}
			if(qq.equals("0")){
				map.remove("qq");
			}
			if(email.equals("0")){
				map.remove("email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> aftetTaskInfo(String source) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("t.task_id,t.title,t.task_state,");
		sql.append("u.users_id,u.username,u.department,u.special,u.classname,u.sex,u.photo,u.grade,");
		sql.append("s.sign_id,s.sign_time,s.message,");
		sql.append("a.p_time,a.prise,a.msg ");
		sql.append("FROM ");
		sql.append("sign s,after_treatment a,task t,users_info u ");
		sql.append("WHERE ");
		sql.append("s.sign_id = a.id ");
		sql.append("AND s.task_id = t.task_id ");
		sql.append("AND t.users_id = u.users_id ");
		sql.append("AND a.id=? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(source);
		Map<String, Object> map = null;
		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findSimpleResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
