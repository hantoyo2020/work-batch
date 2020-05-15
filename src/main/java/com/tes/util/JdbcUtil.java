package com.tes.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//��ȡ��db.properties�ļ��е����ݿ���Ϣ
public class JdbcUtil {
	// ˽�б���
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	// ��̬��
	static {
		try {
			// 1.�½����Լ�����
			Properties properties = new Properties();
			// 2ͨ�����䣬�½��ַ�����������ȡdb.properties�ļ�
			InputStream input = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
			// 3.���������ж�ȡ�������ԣ����ص�properties���Լ�������
			properties.load(input);
			// 4.���ݼ�����ȡproperties�ж�Ӧ��ֵ
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
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
}