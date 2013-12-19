package test.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.dao.TaskMesDao;
import test.util.JDBCFactory;
import test.util.JdbcUtils;
import test.util.Page;

public class TaskMesDaoImpl implements TaskMesDao{
	private JdbcUtils jdbcUtils = JDBCFactory.getJdbcUtils();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> getTaskMesList(String task_id,Page page) {
		List<Map<String, Object>> tmList = new ArrayList<Map<String, Object>>();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT t1.photo,t1.username AS reply,t1.message_id,t1.message_content,t1.message_time,t1.to_who,u.username AS replied ");
		sql.append("FROM (");
		sql.append("SELECT u.photo,u.username,tm.message_id,tm.message_content,tm.message_time,tm.to_who ");
		sql.append("FROM users_info u,task_message tm ");
		sql.append("WHERE u.users_id = tm.users_id AND tm.task_id = ? ");
		sql.append("ORDER BY tm.message_time DESC LIMIT ?,? ");
		sql.append(") t1,users_info u ");
		sql.append("WHERE u.users_id = t1.to_who ");
		
		params.add(task_id);
		params.add(page.getPageNo());
		params.add(page.getPageSize());
		
		try {
			this.jdbcUtils.getConnection();
			tmList = this.jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return tmList;
	}

}
