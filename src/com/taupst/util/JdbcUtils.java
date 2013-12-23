package com.taupst.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("jdbcUtils")
public class JdbcUtils {
	private String host = "sqld.duapp.com";
	private String port = "4050";
	private String username = "A9MIroqTfseTHViNpijkXvV4";
	private String password = "2UEyEfQIMb32jnrUgs01rAyuCdqmcnsu";

	private String dbUrl = "jdbc:mysql://";
	private String serverName = host + ":" + port + "/";

	private String databaseName = "WUdJdNvkxUsqACQRTaxZ";
	private String connName = dbUrl + serverName + databaseName;

	private Connection connection;

	private PreparedStatement pstmt;

	private ResultSet resultSet;

	private Statement stmt;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("驱动加载成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public Connection getConnection() {
		try {

			connection = DriverManager.getConnection(connName, username,
					password);
			// connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wudjdnvkxusqacqrtaxz","root" ,"root");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean deleteByBatch(String[] sql) throws SQLException {
		boolean flag = false;
		stmt = connection.createStatement();
		if (sql != null) {
			for (int i = 0; i < sql.length; i++) {
				stmt.addBatch(sql[i]);
			}
		}
		int[] count = stmt.executeBatch();
		if (count != null) {
			flag = true;
		}
		return flag;
	}

	public boolean updateByPreparedStatement(String sql, List<Object> params)
			throws SQLException {
		boolean flag = false;
		int result = -1;
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	public int getCountByPreparedStatement(String sql) throws SQLException {

		ResultSet result = null;
		int total = 0;
		pstmt = connection.prepareStatement(sql);

		result = pstmt.executeQuery();
		if (result.next()) {
			total = result.getInt("total");
		}

		return total;
	}

	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	public List<Map<String, Object>> findMoreResult(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnLabel(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value instanceof Date) {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					cols_value = df.format(cols_value);
					System.out.println(cols_value);
				}
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {

			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				// if (cols_value == null) {
				// cols_value = "";
				// }
				if (cols_value instanceof Date) {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					cols_value = df.format(cols_value);
					System.out.println(cols_value);
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;
	}

	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				// if (cols_value == null) {
				// cols_value = "";
				// }
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;
	}

	public int test() throws Exception {
		pstmt = connection.prepareStatement("SELECT * FROM users_info");
		resultSet = pstmt.executeQuery();

		int i = 0;
		while (resultSet.next())
			i++;
		return i;
	}

	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
