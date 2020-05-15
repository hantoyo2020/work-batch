package com.tes.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

public class UtilsSelectColumnListHandler {
	public static void main(String[] args) throws SQLException {
		// 创建dbUtils里面的QueryRunner对象
		QueryRunner queryRunner = new QueryRunner();
		// sql语句
		String sql = "select * from company where status=?";
		// 获取数据库连接
		Connection connection = JdbcUtil2.getConnection();
		// 存参数值的数组
		Object[] params = { 2 };
		// 执行查询，并以数组的形式返回查询结果（new ColumnListHandler<>()返回结果中指定的列）
		List<Object> strs = queryRunner.query(connection, sql, new ColumnListHandler<Object>("company_manager"),
				params);
		System.out.println(strs);
		for (Object item : strs) {
			System.out.println(item);
		}
		// 关闭数据库连接
		connection.close();
	}
}
