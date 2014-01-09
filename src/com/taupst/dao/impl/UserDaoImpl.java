package com.taupst.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.taupst.dao.UserDao;
import com.taupst.model.User;
import com.taupst.queryhelper.UserQueryHelper;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao {


	@Override
	public User getUserById(String userId) {

		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		String sql = "select * from users_info where users_id=?";
		
		return this.userTmp(sql, params);

	}
	
	@Override
	public User getUser(String student_id, String pwd, String school) {
		String sql = "select * from users_info where student_id=? and pwd=? and school=?";
		List<Object> params = new ArrayList<Object>();
		params.add(student_id);
		params.add(pwd);
		params.add(school);
		
		return this.userTmp(sql, params);
	}
	
	
	private User userTmp(String sql,List<Object> params){
		User user = null;
		try {
			this.jdbcUtils.getConnection();
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return user;
	}

	@Override
	public int getUserCountByHelper(UserQueryHelper helper) {

		int count = 0;
		String sql = "select count(*) total from users_info where 1=1";
		if (helper != null) {
			if (StringUtils.isNotEmpty(helper.getStudent_id()))
				sql += " and student_id='" + helper.getStudent_id() + "'";
			if (StringUtils.isNotEmpty(helper.getDepartment()))
				sql += " and department='" + helper.getDepartment() + "'";
			if (StringUtils.isNotEmpty(helper.getRealname()))
				sql += " and realname='" + helper.getRealname() + "'";
		}

		try {
			this.jdbcUtils.getConnection();
			count = this.jdbcUtils.getCountByPreparedStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return count;
	}


	/**
	 * 用户登录后获取信息，<br/>
	 * 返回1表示，用户名或密码错误<br/>
	 * 返回2表示，网络异常<br/>
	 * 没有状态返回，表示登录成功
	 */
	@Override
	public Map<String, Object> login(String student_id, String pwd,
			String school) {
		User user = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		params.add(student_id);
		params.add(pwd);
		params.add(school);
		try {
			this.jdbcUtils.getConnection();
			String sql = "select * from users_info where student_id=? and pwd=? and school=?";
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
			if (user == null) {
				resultMap.put("isLogined", "false");
				resultMap.put("state", "1");
			} else {
				resultMap.put("isLogined", "true");
				resultMap.put("users_id", user.getUsers_id().toString());
				resultMap.put("user", user);
			}
		} catch (Exception e) {
			resultMap.put("isLogined", "false");
			resultMap.put("state", "2");
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return resultMap;
	}
	
	@Override
	public int isUserExist(String student_id, String school) {
		String pwd = "%%";
		String sql = "select * from users_info where student_id=? and pwd like ? and school=?";
		return this.isLoginTmp(student_id, pwd, school, sql);
	}

	/**
	 * 
	 * @param student_id
	 * @param pwd
	 * @param school
	 * @param sql
	 * @return 0,表示存在 1，表示不存在 ，2，表示数据库异常
	 */
	private int isLoginTmp(String student_id, String pwd, String school,
			String sql) {
		User user = null;
		int flag = 1;
		List<Object> params = new ArrayList<Object>();
		params.add(student_id);
		params.add(pwd);
		params.add(school);
		try {
			this.jdbcUtils.getConnection();
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
			if (user == null) {
				flag = 1;
			} else {
				flag = 0;
			}
		} catch (Exception e) {
			flag = 2;
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}



	@Override
	public boolean update(User user) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("update users_info set username=?,qq=?,email=?,phone=?,photo=? ");
		sql.append("where users_id=? ");
		params.add(user.getUsername());
		params.add(user.getQq());
		params.add(user.getEmail());
		params.add(user.getPhone());
		params.add(user.getPhoto());
		params.add(user.getUsers_id());

		return this.updataTmp(sql.toString(), params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean update(User user, Map<String, Object> map) {
		
		List<String> fileName = (List<String>) map.get("fileName");
		List<Object> useFileValue = (List<Object>) map.get("fileValue");
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("update users_info set ");
		for (int i = 0; i < fileName.size(); i++) {
			String fName = fileName.get(i);
			if(i + 1 == fileName.size()){
				sql.append(fName + "=? ");
			}else{
				sql.append(fName + "=?,");
			}
		}
		sql.append("where users_id=? ");
		for (Object o : useFileValue) {
			params.add(o);
		}
		params.add(user.getUsers_id());

		return this.updataTmp(sql.toString(), params);
	}
	
	
	@Override
	public boolean saveUserInfo(Map<String, String> stuInfo) {

		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into users_info(users_id,student_id,school,realname,sex,department,special,classname,pwd,grade) ");
		sql.append("values(?,?,?,?,?,?,?,?,?,?) ");
		params.add(stuInfo.get("users_id"));
		params.add(stuInfo.get("student_id"));
		params.add(stuInfo.get("school"));
		params.add(stuInfo.get("xm"));
		params.add(stuInfo.get("lbl_xb"));
		params.add(stuInfo.get("lbl_xy"));
		params.add(stuInfo.get("lbl_zymc"));
		params.add(stuInfo.get("lbl_xzb"));
		params.add(stuInfo.get("pwd"));
		params.add(stuInfo.get("lbl_dqszj"));

		return this.updataTmp(sql.toString(), params);
	}

	@Override
	public boolean updateSignature(User u) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("update users_info set signature=? ");
		sql.append("where users_id=? ");
		params.add(u.getSignature());
		params.add(u.getUsers_id());

		return this.updataTmp(sql.toString(), params);
	}
	
	private boolean updataTmp(String sql, List<Object> params) {
		boolean flag = false;
		//Connection conn = null;
		try {
			//conn = this.jdbcUtils.getConnection();
			this.jdbcUtils.getConnection();
			//conn.setAutoCommit(false);
			flag = this.jdbcUtils.updateByPreparedStatement(sql, params);
			
			//conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			/*try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}


}
