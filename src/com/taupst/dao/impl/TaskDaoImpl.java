package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.taupst.dao.TaskDao;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.util.FinalVariable;
import com.taupst.util.JdbcUtils;
import com.taupst.util.Page;

@Repository("taskDao")
public class TaskDaoImpl implements TaskDao {

	@Resource(name = "jdbcUtils")
	private JdbcUtils jdbcUtils;

	@Override
	public List<Map<String, Object>> getTaskList(Page page) {
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT t.task_id,t.title,t.content,t.release_time,t.end_of_time,");
		sql.append("t.rewards,t.task_state,t.exe_id,u.photo,u.username,u.sex,u.grade,");
		sql.append("u.department,count(tm.message_id) as tm_cnt ");
		sql.append("FROM users_info u,task t,task_message tm ");
		sql.append("WHERE u.users_id = t.users_id AND t.task_id = tm.task_id ");
		sql.append("GROUP BY u.users_id, t.task_id ");
		sql.append("ORDER BY t.release_time DESC ");
		sql.append("LIMIT " + page.getPageNo() + "," + page.getPageSize() + " ");

		try {
			this.jdbcUtils.getConnection();
			taskList = this.jdbcUtils.findMoreResult(sql.toString(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return taskList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,int type) {
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		String task_id = conditions.getTask_id();
		
		if(task_id != null && !task_id.equals("")){
			task_id = task_id.substring(0, 17);
		}
		

		params.add(conditions.getSchool());
		params.add(conditions.getLevel());
		params.add(conditions.getKeywords());
		
		sql.append("SELECT t.task_id,t.title,t.content,t.release_time,t.end_of_time,t.rewards,t.task_state,t.task_level,");
		sql.append("u.users_id,u.photo,u.username,u.sex,u.grade,u.department,");
		sql.append("COUNT(DISTINCT tm.message_id) AS tm_cnt,");
		sql.append("COUNT(DISTINCT s.sign_id) AS sign_cnt ");
		sql.append("FROM task t ");
		sql.append("LEFT JOIN task_message tm ON t.task_id = tm.task_id ");
		sql.append("LEFT JOIN sign s ON t.task_id = s.task_id ");
		sql.append("LEFT JOIN users_info u ON u.users_id = t.users_id ");
		sql.append("WHERE u.school=? AND t.task_level LIKE ? AND t.title LIKE ? ");

		if(type == 1){
			sql.append("AND SUBSTRING(t.task_id FROM 1 FOR 17) > ? ");
			params.add(task_id);
		}else if(type == 2){
			sql.append("AND SUBSTRING(t.task_id FROM 1 FOR 17) < ? ");
			params.add(task_id);
		}
		sql.append("GROUP BY t.task_id ");
		sql.append("ORDER BY t.release_time DESC ");
		sql.append("LIMIT ? ");

		params.add(FinalVariable.PAGE_SIZE);

		try {
			this.jdbcUtils.getConnection();
			taskList = this.jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return taskList;
	}

}
