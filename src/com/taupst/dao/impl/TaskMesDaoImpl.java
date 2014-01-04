package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.TaskMesDao;
import com.taupst.util.FinalVariable;

@Repository("taskMesDao")
public class TaskMesDaoImpl extends BaseDao implements TaskMesDao{
	
	@Override
	public List<Map<String, Object>> getTMList(String task_id,String tm_id,int type) {
		List<Map<String, Object>> tmList = new ArrayList<Map<String, Object>>();
		List<Object> params = new ArrayList<Object>();
		params.add(task_id);
		
		if(tm_id != null && !tm_id.equals("")){
			tm_id = tm_id.substring(0, 17);
		}
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT t1.photo,t1.username AS reply,t1.message_id,t1.message_content,t1.message_time,t1.to_who,u.username AS replied ");
		sql.append("FROM (");
		sql.append("SELECT u.photo,u.username,tm.message_id,tm.task_id,tm.message_content,tm.message_time,tm.to_who ");
		sql.append("FROM users_info u,task_message tm ");
		sql.append("WHERE u.users_id = tm.users_id AND tm.task_id = ? ");
		// type == 1 表示向下拉，获取比当前id时间更新的
		if(type == 1){
			sql.append("AND SUBSTRING(tm.message_id FROM 1 FOR 17) > ? ");
			params.add(tm_id);
		}else if(type == 2){
			// type == 2 表示向上拉，获取比当前id时间更旧的
			sql.append("AND SUBSTRING(tm.message_id FROM 1 FOR 17) < ? ");
			params.add(tm_id);
		}
		sql.append("ORDER BY tm.message_time DESC LIMIT ? ");
		sql.append(") t1,users_info u ");
		sql.append("WHERE u.users_id = t1.to_who ");
		
		params.add(FinalVariable.PAGE_SIZE);
		
		try {
			this.jdbcUtils.getConnection();
			tmList = this.jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return tmList;
	}

}
