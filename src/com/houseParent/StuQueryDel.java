package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

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
	Logger log = Logger.getLogger(this.getClass().getName());
	//����Ĳ���
	JLabel findRoomLable = new JLabel("��ѯ������Ϣ",JLabel.LEFT);
	JLabel roomNumLable = new JLabel("���Һ�");
	JTextField roomNumField = new JTextField(6);   //�����   ����������Ҫ��ѯ�����Һ�
	JButton showAllStuBtn = new JButton("��ʾȫ��");
	JButton queryBtn = new JButton("��ѯ");
	JButton deleteBtn = new JButton("ɾ��");
	JButton exitBtn = new JButton("�˳�");
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
	
	JTable roomTable = new JTable();
	private JScrollPane scrollPane;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	public static final int JTABLE_COLUMN_COUNTS = 7;  //JTable����
	
	/**
	 * ����һά������Ϊ�б���
	 */
	Object[] columnTitle = new Object[]{"ѧ��","����","�Ա�","�༶","ѧԺ","��λ","���Һ�"};
	StudentTableModel studentTableModel = new StudentTableModel();
	List<Student> stuList = new ArrayList<Student>();
	
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
		topPanel.add(showAllStuBtn);
		topPanel.add(deleteBtn);
		topPanel.add(exitBtn);
		
		//���ò�ѯ��ť������
		queryBtn.addActionListener(new QueryListener());
		//����ɾ����ť������
		deleteBtn.addActionListener(new DeleteListener());
		//������ʾȫ����ť������
		showAllStuBtn.addActionListener(new ShowAllStuListener());  
		exitBtn.addActionListener(new ExitListener());
		
		stuList = getAllStuInfo("");   //��ʼ��List����,��Щ��Ϣ��ȫ��ѧ������Ϣ(����ָ�ĸ�����)   ��Ҫ��ӵ�JTable�е�
		studentTableModel.setStudent(stuList);   //��ʼ��TableModel
		roomTable.setModel(studentTableModel);   //����JTable��TableModel
		roomTable.setRowHeight(30);           //����JTable�и�
		setColumnWidth(4,250,250,250);
		//��ֹ���������֮�佻��
		JTableHeader tableHeader =roomTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		
		scrollPane = new JScrollPane(roomTable);  //��ӱ����������
		
		//����ˮƽ��������ʾ��ʽ
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//���ô�ֱ��������ʾ��ʽ
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mainFrame.add(topPanel,BorderLayout.NORTH);
		mainFrame.add(scrollPane,BorderLayout.CENTER);
		mainFrame.setSize(900, 500);
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
	 * ����ĳһ�е��п� 
	 * ����:ĳһ��,��ѡ���,�����,��С���
	 */
	private void setColumnWidth(int xColumn,int columnPreferredWidth,int columnMaxWidth,int columnMinWidth){
		TableColumn tempColumn = roomTable.getColumnModel().getColumn(xColumn);   //���JTable��ĳһ��
		tempColumn.setPreferredWidth(columnPreferredWidth);   //������ѡ���
		tempColumn.setMaxWidth(columnMaxWidth);               //���������
		tempColumn.setMinWidth(columnMinWidth);               //������С���
	}
	
	/**
	 * ������е�ѧ����Ϣ��JTable   
	 * @return �������е�ѧ��Ԫ��  ÿ��ѧ����Ϣ����JTableһ�е�ֵ
	 */
	private List<Student> getAllStuInfo(String roomNum){
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
			
			String query = null;
			conn = DatabaseConnect.connDatabase();
			if(roomNum.equals("")){
				query = "select * from student_info";
				preState = conn.prepareStatement(query);
			} else {
				query = "select * from student_info where roomnum=?";
				preState = conn.prepareStatement(query);
				preState.setString(1, roomNum);
			}

			
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
	 *  ��չAbstractTableModel�����ڽ�һ��List<Student>���ϰ�װ��TableModel
	 *  JTable��ʵ���ǻ���MVC��, ����JTabel��������ʾ��һ��������model��, JTable#setModel(TableModel dataModel)����������model��,
	 *  ����������붯̬��ʾ����, ����Ҫʵ��һ��TableMode
	 */
	class StudentTableModel extends AbstractTableModel {

		//����һ��Student���б�
		List<Student> stuList = new ArrayList<Student>();

		//����Student�б�,ͬʱ֪ͨJTable���ݶ����Ѹ���,�ػ����
		public void setStudent(final List<Student> list){
			//invokeLater()����:���� doRun.run() �� AWT �¼�ָ���߳����첽ִ�С������й���� AWT �¼��������ŷ�����
			//�˷���Ӧ����Ӧ�ó����߳���Ҫ���¸� GUI ʱʹ��
			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					stuList = list;
					fireTableDataChanged();  //֪ͨJTable���ݶ����Ѹ���,�ػ����
					//System.out.println("���½���");
				}
				
			});
			
		}
		
		//����JTable������
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		//����JTable������
		public int getRowCount() {
			return stuList.size();
		}

		// ��List���ó�rowIndex��columnIndex����ʾ��ֵ     �������ø�TableModelָ����Ԫ���ֵ
		public Object getValueAt(int rowIndex, int columnIndex) {
			Student student = stuList.get(rowIndex); // ��ȡ��ǰ�е�Student
			switch (columnIndex) { // ������,����ֵ
			case 0:
				return student.getAccount();   //��һ�� ѧ��
			case 1:
				return student.getName();      //�ڶ��� ����
			case 2:
				return student.getSex();       //������ �Ա�
			case 3:
				return student.getClassroom(); //������ �༶
			case 4:
				return student.getCollege();   //������ ѧԺ
			case 5:
				return student.getBed();       //������ ��λ
			case 6:
				return student.getRoomnum();   //������ ���Һ�
			default:
				break;
			}
			return null;
		}

		/**
		 * ����JTable������      ��������
		 */
		@Override
		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return columnTitle[0].toString();
			case 1:
				return columnTitle[1].toString();
			case 2:
				return columnTitle[2].toString();
			case 3:
				return columnTitle[3].toString();
			case 4:
				return columnTitle[4].toString();
			case 5:
				return columnTitle[5].toString();
			case 6:
				return columnTitle[6].toString();
			default:
				break;
			}
			return "��";
		}
		
		
		//��дisCellEditable()��������false����ÿ����Ԫ�񲻿ɱ༭
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
	}

	/**
	 * ��ѯ��ť������
	 */
	class QueryListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String queryRoom = roomNumField.getText();   //����û�����Ĳ�ѯ�����Һ�
			String regex = "^[0-9a-zA-Z]+$";   //�ж��û���������Һ��Ƿ���ȷ,ֻ������Ӣ�ĺ�����
			
			//�ж�������Ƿ�Ϊ��                       �ж��û���������Һ��Ƿ�Ϸ�
			if(queryRoom.equals("") || !queryRoom.matches(regex)){
				JOptionPane.showMessageDialog(null, "��������ȷ�����Һ�");
			}
			
			List<Student> list = new ArrayList<Student>();
			list = getAllStuInfo(queryRoom);      //��ȡ��ѯ�����ҵ�ѧ������Ϣ
			studentTableModel.setStudent(list);   //��������
			mainFrame.add(scrollPane,BorderLayout.CENTER);  //��JScrollPane������ӵ�JFrame��
			
			roomNumField.requestFocus();    //�ڲ�ѯ������,��������ý���
		}
		
	}
	
	/**
	 * ɾ����ť ������
	 */
	class DeleteListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			
			//getSelectedRows():��������JTableѡ���е�����
			int selectedRows[] = roomTable.getSelectedRows();  
			
			//�ж��û��Ƿ�ѡ�е���ѧ����Ϣ
			if(selectedRows.length > 0){  
				log.info("ѡ��������Ϊ:"+selectedRows.length);
				
			      /*-----------���û�ѡ���ѧ��������ʱ��¼����---------------*/
				StringBuffer names = new StringBuffer();
				StringBuffer ids = new StringBuffer();
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < selectedRows.length; i++) {  //����ѡ�е���  ѭ��   ȡ��Ҫ������
					idList.add(roomTable.getValueAt(selectedRows[i], 0).toString());
					if(i == (selectedRows.length-1)){  //���һ������֮����Ҫ���붺��
						//getValueAt():��ȡĳһ��ĳһ�е�ֵ
						names = names.append(roomTable.getValueAt(selectedRows[i], 1).toString());
						ids = ids.append(roomTable.getValueAt(selectedRows[i], 0).toString());
					} else {
						names = names.append(roomTable.getValueAt(selectedRows[i], 1).toString()+",");
						ids = ids.append(roomTable.getValueAt(selectedRows[i], 0).toString()+",");
					}
				}
				log.info(names.toString());
				log.info(ids.toString());
				
				Icon icon = new ImageIcon("image//student//ȷ��ɾ��.png", "ɾ��");
				// ȷ�϶Ի��� ����:ָʾ�û���ѡѡ��� int ѡ���һ��ͷ���0,�ڶ�����1
				int select = JOptionPane.showConfirmDialog(mainFrame, "��ȷ��Ҫɾ�� "+names.toString()+" ����Ϣ��?",
						"�Ƿ�ɾ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				//����û�ѡ�����ȷ��,��ɾ���ո�ѡ���ѧ����Ϣ
				if(select == 0){
					if(deleteStuById(idList)){
						
						   /*---------------------ɾ��������(JTable��)�û�ѡ�е���-----------------*/
						for(int i=selectedRows.length-1; i>= 0; i--){
							if(i <= stuList.size()){
								stuList.remove(selectedRows[i]);
							}
						}
						studentTableModel.setStudent(stuList);   //�������ñ������,���ػ����
						
						JOptionPane.showMessageDialog(null, names.toString()+"�Ѿ���ɾ����~");
					} else {
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��....");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "û��ѡ����ѧ����Ϣ,�����±���ѡ�к�,��ɾ��");
			}
			 
			
		}
		
	}
	
	/**
	 * ɾ��ѧ����Ϣ,ͨ��id��ɾ��     
	 * @param idList  ������Ҫɾ����ѧ����id��Ϣ
	 * @return
	 */
	private boolean deleteStuById(List<String> idList){
		  /*----------��װ����ɾ��sql���--------------*/
		String sql = "delete from student_info where id in(";
		int index = 0;
		for (String string : idList) {
			if(index == 0){
				sql += string;
			} else {
				sql += ","+string;
			}
			index++;
		}
		sql += ")";
		
		     /*---------------���ݿ����-------------*/
		try {
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(sql);
			int counts = preState.executeUpdate();  //������ INSERT��UPDATE �� DELETE ���   ����int,�����Ƿ���ɾ��������
			//�ж��Ƿ��û�ѡ���ѧ����Ϣ��ɾ����
			if(counts == idList.size()){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"�������ݿ����,ɾ��ʧ��....");
		} finally {
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		
		return false;
	}
	
	/**
	 * ��ʾȫ��ѧ����Ϣ  ��ť������
	 */
	class ShowAllStuListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			stuList = getAllStuInfo("");   //��ȡȫ��ѧ����Ϣ
			studentTableModel.setStudent(stuList);  //����TableModel������,���ػ����
			mainFrame.add(scrollPane,BorderLayout.CENTER);  //��JScrollPane������ӵ�JFrame��   �Դﵽ������ʾJTable��Ч��
		}
		
	}
	
	/**
	 * �˳� ��ť������
	 */
	class ExitListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //�رյ�ǰ����
		}
		
	}
	
}
