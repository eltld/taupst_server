package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.TaskMesDao;
import com.taupst.model.News;
import com.taupst.model.TaskMessage;
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
		
		sql.append("SELECT tm.task_id,tm.message_id,tm.users_id,u.username,u.sex,u.photo,tm.message_content,tm.message_time ");
		sql.append("FROM users_info u,task_message tm ");
		sql.append("WHERE u.users_id = tm.users_id AND tm.task_id=? AND tm.root_id = '-1' ");
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
		
		params.add(FinalVariable.PAGE_SIZE);
		
		try {
			this.jdbcUtils.getConnection();
			tmList = this.jdbcUtils.findMoreResult(sql.toString(), params);
			
			StringBuilder sql_child = new StringBuilder();
			sql_child.append("SELECT ");
			sql_child.append("t1.username AS reply,t1.users_id AS reply_id,");
			sql_child.append("t1.message_content,");
			sql_child.append("u.username AS replied,u.users_id AS replied_id ");
			sql_child.append("FROM (");
			sql_child.append("SELECT ");
			sql_child.append("u.photo,u.username,tm.users_id,tm.message_id,tm.task_id,");
			sql_child.append("tm.message_content,tm.message_time,tm.to_user ");
			sql_child.append("FROM ");
			sql_child.append("users_info u,task_message tm ");
			sql_child.append("WHERE ");
			sql_child.append("u.users_id = tm.users_id AND tm.task_id=? AND tm.root_id=? ");
			sql_child.append("ORDER BY ");
			sql_child.append("tm.message_time DESC ");
			sql_child.append(")t1,users_info u ");
			sql_child.append("WHERE ");
			sql_child.append("t1.to_user = u.users_id ");
			sql_child.append("ORDER BY ");
			sql_child.append("t1.message_time ASC ");
			
			for (Map<String, Object> rootMap : tmList) {
				String messageId = (String) rootMap.get("message_id");
				List<Object> param_child = new ArrayList<Object>();
				param_child.add(task_id);
				param_child.add(messageId);
				
				Map<String, Object> contentMap = new HashMap<String, Object>();
				List<Map<String, Object>> childList = this.jdbcUtils.findMoreResult(sql_child.toString(), param_child);
				String message_content = (String) rootMap.get("message_content");
				contentMap.put("message_content", message_content);
				List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
				contentList.add(contentMap);
				contentList.addAll(1, childList);
				rootMap.remove("message_content");
				rootMap.put("content", contentList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return tmList;
	}

	@Override
	public int save(TaskMessage tm,News news) {
		
		int flag = 2;
		
		StringBuilder sql_tm = new StringBuilder();
		
		sql_tm.append("insert into task_message(");
		sql_tm.append("message_id,users_id,task_id,message_content,message_time,to_user,root_id) ");
		sql_tm.append("values(");
		sql_tm.append("'"+tm.getMessage_id()+"','"+tm.getUsers_id()+"',");
		sql_tm.append("'"+tm.getTask_id()+"','"+tm.getMessage_content()+"',");
		sql_tm.append("'"+tm.getMessage_time()+"','"+tm.getTo_user()+"',");
		sql_tm.append("'"+tm.getRoot_id()+"') ");
		
	    StringBuilder sql_news = new StringBuilder();
	    
	    sql_news.append("insert into news(");
	    sql_news.append("news_id,type,send,receive,source,content) ");
	    sql_news.append("values(");
	    sql_news.append("'"+news.getNews_id()+"',"+news.getType()+",");
	    sql_news.append("'"+news.getSend()+"','"+news.getReceive()+"',");
	    sql_news.append("'"+news.getSource()+"','"+news.getContent()+"')");
	    
		try {
			String[] sql = new String[]{sql_tm.toString(),sql_news.toString()};
			jdbcUtils.getConnection();
			boolean isSucceed = jdbcUtils.updateByBatch(sql);
			if(isSucceed == true){
				flag = 0;
			}else{
				flag = 1;
			}
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		
		return flag;
	}

}
