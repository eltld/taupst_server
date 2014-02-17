package com.taupst.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taupst.dao.SignDao;
import com.taupst.model.News;
import com.taupst.model.Sign;

@Repository("signDao")
public class SignDaoImpl extends BaseDao implements SignDao {

	@Override
	public Map<String, Object> save(Sign sign, News news) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		StringBuilder sql_sign = new StringBuilder();
		sql_sign.append("insert into `sign`(");
		sql_sign.append("sign_id,task_id,users_id,sign_time,open_mes,isexe,message) ");
		sql_sign.append("values(");
		sql_sign.append("'" + sign.getSign_id() + "','" + sign.getTask_id()
				+ "',");
		sql_sign.append("'" + sign.getUsers_id() + "','" + sign.getSign_time()
				+ "',");
		sql_sign.append("'" + sign.getOpen_mes() + "'," + sign.getIsexe() + ",");
		sql_sign.append("'" + sign.getMessage() + "') ");

		StringBuilder sql_news = new StringBuilder();

		sql_news.append("insert into news(");
		sql_news.append("news_id,type,send,receive,source,content) ");
		sql_news.append("values(");
		sql_news.append("'" + news.getNews_id() + "'," + news.getType() + ",");
		sql_news.append("'" + news.getSend() + "','" + news.getReceive() + "',");
		sql_news.append("'" + news.getSource() + "','" + news.getContent() + "')");

		try {
			this.jdbcUtils.getConnection();
			String[] sql = new String[] { sql_sign.toString(),
					sql_news.toString() };
			boolean flag = this.jdbcUtils.updateByBatch(sql);
			if (flag == true) {
				resultMap.put("success", true);
				resultMap.put("state", 0);
				resultMap.put("msg", "亲，报名成功！");
			}else{
				resultMap.put("success", false);
				resultMap.put("state", 2);
				resultMap.put("msg", "亲，网络超时！");
			}
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
	 * return 0.表示可接任务 <br>
	 * 1.表示已经接过的任务 <br>
	 * 2.表示数据库异常或数据库中没有这条任务 <br>
	 * 3.表示任务是当前用户发布的<br>
	 * 4.表示任务已经过期了<br>
	 * 5.表示任务已经完成了<br>
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

			if (!map.isEmpty()) {
				Integer state = (Integer) map.get("task_state");
				String users_id = (String) map.get("users_id");

				// 表示任务是当前用户发布的
				if (users_id.equals(sign.getUsers_id())) {
					flag = 3;
				} else {

					// state == 1 表示正常,state == 2 表示过期 ,state == 3 表示完成
					switch (state) {
					case 1:
						// 判断该用户是否已经接过这个任务
						s = this.jdbcUtils.findSimpleRefResult(sql_sign,
								params_sign, Sign.class);
						if (s == null) {
							flag = 0;// 表示可接任务
						} else {
							flag = 1;// 表示已经接过的任务
						}
						break;
					/*
					 * case 2: flag = 4;//表示任务已经过期了 break;
					 */case 3:
						flag = 5;// 表示任务已经完成了
						break;
					}
				}
			} else {
				flag = 2;// 表示数据库异常或数据库中没有这条任务
			}

		} catch (Exception e) {
			flag = 2;// 表示数据库异常或数据库中没有这条任务
			e.printStackTrace();
		} finally {
			this.jdbcUtils.releaseConn();
		}
		return flag;
	}

	@Override
	public Map<String, Object> getUserBySignId(String sign_id) {
		Map<String, Object> map = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("u.* ");
		sql.append("FROM ");
		sql.append("sign s,users_info u ");
		sql.append("WHERE ");
		sql.append("s.users_id=u.users_id ");
		sql.append("AND s.sign_id=?");
		
		List<Object> params = new ArrayList<Object>();
		params.add(sign_id);
		
		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findSimpleResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public boolean ChooseExe(String sign_id, String c_time, String reply, News news) {
		
		StringBuilder sql_sign = new StringBuilder();
		sql_sign.append("UPDATE ");
		sql_sign.append("sign ");
		sql_sign.append("SET ");
		sql_sign.append("isexe=0 ");
		sql_sign.append("WHERE ");
		sql_sign.append("sign_id='"+sign_id+"'");
		
		StringBuilder sql_af = new StringBuilder();
		sql_af.append("insert into after_treatment(");
		sql_af.append("id,c_time,reply) ");
		sql_af.append("values('"+sign_id+"','"+c_time+"','"+reply+"')");

		StringBuilder sql_news = new StringBuilder();
		sql_news.append("insert into news(");
		sql_news.append("news_id,type,send,receive,source,content) ");
		sql_news.append("values(");
		sql_news.append("'" + news.getNews_id() + "'," + news.getType() + ",");
		sql_news.append("'" + news.getSend() + "','" + news.getReceive() + "',");
		sql_news.append("'" + news.getSource() + "','" + news.getContent() + "')");
		
		String[] sql = new String[]{sql_sign.toString(),sql_af.toString(),sql_news.toString()};
		
		boolean flag = false;
		try {
			this.jdbcUtils.getConnection();
			flag = this.jdbcUtils.updateByBatch(sql);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Map<String, Object> signInfo(String source) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("s.sign_id,s.task_id,s.sign_time,s.message,");
		sql.append("af.c_time,af.reply,t.title,t.users_id,u.username ");
		sql.append("FROM ");
		sql.append("sign s,after_treatment af,task t,users_info u ");
		sql.append("WHERE ");
		sql.append("s.sign_id = af.id ");
		sql.append("AND s.sign_id=? ");
		sql.append("AND s.task_id = t.task_id ");
		sql.append("AND u.users_id = t.users_id ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(source);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findSimpleResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> list(String task_id, String type) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("u.users_id,u.username,u.classname,u.department,u.special,u.sex,");
		sql.append("s.sign_id,s.isexe,s.sign_time,");
		sql.append("a.c_time,a.p_time,a.msg,a.prise ");
		sql.append("FROM ");
		sql.append("sign s ");
		sql.append("LEFT JOIN after_treatment a ON a.id = s.sign_id ");
		sql.append("LEFT JOIN users_info u ON u.users_id = s.users_id ");
		sql.append("WHERE ");
		sql.append("s.task_id=? ");
		if(type != null && type.equals("2")){
			sql.append("AND s.isexe = 0");
		}
		List<Object> params = new ArrayList<Object>();
		params.add(task_id);
		List<Map<String, Object>> map = null;
		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findMoreResult(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public int isSign(String task_id, String users_id) {
		
		String sql = "SELECT s.* FROM sign s WHERE s.task_id=? AND s.users_id=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(task_id);
		params.add(users_id);
		int flag = 0;
		Map<String, Object> map = null;
		try {
			this.jdbcUtils.getConnection();
			map = this.jdbcUtils.findSimpleResult(sql, params);
			if(map == null){
				flag = 0;
			}else{
				flag = 1;
			}
		} catch (Exception e) {
			flag = 2;
			e.printStackTrace();
		}
		return flag;
	}

}
