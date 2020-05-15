package com.tes.batch;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.tes.util.JdbcUtil2;

public class DbutilsInsert {
	public static void main(String[] args) throws SQLException {
		// 创建dbUtils里面的QueryRunner对象
		QueryRunner queryRunner = new QueryRunner();
		// sql语句
		String sql = "INSERT INTO company (company_id, company_name, company_manager, manager_tel, manager_mail, company_address, status, bought_plan_id, event_plus_account, event_plus_month, even_plus_retention_period, event_plus_disk, remark) VALUES ('45678998', 'cccccc', 'ccccc', ?, ?, 'tokyo', '2', '1', '2', '3', '3', '200', 'TEST');";
		// 存参数值的数组
		Object[] objects = { "03-36526-9854", "adfsf@hotmail.com" };
		// 获取数据库连接
		Connection connection = JdbcUtil2.getConnection();
		// 执行sql语句，并返回影响的行数
		int i = queryRunner.update(connection, sql, objects);
		System.out.println(i);
		// 关闭数据库连接
		connection.close();
	}
}