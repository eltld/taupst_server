package test.util;


public class JDBCFactory {
	private static JdbcUtils jdbcUtils = new JdbcUtils();

	public static JdbcUtils getJdbcUtils() {
		return jdbcUtils;
	}

}
