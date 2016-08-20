package com;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.dataControl.DatabaseConnect;

/**
 * 2016��8��17��19:30:48
 * 
 * ���õĲ���: �޸�����
 * 
 * @author XFHY
 * 
 */
public class CommonOperate {

	/**
	 * ���ݴ�����û�����,�˻�,�޸�����
	 * 
	 * @param staffType
	 *            �û�����
	 * @param account
	 *            �û��˻�
	 * @param oldPasswrd
	 *            �û�ԭ��������
	 * @return �ж�һ���û�����ľ������Ƿ���ȷ,��ȷ�򷵻�true,���򷵻�false
	 */
	public static boolean isOldPasswrd(String staffType, String account,
			String oldPasswrd) {
		String sql = ""; // �����Ҫִ�е�SQL���
		if (staffType.equals("ѧ��")) {
			staffType = "student_info";
			sql = "select * from student_info where id=?";
		} else if (staffType.equals("�������Ա")) {
			staffType = "college_admin";
			sql = "select * from college_admin where id=?";
		} else if (staffType.equals("ϵͳ����Ա")) {
			staffType = "system_admin";
			sql = "select * from system_admin where id=?";
		}

		// ���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		// SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;
		// ���صĽ�� ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resultSet = null;
		try {
			conn = DatabaseConnect.connDatabase(); // �������ݿ�
			preState = conn.prepareStatement(sql); // ����PreparedStatement����
			// setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~ ����λ���Ǵ�1��ʼ
			preState.setString(1, account);
			// ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null
			resultSet = preState.executeQuery();

			/*
			 * �����ӵ�ǰλ����ǰ��һ�С�ResultSet ������λ�ڵ�һ��֮ǰ����һ�ε��� next ����ʹ��һ�г�Ϊ��ǰ�У�
			 * �ڶ��ε���ʹ�ڶ��г�Ϊ��ǰ�У��������ơ� ������ next �������� false ʱ�����λ�����һ�еĺ��档
			 */
			if (resultSet.next()) { // �����ֵ,���ҵ��˺Ϸ����û�
				String oldPass = resultSet.getString("passwrd");
				if (oldPass.equals(oldPasswrd)) { // ������ݿ��в�ѯ��������(������)���û�����ľ�����ƥ��
					// JOptionPane.showMessageDialog(null,
					// "���ݿ��в�ѯ��������(������)���û�����ľ�����ƥ��");
					return true; // ���Ｔʹ��return���,���ǻ��ǻ���ִ�������finally��return
				} else {
					// JOptionPane.showMessageDialog(null,
					// "���ݿ��в�ѯ��������(������)���û�����ľ����벻ƥ��");
					return false;
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "���ݿ���ʴ��� ! ! !");
		} finally {
			// �������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeResultset(resultSet); // �ر�ResultSet
			DatabaseConnect.closeStatement(preState); // �ر�PreparedStatement
			DatabaseConnect.closeConnection(conn); // �ر�Connection
		}

		return false;
	}

	/**
	 * �޸�����
	 * 
	 * @param staffType
	 *            �û�����
	 * @param account
	 *            �û��˻�
	 * @param passwrd
	 *            �û�������
	 * @return �����Ƿ��޸ĳɹ�
	 */
	public static boolean alterPasswrd(String staffType, String account,
			String passwrd) {
		String sql = "";
		if (staffType.equals("ѧ��")) {
			sql = "update student_info set passwrd = ? where id = ?";
		} else if (staffType.equals("�������Ա")) {
			sql = "update college_admin set passwrd=? where id=?";
		} else if (staffType.equals("ϵͳ����Ա")) {
			sql = "update system_admin set passwrd=? where id=?";
		}

		// ���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		// SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;

		// ����sql���ִ�к󷵻ص�����
		int result = 0;

		try {
			conn = DatabaseConnect.connDatabase(); // �������ݿ�
			preState = conn.prepareStatement(sql); // ����PreparedStatement����

			// setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~ ����λ���Ǵ�1��ʼ
			preState.setString(1, passwrd);
			preState.setString(2, account);

			// ִ�в�ѯ���,�����м��� ����ʲô�������ص�sql����򷵻�0
			result = preState.executeUpdate();

			if (result != 0) { // ��������������0,���޸ĳɹ�
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "���ݿ���ʴ��� ! ! !");
		} finally {
			// �������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeStatement(preState); // �ر�PreparedStatement
			DatabaseConnect.closeConnection(conn); // �ر�Connection
		}

		return false;
	}

	/**
	 * ��������   ���������֮��������������������嶼��font��
	 * @param font ����
	 */
	public static void InitGlobalFont(Font font) {
		
		// ʵ�� UIResource �� java.awt.Font �����ࡣ����Ĭ���������Ե� UI ��Ӧ��ʹ�ô��ࡣ
		FontUIResource fontRes = new FontUIResource(font);

		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); // keys():���ش˹�ϣ���еļ���ö��
		keys.hasMoreElements();) { // hasMoreElements():���Դ�ö���Ƿ���������Ԫ�ء�
			Object key = keys.nextElement(); // ��һ��
			Object value = UIManager.get(key); // ��Ĭ��ֵ����һ������

			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	

}