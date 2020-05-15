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

			// ��ȡһ��ʱ��� ���Բ���ִ��Ч��
			long start = System.currentTimeMillis();
			// ͳһִ��ִ��������
			statement.execute();

			statement.getResultSet();

			// ��ȡһ��ʱ���
			long end = System.currentTimeMillis();
			System.out.println("�������ˣ�" + (end - start));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// ����ر���Դ
			statement.close();
			connection.close();
		}

	}
}
