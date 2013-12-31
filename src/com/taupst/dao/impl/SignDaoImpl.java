package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.taupst.dao.SignDao;
import com.taupst.model.Sign;
import com.taupst.util.JdbcUtils;


@Repository("signDao")
public class SignDaoImpl implements SignDao {

	@Resource(name = "jdbcUtils")
	private JdbcUtils jdbcUtils;
	
	@Override
	public Map<String, Object> save(Sign sign) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into `sign`(sign_id,task_id,users_id,sign_time,open_mes,isexe) ");
		sql.append("values(?,?,?,?,?,?) ");
		
		params.add(sign.getSign_id());
		params.add(sign.getTask_id());
		params.add(sign.getUsers_id());
		params.add(sign.getSign_time());
		params.add(sign.getOpen_mes());
		params.add(sign.getIsexe());
		
		try {
			this.jdbcUtils.getConnection();
			this.jdbcUtils.updateByPreparedStatement(sql.toString(), params);
			resultMap.put("success", true);
			resultMap.put("state", 0);
			resultMap.put("msg", "亲，报名成功！");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("state", 2);
			resultMap.put("msg", "亲，网络超时！");
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return resultMap;
	}

	/**
	 * return 0，表示不存在 ，1,表示存在 ,2，表示数据库异常 ,3.表示任务是当前用户发布的
	 */
	@Override
	public int isSign(Sign sign) {
		
		String sql_sign = "SELECT s.* FROM sign s WHERE s.task_id=? AND s.users_id=? ";
		String sql_task = "SELECT t.* FROM task t WHERE t.task_id=? AND t.users_id=? ";
		
		Sign s = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		int flag = 1;
		
		List<Object> params = new ArrayList<Object>();
		params.add(sign.getTask_id());
		params.add(sign.getUsers_id());

		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findSimpleResult(sql_task, params);
			
			if(map.isEmpty()){
				s = this.jdbcUtils.findSimpleRefResult(sql_sign, params, Sign.class);
				if (s == null) {
					flag = 0;
				} else {
					flag = 1;
				}
			}else {
				flag = 3;
			}
			
		} catch (Exception e) {
			flag = 2;
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}

}
