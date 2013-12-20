package test.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.dao.TaskDao;
import test.model.TaskQueryConditions;
import test.util.JDBCFactory;
import test.util.JdbcUtils;
import test.util.Page;

public class TaskDaoImpl implements TaskDao {
	private JdbcUtils jdbcUtils = JDBCFactory.getJdbcUtils();


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
	public List<Map<String, Object>> getTaskList(
			TaskQueryConditions conditions, Page page) {
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT t.task_id,t.title,t.content,t.release_time,t.end_of_time,");
		sql.append("t.rewards,t.task_state,t.exe_id,t.task_level,u.photo,u.username,u.sex,u.grade,");
		sql.append("u.department,count(tm.message_id) as tm_cnt ");
		sql.append("FROM users_info u,task t,task_message tm ");
		sql.append("WHERE u.users_id = t.users_id AND t.task_id = tm.task_id ");
		sql.append("AND t.task_level LIKE ? AND t.title LIKE ? ");
		sql.append("GROUP BY u.users_id, t.task_id ");
		sql.append("ORDER BY t.release_time DESC ");
		sql.append("LIMIT ?,? ");
		
		List params = new ArrayList();
		params.add(conditions.getLevel());
		params.add(conditions.getKeywords());
		params.add(page.getPageNo());
		params.add(page.getPageSize());
		
		try {
			this.jdbcUtils.getConnection();
			 taskList = this.jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return taskList;
	}

}
