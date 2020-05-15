package com.tes.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.tes.util.JdbcUtil;

public class test {

	private static Logger logger = Logger.getLogger(test.class);

	public static void main(String args[]) throws SQLException {

		Connection connection = JdbcUtil.getConnection();

		PreparedStatement statement = null;

		try {
			String sql = "SELECT * FROM `t-work`.emploee;";

			statement = connection.prepareStatement(sql);

			// 获取一个时间戳 可以测试执行效率
			long start = System.currentTimeMillis();
			// 统一执行执行批处理
			statement.execute();

			statement.getResultSet();

			// 获取一个时间戳
			long end = System.currentTimeMillis();
			System.out.println("共花费了：" + (end - start));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 逆序关闭资源
			statement.close();
			connection.close();
		}

	}
}
