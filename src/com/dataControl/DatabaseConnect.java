package com.dataControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 2016��8��15��22:20:00 
 * 
 * �������ݿ����Ӻ͹ر�Connection,PreparedStatement,Resultset
 * 
 * @author XFHY
 * 
 */
public class DatabaseConnect {
	static String drivers;
	static String url;
	static String username;
	static String password;

	//���췽��
	public DatabaseConnect() {
		
	}
	
	/**
	 *  �������ݿ�
	 * @return
	 */
	public static Connection connDatabase() {
		Connection conn = null;
		/*
		 * Properties ���ʾ��һ���־õ����Լ���Properties �ɱ��������л�����м��ء�
		 * �����б���ÿ���������Ӧֵ����һ���ַ����� 
		 * */
		Properties props = new Properties();
		// �������ݿ�����
		try {
			//FileInputStream:���ļ�ϵͳ�е�ĳ���ļ��л�������ֽ�    �������ļ����ص���������
			FileInputStream in = new FileInputStream("conn.ini");
			props.load(in); //���������ж�ȡ�����б�����Ԫ�ضԣ���
			in.close();  //�ر�������
			
			   /*-------�������ļ��м������ݿ���������---------*/
			//getProperty()��ָ���ļ��ڴ������б�����������
			drivers = props.getProperty("jdbc.drivers");
			url = props.getProperty("jdbc.url");
			username = props.getProperty("jdbc.username");
			password = props.getProperty("jdbc.password");
			Class.forName(drivers);   //��������
			
			DriverManager.setLoginTimeout(4);  //��������������ͼ���ӵ�ĳһ���ݿ�ʱ���ȴ����ʱ�䣬����Ϊ��λ
			//��ͼ�������������ݿ� URL ������
			conn = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��������ʧ��");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��! ! !\n ��ȷ���ѿ������ݿ����");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�����ļ�δ�ҵ�..");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��ȡ�����ļ�����..");
		}
		return conn;
	}

	// ר�������ر�Connection�����
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // �����ͷŴ� Connection ��������ݿ�� JDBC ��Դ�������ǵȴ����Ǳ��Զ��ͷ�
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "���ݿ���ʴ��󣡣���", "�����ʾ��Ϣ���",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// �ر�PreparedStatement�����
	public static void closeStatement(PreparedStatement stat) {
		if (stat != null) {
			try {
				// �����ͷŴ� Statement ��������ݿ�� JDBC ��Դ�������ǵȴ��ö����Զ��ر�ʱ�����˲�����
				//һ����˵��ʹ����������ͷ���Դ��һ����ϰ�ߣ��������Ա�������ݿ���Դ��ռ�á� 
				stat.close(); 
			} catch (Exception e2) {
				System.out.println("PreparedStatement");
				JOptionPane.showMessageDialog(null, "���ݿ���ʴ���!!!", "�����ʾ��Ϣ���",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// �ر�Resultset
	public static void closeResultset(ResultSet results) {
		if (results != null) {
			try {
				results.close(); // �����ͷŴ� ResultSet ��������ݿ�� JDBC ��Դ�������ǵȴ��ö����Զ��ر�ʱ�����˲����� 
			} catch (Exception e2) {
				System.out.println("ResultSet");
				JOptionPane.showMessageDialog(null, "���ݿ���ʴ��󣡣���", "�����ʾ��Ϣ���",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
