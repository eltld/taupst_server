package com.taupst.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.TaskDao;
import com.taupst.model.Task;
import com.taupst.queryhelper.TaskQueryConditions;
import com.taupst.util.FinalVariable;
import com.taupst.util.MethodUtil;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDao implements TaskDao {

	@Override
	public List<Map<String, Object>> getTaskList(TaskQueryConditions conditions,int type) {
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		List<Object> params = new ArrayList<Object>();
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

	@Override
	public Map<String, Object> getTaskById(String task_id) {
		Map<String, Object> task = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();

		List<Object> params = new ArrayList<Object>();
		
		sql.append("SELECT t.* FROM task t where t.task_id=?");
		
		params.add(task_id);
		
		try {
			this.jdbcUtils.getConnection();
			task = this.jdbcUtils.findSimpleResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return task;
	}

	@Override
	public Map<String, Object> save(Task task) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into task(task_id,users_id,title,content,rewards,");
		sql.append("release_time,end_of_time,task_state,task_level) ");
		sql.append("values(?,?,?,?,?,?,?,?,?)");
		
		List<Object> params = new ArrayList<Object>();
		params.add(task.getTask_id());
		params.add(task.getUser_id());
		params.add(task.getTitle());
		params.add(task.getContent());
		params.add(task.getRewards());
		params.add(task.getRelease_time());
		params.add(task.getEnd_of_time());
		params.add(task.getTask_state());
		params.add(task.getTask_level());
		
		try {
			jdbcUtils.getConnection();
			boolean flag = jdbcUtils.updateByPreparedStatement(sql.toString(), params);
			if(flag == true){
				returnMap.put("msg", "亲，任务发布成功！");
				returnMap.put("state", 0);
				returnMap.put("success", true);
			}else{
				returnMap.put("msg", "亲，网络超时！");
				returnMap.put("state", 2);
				returnMap.put("success", false);
			}
			
		} catch (Exception e) {
			returnMap.put("msg", "亲，网络超时！");
			returnMap.put("state", 2);
			returnMap.put("success", false);
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public boolean updateTaskState() {

		boolean flag = false;
		
		StringBuilder sql = new StringBuilder();
		
		MethodUtil util = MethodUtil.getInstance();
		String date = util.getDate(0, null);
		
		sql.append("update task set task_state=2 where task_state=1 and end_of_time<?");
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(date);
		
		try {
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql.toString(), params);
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}


}
