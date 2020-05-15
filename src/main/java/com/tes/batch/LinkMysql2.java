package com.tes.batch;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tes.util.JdbcUtil2;

public class LinkMysql2 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 获取数据库连接
		Connection connection = JdbcUtil2.getConnection();
		// 需要执行的sql语句
		String sql = "INSERT INTO company (company_id, company_name, company_manager, manager_tel, manager_mail, company_address, status, bought_plan_id, event_plus_account, event_plus_month, even_plus_retention_period, event_plus_disk, remark) VALUES ('98563210', 'tes', 'kin', '09065659898', 'kin.te-system.com', 'tokyo', '2', '1', '2', '3', '3', '200', 'TEST');";
		// 获取预处理对象，并给参数赋值
		PreparedStatement statement = connection.prepareCall(sql);
//		statement.setDate(1,new Date(0));
		// 执行sql语句（执行了几条记录，就返回几）
		int i = statement.executeUpdate(); // executeUpdate：执行并更新
		System.out.println(i);
		// 关闭jdbc连接
		JdbcUtil2.closeResource(null, statement, connection);
	}
}