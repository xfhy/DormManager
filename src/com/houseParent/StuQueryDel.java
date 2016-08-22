package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.dataControl.DatabaseConnect;
import com.student.Student;


/**
 * 2016��8��22��17:59:42
 * 
 * ��ѯɾ��ѧ��
 * 
 * @author XFHY
 *
 */
public class StuQueryDel {
	
	JFrame mainFrame = new JFrame("��ѯɾ��");
	
	//����Ĳ���
	JLabel findRoomLable = new JLabel("��ѯ������Ϣ",JLabel.LEFT);
	JLabel roomNumLable = new JLabel("���Һ�");
	JTextField roomNumField = new JTextField(6);   //�����   ����������Ҫ��ѯ�����Һ�
	JButton queryBtn = new JButton("��ѯ");
	JButton deleteBtn = new JButton("ɾ��");
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
	
	JTable roomTable = null;
	private JScrollPane scrollPane;
	//private ResultSetTableModel model;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	//����һά������Ϊ�б���
	Object[] columnTitle = new Object[]{"ѧ��","����","�Ա�","�༶","ѧԺ","��λ","���Һ�"};
	
	// ���췽��
	public StuQueryDel() {
		init();
	}

	// ��ʼ��
	private void init() {
		topPanel.add(findRoomLable);
		topPanel.add(roomNumLable);
		topPanel.add(roomNumField);
		topPanel.add(queryBtn);
		topPanel.add(deleteBtn);
		
		List<Student> stuList = new ArrayList<Student>();
		stuList = getAllStuInfo();   //��ʼ��List����,��Щ��Ϣ��ѧ������Ϣ   ��Ҫ��ӵ�JTable�е�
		
		roomTable = new JTable(getRowInfo(stuList),columnTitle);
		
		scrollPane = new JScrollPane(roomTable);  //��ӱ����������
		
		//����ˮƽ��������ʾ��ʽ
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//���ô�ֱ��������ʾ��ʽ
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mainFrame.add(topPanel,BorderLayout.NORTH);
		mainFrame.add(scrollPane,BorderLayout.CENTER);
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	// ��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ������
	public static void main(String[] args) {
		new StuQueryDel();
	}
	
	/**
	 * ��ȡ��Ҫ���뵽JTable�е������е���Ϣ
	 * @param stuList  װ��ѧ����Ϣ��List����
	 * @return  ���ط�װ�ɶ�ά�����ѧ����ϢObject[][]����
	 */
	private Object[][] getRowInfo(List<Student> stuList){
		Object[][] allStuInfo = new Object[stuList.size()][7];
		
		for (int i=0; i < stuList.size(); i++) {
			Student student = stuList.get(i);
			allStuInfo[i][0] = student.getAccount();
			allStuInfo[i][1] = student.getName();
			allStuInfo[i][2] = student.getSex();
			allStuInfo[i][3] = student.getClassroom();
			allStuInfo[i][4] = student.getCollege();
			allStuInfo[i][5] = student.getBed();
			allStuInfo[i][6] = student.getRoomnum();
		}
		
		return allStuInfo;
	}
	
	/**
	 * ������е�ѧ����Ϣ��JTable   
	 * @return �������е�ѧ��Ԫ��  ÿ��ѧ����Ϣ����JTableһ�е�ֵ
	 */
	private List<Student> getAllStuInfo(){
		List<Student> stuList = new ArrayList<Student>();
		try {
			// ���װ��JTable��JScrollPane��Ϊ��
			if (scrollPane != null) {
				// ����������ɾ�����
				mainFrame.remove(scrollPane);
			}
			
			// ����������Ϊ�գ���رս����
			if (resultSet != null) {
				resultSet.close();
			}
			
			//select * from student_info where roomnum=?
			String query = "select * from student_info";
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(query);
			// ��ѯ���ݱ�
			resultSet = preState.executeQuery();
			while(resultSet.next()){
				Student student = new Student();
				student.setAccount(resultSet.getString("id"));   //���ѧ��
				student.setName(resultSet.getString("name"));    //�������
				student.setSex(resultSet.getString("sex"));      //�Ա�
				student.setClassroom(resultSet.getString("classroom")); //�༶
				student.setCollege(resultSet.getString("college"));     //Ժϵ
				student.setBed(resultSet.getString("bed"));             //��λ
				student.setRoomnum(resultSet.getString("roomnum"));     //���ұ��
				stuList.add(student);  //������ѧ����Ϣ��stuList�б���
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"��ѯ���ݿ����~");
			return null;
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		return stuList;
	}
	
	/**
	 * ��չAbstractTableModel,���ڽ�һ��ResultSet��װ��TableModel     
	 * TableModel�ӿ�ָ���� JTable ����ѯ�ʱ��ʽ����ģ�͵ķ�����
	 *
	 */
	class ResultSetTableModel extends AbstractTableModel{
		private ResultSet rs;
		private ResultSetMetaData rsmd;   //�����ڻ�ȡ���� ResultSet �������е����ͺ�������Ϣ�Ķ���
		
		//������,��ʼ��rs��rsmd��������
		public ResultSetTableModel(ResultSet aResultSet){
			rs = aResultSet;
			try {
				rsmd = rs.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//��дgetColumnName()����,���������ȡָ���е�����    ����Ϊ��TableModel��������
		@Override
		public String getColumnName(int c) {
			try {
				return rsmd.getColumnName(c + 1);
			} catch (SQLException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		//��дgetColumnCount()����,���ظ�ģ���е�����     �������ø�TableModel������
		public int getColumnCount() {
			try {
				return rsmd.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

		//��дgetRowCount()����,���ظ�ģ���е�����    �������ø�TableModel������
		public int getRowCount() {
			try {
				//��ȡ���һ�еı��   ���ǵõ�ģ���е�����
				rs.last();        //������ƶ����� ResultSet ��������һ�С�
				return rs.getRow();   //��ȡ��ǰ�б��       
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

		//��дgetValueAt()����,���� columnIndex �� rowIndex λ�õĵ�Ԫ��ֵ��    �������ø�TableModelָ����Ԫ���ֵ
		public Object getValueAt(int r, int c) {
			try {
				rs.absolute(r + 1);    //������ƶ����� ResultSet ����ĸ����б��
				return rs.getObject(c + 1);  //�� Java ��������� Object ����ʽ��ȡ�� ResultSet ����ĵ�ǰ����ָ���е�ֵ��
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//��дisCellEditable()����,��Ԫ���Ƿ�ɱ༭    �õ�Ԫ�񲻿ɱ༭
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			//��� rowIndex �� columnIndex λ�õĵ�Ԫ���ǿɱ༭�ģ��򷵻� true�������ڸõ�Ԫ���ϵ��� setValueAt ������ĸõ�Ԫ���ֵ
			return false;    
		}
		
		
		
	}
	
	
	
}
