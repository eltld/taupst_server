package test.util;

import test.dao.UserDao;
import test.dao.impl.UserDaoImpl;
import test.queryhelper.UserQueryHelper;
import test.service.UserService;
import test.service.impl.UserServiceImpl;

public class JDBCFactory {
	private static JdbcUtils jdbcUtils = new JdbcUtils();

	public static JdbcUtils getJdbcUtils() {
		return jdbcUtils;
	}

}
