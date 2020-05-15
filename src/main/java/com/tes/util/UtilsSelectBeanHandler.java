package com.tes.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.tes.batch.Company;

public class UtilsSelectBeanHandler {
	public static void main(String[] args) throws SQLException {
		// 创建dbUtils里面的QueryRunner对象
		QueryRunner queryRunner = new QueryRunner();
		// sql语句
		String sql = "select * from company where status=?";
		// 获取数据库连接
		Connection connection = JdbcUtil2.getConnection();
		// 存参数值的数组
		Object[] params = { 2 };
		// 执行查询，并以数组的形式返回查询结果（new BeanHandler()只会返回第一条记录，并转成对象）
		Company company = queryRunner.query(connection, sql, new BeanHandler<Company>(Company.class), params);
		System.out.println(company);
		System.out.println("\n");
		// 关闭数据库连接
		connection.close();
	}
}