package com.tes.batch;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tes.util.JdbcUtil2;

public class LinkMysql2 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// ��ȡ���ݿ�����
		Connection connection = JdbcUtil2.getConnection();
		// ��Ҫִ�е�sql���
		String sql = "INSERT INTO company (company_id, company_name, company_manager, manager_tel, manager_mail, company_address, status, bought_plan_id, event_plus_account, event_plus_month, even_plus_retention_period, event_plus_disk, remark) VALUES ('98563210', 'tes', 'kin', '09065659898', 'kin.te-system.com', 'tokyo', '2', '1', '2', '3', '3', '200', 'TEST');";
		// ��ȡԤ������󣬲���������ֵ
		PreparedStatement statement = connection.prepareCall(sql);
//		statement.setDate(1,new Date(0));
		// ִ��sql��䣨ִ���˼�����¼���ͷ��ؼ���
		int i = statement.executeUpdate(); // executeUpdate��ִ�в�����
		System.out.println(i);
		// �ر�jdbc����
		JdbcUtil2.closeResource(null, statement, connection);
	}
}