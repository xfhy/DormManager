package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.dataControl.DatabaseConnect;

/**
 * 2016��8��16��16:58:01
 * 
 * ��������û���¼�Ƿ�ɹ�   ר��ΪLogin�����
 * 
 * @author XFHY
 * 
 * �������ݿ�,������û���¼�Ƿ�ɹ�
 * 
 */
public class LoginCheck {
	
	/**
	 * �ж��û���¼�Ƿ�ɹ�
	 * @param table   ����
	 * @param id      �û�id
	 * @param passwrd �û�����
	 * @return        ����ֵ  1:��¼�ɹ�,�ҵ��˺Ϸ����û�     0:���ǺϷ��û�      -1:�����ݿⶼû�����ӳɹ�
	 */
	public static int isSucceed(String table,String id,String passwrd){
		String sql = ""; //�����Ҫִ�е�SQL���
		if(table.equals("ѧ��")){
			table = "student_info";
			sql = "select * from student_info where id=? and passwrd=?";
		} else if(table.equals("�������Ա")) {
			table = "college_admin";
			sql = "select * from college_admin where id=? and passwrd=?";
		} else if(table.equals("ϵͳ����Ա")) {
			table = "system_admin";
			sql = "select * from system_admin where id=? and passwrd=?";
		}
		
		//���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		//SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣 
		PreparedStatement preState = null; 
		//���صĽ��   ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resultSet = null;
		try {
			conn = DatabaseConnect.connDatabase();   //�������ݿ�
			//��������������ݶ�ʧ����,��ô����Ĵ��벻��ִ��,����finally
			if(conn == null){
				return -1;   
			}
			
			preState = conn.prepareStatement(sql);   //����PreparedStatement����
			//setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~    ����λ���Ǵ�1��ʼ
			preState.setString(1, id);
			preState.setString(2, passwrd);
			//ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null 
			resultSet = preState.executeQuery();   
			
			/*
			 * �����ӵ�ǰλ����ǰ��һ�С�ResultSet ������λ�ڵ�һ��֮ǰ����һ�ε��� next ����ʹ��һ�г�Ϊ��ǰ�У�
			 * �ڶ��ε���ʹ�ڶ��г�Ϊ��ǰ�У��������ơ� ������ next �������� false ʱ�����λ�����һ�еĺ��档
			 * */
			if(resultSet.next()){   //�����ֵ,���ҵ��˺Ϸ����û�
				return 1;  //���Ｔʹ��return���,���ǻ��ǻ���ִ�������finally��return
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "���ݿ���ʴ��� ! ! !");
		} catch (Exception e1){
			e1.printStackTrace();
		} finally {
			//�������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeResultset(resultSet);  //�ر�ResultSet
			DatabaseConnect.closeStatement(preState);   //�ر�PreparedStatement
			DatabaseConnect.closeConnection(conn);      //�ر�Connection
		}
		return 0;
	}
	
}
