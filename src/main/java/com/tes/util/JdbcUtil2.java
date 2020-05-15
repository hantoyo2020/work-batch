package com.tes.util;

import java.sql.*;
import java.util.ResourceBundle;

public class JdbcUtil2 {
	// ˽�б���
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	// ��̬��
	static {
		try {
			// 2.3ͨ��ResourceBundle���õ����ݿ�������Ϣ
			ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
			driver = resourceBundle.getString("driver");
			url = resourceBundle.getString("url");
			user = resourceBundle.getString("user");
			password = resourceBundle.getString("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �������ݿ�����
	public static Connection getConnection() {
		try {
			// ע�����ݿ������
			Class.forName(driver);
			// ��ȡ���ݿ����ӣ��������������ǣ��������Ͷ˿ڡ��û��������룩
			Connection connection = DriverManager.getConnection(url, user, password);
			// �������ݿ�����
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// �رս����
	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// �ر�Ԥ�������
	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// �ر����ݿ�����
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// һ���Թر���������
	public static void closeResource(ResultSet resultSet, Statement statement, Connection connection) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}
}