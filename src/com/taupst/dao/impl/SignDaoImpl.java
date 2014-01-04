package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.SignDao;
import com.taupst.model.Sign;


@Repository("signDao")
public class SignDaoImpl extends BaseDao implements SignDao {

	@Override
	public Map<String, Object> save(Sign sign) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into `sign`(sign_id,task_id,users_id,sign_time,open_mes,isexe,message) ");
		sql.append("values(?,?,?,?,?,?,?) ");
		
		params.add(sign.getSign_id());
		params.add(sign.getTask_id());
		params.add(sign.getUsers_id());
		params.add(sign.getSign_time());
		params.add(sign.getOpen_mes());
		params.add(sign.getIsexe());
		params.add(sign.getMessage());
		
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
	 * return 
	 * 		0.表示可接任务 <br>
	 * 		1.表示已经接过的任务 <br>
	 * 		2.表示数据库异常或数据库中没有这条任务 <br>
	 * 		3.表示任务是当前用户发布的<br>
	 * 		4.表示任务已经过期了<br>
	 * 		5.表示任务已经完成了<br>
	 */
	@Override
	public int isSign(Sign sign) {
		
		String sql_sign = "SELECT s.* FROM sign s WHERE s.task_id=? AND s.users_id=? ";
		String sql_task = "SELECT t.* FROM task t WHERE t.task_id=? ";
		
		Sign s = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		int flag = 1;
		
		List<Object> params_sign = new ArrayList<Object>();
		params_sign.add(sign.getTask_id());
		params_sign.add(sign.getUsers_id());
		List<Object> params_task = new ArrayList<Object>();
		params_task.add(sign.getTask_id());

		try {
			this.jdbcUtils.getConnection();
			// 查询任务的状态以及发布人
			map = this.jdbcUtils.findSimpleResult(sql_task, params_task);
			
			if(!map.isEmpty()){
				Integer state = (Integer) map.get("task_state");
				String users_id = (String) map.get("users_id");
				
				// 表示任务是当前用户发布的
				if(users_id.equals(sign.getUsers_id())){
					flag = 3;
				}else{
					
					// state == 1 表示正常,state == 2 表示过期  ,state == 3 表示完成
					switch (state) {
					case 1:
						// 判断该用户是否已经接过这个任务
						s = this.jdbcUtils.findSimpleRefResult(sql_sign, params_sign, Sign.class);
						if (s == null) {
							flag = 0;//表示可接任务
						} else {
							flag = 1;//表示已经接过的任务
						}
						break;
					case 2:
						flag = 4;//表示任务已经过期了
						break;
					case 3:
						flag = 5;//表示任务已经完成了
						break;
					}
				}
			}else {
				flag = 2;//表示数据库异常或数据库中没有这条任务
			}
			
		} catch (Exception e) {
			flag = 2;//表示数据库异常或数据库中没有这条任务
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}

}
