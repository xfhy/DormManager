package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.dataControl.DatabaseConnect;

/**
 * �������ݿ�,��ȡѧ����Ϣ
 * 
 * @author XFHY
 * 
 * 
 * 
 */
public class StudentOperate {
	
	/**
	 * ͨ���������ݿ�,��ѯ,��ȡĳ��ѧ����Ϣ,ȷ��ѧ����id
	 * @param id ѧ��ѧ��
	 *    ���ش���ѧ����Ϣ��Object[]����
	 */
	public static Object[] getStudentInfo(String id){
		Object[] data = new Object[6];  //���ڴ��ѧ��������
		
		//���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		//SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;
		//���صĽ��   ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resSet = null;
		String sql = "select * from student_info where id=?";
		try {
			conn = DatabaseConnect.connDatabase();  //�õ����������ݿ��Connectionʵ��
			preState = conn.prepareStatement(sql);  //����PreparedStatement����
			//setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~    ����λ���Ǵ�1��ʼ
			preState.setString(1, id);
			
			//ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null 
			resSet = preState.executeQuery();
			
			/*
			 * �����ӵ�ǰλ����ǰ��һ�С�ResultSet ������λ�ڵ�һ��֮ǰ����һ�ε��� next ����ʹ��һ�г�Ϊ��ǰ�У�
			 * �ڶ��ε���ʹ�ڶ��г�Ϊ��ǰ�У��������ơ� ������ next �������� false ʱ�����λ�����һ�еĺ��档
			 * */
			if(resSet.next()){  //����ҵ���
				data[0] = resSet.getString("name");        //����
				data[1] = resSet.getString("sex");         //�Ա�
				data[2] = resSet.getString("classroom");   //�༶
				data[3] = resSet.getString("college");  //Ժϵ
				data[4] = resSet.getString("bed");      //��λ
				data[5] = resSet.getString("roomnum");  //���ұ��
			} else {  
				//��Ȼ֮ǰ�ܹ���¼,��˵��֮ǰ���ݿ��ǿ������ӵ�,����ȴ�鲻�����û�,��˵�������û������ݿ�ر���,��������ԭ��
				JOptionPane.showMessageDialog(null,"δ��ѯ����ѧ������Ϣ....");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"���ݿ���ʳ�����! ! !");
		} finally {
			DatabaseConnect.closeResultset(resSet);   //�ر�Resultset
			DatabaseConnect.closeStatement(preState); //�ر�PreparedStatement�����
			DatabaseConnect.closeConnection(conn);   //�ر�Connection�����
		}
		return data;  //������������鷵��
	}
	
	
	
}
