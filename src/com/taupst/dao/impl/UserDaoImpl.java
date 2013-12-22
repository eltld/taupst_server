package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.taupst.dao.UserDao;
import com.taupst.model.User;
import com.taupst.queryhelper.UserQueryHelper;
import com.taupst.util.JdbcUtils;
@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Resource(name="jdbcUtils")
	private JdbcUtils jdbcUtils;// = JDBCFactory.getJdbcUtils();


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUserByHelper(UserQueryHelper helper, int startIndex,
			int endIndex) {
		//this.jdbcUtils = new JdbcUtils();
		List<User> userList = null;
		String sql = "select * from users_info where 1=1";
		if (helper != null) {
			if (helper.getStudent_id() != null)
				sql += " and student_id='" + helper.getStudent_id() + "'";
			if (helper.getDepartment() != null)
				sql += " and department='" + helper.getDepartment() + "'";
			if (helper.getRealname() != null)
				sql += " and realname='" + helper.getRealname() + "'";
		}
		sql += " limit ?,?";
		List params = new ArrayList();
		params.add(startIndex);
		params.add(endIndex - startIndex);
		try {
			this.jdbcUtils.getConnection();
			userList = this.jdbcUtils
					.findMoreRefResult(sql, params, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return userList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User getUserById(String userId) {
		//this.jdbcUtils = new JdbcUtils();
		User user = null;
		List params = new ArrayList();
		params.add(userId);
		String sql = "select * from users_info where users_id=?";
		try {
			this.jdbcUtils.getConnection();
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return user;
	}

	@Override
	public int getUserCountByHelper(UserQueryHelper helper) {
		//this.jdbcUtils = new JdbcUtils();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return count;
	}

	@Override
	public int test() {
		//this.jdbcUtils = new JdbcUtils();
		int count = 0;

		try {
			this.jdbcUtils.getConnection();
			count = this.jdbcUtils.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean saveUserInfo(Map<String, String> stuInfo) {
		//this.jdbcUtils = new JdbcUtils();
		boolean flag = false;
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into users_info(student_id,school,realname,sex,department,special,classname,pwd,grade) ");
		sql.append("values(?,?,?,?,?,?,?,?,?) ");
		params.add(stuInfo.get("student_id"));
		params.add(stuInfo.get("school"));
		params.add(stuInfo.get("xm"));
		params.add(stuInfo.get("lbl_xb"));
		params.add(stuInfo.get("lbl_xy"));
		params.add(stuInfo.get("lbl_zymc"));
		params.add(stuInfo.get("lbl_xzb"));
		params.add(stuInfo.get("pwd"));
		params.add(stuInfo.get("lbl_dqszj"));
		try {
			this.jdbcUtils.getConnection();
			flag = this.jdbcUtils.updateByPreparedStatement(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean isUserExist(String student_id, String school) {
		String pwd = "%%";
		String sql = "select * from users_info where student_id=? and pwd like ? and school=?";
		return this.isLoginTmp(student_id, pwd, school,sql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String , String> login(String student_id, String pwd, String school) {
		//return this.isLoginTmp(student_id, pwd, school, sql);
		User user = null;
		//boolean flag = false;
		Map<String , String> resultMap = new HashMap<String, String>();
		List params = new ArrayList();
		params.add(student_id);
		params.add(pwd);
		params.add(school);
		try {
			this.jdbcUtils.getConnection();
			String sql = "select users_id from users_info where student_id=? and pwd=? and school=?";
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
			if(user == null){
				//flag =  false;
				resultMap.put("isLogined", "false");
			}else {
				//flag = true;
				resultMap.put("isLogined", "true");
				resultMap.put("users_id", user.getUsers_id().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return resultMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isLoginTmp(String student_id, String pwd, String school,String sql){
		User user = null;
		boolean flag = false;
		List params = new ArrayList();
		params.add(student_id);
		params.add(pwd);
		params.add(school);
		try {
			this.jdbcUtils.getConnection();
			user = this.jdbcUtils.findSimpleRefResult(sql, params, User.class);
			if(user == null){
				flag =  false;
			}else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}
	
}
