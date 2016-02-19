package cn.jiuling.vehicleinfosys2.util;

import java.sql.*;

/**
 * JDBC工具类(目前只应用于对接白山项目)
 *
 * @author Tony
 * @version 1.001, 2015-11-06
 */
public class JdbcUtil {

	//连接数据库的参数
	private static String url = null;
	private static String user = null;
	private static String driver = null;
	private static String password = null;

	private JdbcUtil () {

	}

	private static JdbcUtil instance = null;

	public static JdbcUtil getInstance() {
		if (instance == null) {
			synchronized (JdbcUtil.class) {
				if (instance == null) {
					instance = new JdbcUtil();
				}
			}
		}

		return instance;
	}

	//注册驱动
	static {
		try {
			url = "jdbc:postgresql://"+PropertiesUtils.get("datasource.postgresql.userUrlHost")+"/"+PropertiesUtils.get("datasource.postgresql.userDBName");
			user = PropertiesUtils.get("datasource.postgresql.userName");
			driver = PropertiesUtils.get("datasource.postgresql.driverClassName");
			password = PropertiesUtils.get("datasource.postgresql.password");

			Class.forName(driver);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//该方法获得连接
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	//释放资源
	public void closeConnection(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {

						e.printStackTrace();
					} finally {
						if (conn != null) {
							try {
								conn.close();
							} catch (SQLException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

}