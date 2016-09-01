package com.houseParent;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import com.CommonOperate;
import com.dataControl.DatabaseConnect;
import com.model.CheckRoomRecord;

/**
 * 2016��9��1��9:47:52 ����
 * 
 * @author XFHY
 * 
 */
public class CheckRoom {

	JFrame mainFrame = new JFrame("����");
	
	JLabel roomNumLabel = new JLabel("���Һ�:");
	JLabel isFullCountLabel = new JLabel("�����Ƿ���ȫ:");
	
	//����ѡ��  �����б�
	JComboBox<String> roomNumBobox = new JComboBox<String>();
	//�����Ƿ���������
	JComboBox<String> isFullRoomBobox = new JComboBox<String>();
	//��Ӽ�¼��ť
	JButton addCheckRoRecord = new JButton("��Ӳ��޼�¼");
	
	JTable roomTable = new JTable();
	private JScrollPane scrollPane;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	public static final int JTABLE_COLUMN_COUNTS = 3;
	RoomTableModel roomTableModel = new RoomTableModel();
	List<CheckRoomRecord> checkRoomRecordList = new ArrayList<CheckRoomRecord>();
	
	/**
	 * ����һά������Ϊ�б���
	 */
	Object[] columnTitle = new Object[]{"���Һ�","�����Ƿ���ȫ","����"};
	
	// ���췽��
	public CheckRoom() {
		init();
	}

	// ��ʼ��
	private void init() {
		
		initChooser();//��ʼ�������б�
		initTable();  //�������ݿ�,�����ݿ��еĲ�����Ϣ�ŵ�JTable
		roomTableModel.setCheckRoomRecord(checkRoomRecordList);  //��������
		
		//����
		roomNumLabel.setBounds(10, 10, 80, 30);
		roomNumBobox.setBounds(90, 10, 100, 30);
		isFullCountLabel.setBounds(230, 10, 150, 30);
		isFullRoomBobox.setBounds(370, 10, 70, 30);
		addCheckRoRecord.setBounds(480, 10, 160, 30);
		mainFrame.add(roomNumLabel);
		mainFrame.add(roomNumBobox);
		mainFrame.add(isFullCountLabel);
		mainFrame.add(isFullRoomBobox);
		mainFrame.add(addCheckRoRecord);
		
		addCheckRoRecord.addActionListener(new AddCheckRoRecordListener());
		
		roomTable.setModel(roomTableModel);   //����TableModel
		roomTable.setRowHeight(30);           //����JTable�и�
		
		// ��ֹ���������֮�佻��
		JTableHeader tableHeader = roomTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		scrollPane = new JScrollPane(roomTable); // ��ӱ����������
		scrollPane.setBounds(10, 80, 770, 370);  //����ScrollPane�ĳߴ�
		mainFrame.add(scrollPane);
		
		// ����ˮƽ��������ʾ��ʽ
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// ���ô�ֱ��������ʾ��ʽ
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mainFrame.setLayout(null);
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	/**
	 * ��ʼ��������
	 */
	private void initChooser() {
		int temp = 1;
		for (int d = 1; d <= StuAdmission.DORM_FLOOR; d++) {
			for (int i = 1; i <= StuAdmission.FLOOR_ROOM_COUNT; i++) {
				temp = d * 100 + i;
				roomNumBobox.addItem(temp + "");
			}
		}
		
		isFullRoomBobox.addItem("��");
		isFullRoomBobox.addItem("��");
	}

	/**
	 * ��ʾUI����
	 */
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ������
	public static void main(String[] args) {
		// ����ȫ������ ����:����,����,��С
		CommonOperate.InitGlobalFont(new Font("����", Font.BOLD, 20));
		new CheckRoom();
	}

	/**
	 * ��չAbstractTableModel�����ڽ�һ��List<CheckRoomRecord>���ϰ�װ��TableModel
	 *  JTable��ʵ���ǻ���MVC��, ����JTabel��������ʾ��һ��������model��,
	 *  JTable#setModel(TableModel dataModel)����������model��,
	 *  ����������붯̬��ʾ����, ����Ҫʵ��һ��TableMode
	 */
	class RoomTableModel extends AbstractTableModel{

		//����һ��CheckRoomRecord(���޼�¼����)���б�
		List<CheckRoomRecord> checkRecordList = new ArrayList<CheckRoomRecord>();
		
		//����CheckRoomRecord�б�,ͬʱ֪ͨJTable���ݶ����Ѹ���,�ػ����
		public void setCheckRoomRecord(final List<CheckRoomRecord> list){
			//invokeLater()����:���� doRun.run() �� AWT �¼�ָ���߳����첽ִ�С������й���� AWT �¼��������ŷ�����
			//�˷���Ӧ����Ӧ�ó����߳���Ҫ���¸� GUI ʱʹ��
			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					checkRecordList = list;
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
			return checkRecordList.size();
		}

		//��List���ó�rowIndex��columnIndex����ʾ��ֵ     �������ø�TableModelָ����Ԫ���ֵ
		public Object getValueAt(int rowIndex, int columnIndex) {
			CheckRoomRecord checkRoomRecord = checkRecordList.get(rowIndex);  //��õ�ǰ�е�CheckRoomRecord
			switch (columnIndex) {
			case 0:
				return checkRoomRecord.getRoomNum();  //��һ��   ���ұ��
			case 1:
				return checkRoomRecord.isFullRoom();  //�ڶ���   �����Ƿ�������
			case 2:
				return checkRoomRecord.getDateInfo(); //������   ���޵�����
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
			default:
				break;
			}
			return "��";
		}
		
		//��дisCellEditable()����,����false,��ÿ����Ԫ�񲻿ɱ༭
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}
		
	}
	
	/**
	 * �������ݿ�,�����ݿ��еĲ�����Ϣ�ŵ�JTable   �����ݱ��浽List<CheckRoomRecord> checkRoomRecordList   ������
	 */
	private void initTable(){
		String sql = "select * from insp_bedr";  //��ѯȫ���Ĳ��޼�¼
		
		if(conn != null){
			DatabaseConnect.closeConnection(conn);
		}
		if(preState != null){
			DatabaseConnect.closeStatement(preState);
		}
		if(resultSet != null){
			DatabaseConnect.closeResultset(resultSet);
		}
		
		try {
			conn = DatabaseConnect.connDatabase();   //�������ݿ�
			preState = conn.prepareStatement(sql);   //�õ�
			resultSet = preState.executeQuery();     //��ѯ���ݿ�
			while(resultSet.next()){   //�ж��Ƿ�����һ��
				CheckRoomRecord checkRoomRecord = new CheckRoomRecord();
				checkRoomRecord.setRoomNum(resultSet.getString("roomnum"));
				checkRoomRecord.setFullRoom(resultSet.getString("complete"));
				checkRoomRecord.setDateInfo(resultSet.getString("dateinfo"));
				checkRoomRecordList.add(checkRoomRecord);   //��ӵ��б�
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"��ѯ���ݿ����~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
	}
	
	//������Ҽ�¼ ��ť������
	class AddCheckRoRecordListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			// �����ӳɹ�
			if (addRecord()) {
				checkRoomRecordList.clear(); // ���֮ǰ���б��е�����
				initTable(); // �������ݿ�,�����б��е�����
				roomTableModel.setCheckRoomRecord(checkRoomRecordList); // �����б�����
				mainFrame.add(scrollPane); // �ٴ��������µ�ScrollPane
			}

		}

	}
	
	/**
	 * �����ݿ�����Ӽ�¼
	 */
	private boolean addRecord(){
		//��ȡ�û�ѡ�������
		String roomnum = roomNumBobox.getItemAt(roomNumBobox.getSelectedIndex());
		//��ȡ�û�ѡ����Ƿ���������
		String complete = isFullRoomBobox.getItemAt(isFullRoomBobox.getSelectedIndex());
		
		//��ȡ��ǰ����ʱ��
		Calendar cal = Calendar.getInstance();
		String dateinfo = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+
		cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
		//System.out.println(dateinfo);
		
		String sql = "insert into insp_bedr values(?,?,?)";
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(preState != null){
			try {
				preState.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		int count = -1;
		try {
			conn = DatabaseConnect.connDatabase();   //�������ݿ�
			preState = conn.prepareStatement(sql);   //�õ�
			preState.setString(1, roomnum);
			preState.setString(2, complete);
			preState.setString(3, dateinfo);
			
			count = preState.executeUpdate();  //ִ��sql���
			if(count > 0){
				JOptionPane.showMessageDialog(null, "��ӳɹ� ! ! !");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "���ʧ�� ~ ~");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"��ӵ����ݿ����~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		return false;
	}
	
}
