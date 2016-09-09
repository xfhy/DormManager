package com.houseParent;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.CommonOperate;
import com.dataControl.DatabaseConnect;
import com.model.HealthRoomRecord;

/**
 * 2016��9��6��11:03:04 �������
 * 
 * @author xfhy
 * 
 */
public class CheckHealth {

	JFrame mainFrame = new JFrame("�����������");

	JLabel roomLabel = new JLabel("���Һ�:");
	JLabel evaluationLabel = new JLabel("����:");
	JLabel remarkLabel = new JLabel("��ע:");
	JLabel limitLabel = new JLabel("����30��");

	// ����ѡ�� �����б�
	JComboBox<String> roomNumBobox = new JComboBox<String>();
	// ����ѡ��
	JComboBox<String> evaluationBoBox = new JComboBox<String>();
	// �ı�����
	JTextArea remarkText = new JTextArea(2, 30);

	// ��ť��
	JButton addHealthRecords = new JButton("��¼����������Ϣ");
	JButton exitBtn = new JButton("�˳�");

	// ���
	JTable healthTable = new JTable();
	// ������
	private JScrollPane tableScrollPane;
	//��ע(�ı��������)
	private JScrollPane textScrollPane = new JScrollPane(remarkText);

	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;

	/**
	 * ��������
	 */
	public static final int JTABLE_COLUMN_COUNTS = 4;

	// JTable��Ҫ�õ���TableModel HealthTableModel���Լ�����ļ̳���AbstractTableModel����
	HealthTableModel healthTableModel = new HealthTableModel();
	// ���Ǵ洢JTable��ÿһ�е����ݵļ��� һ�о���һ��Ԫ��
	List<HealthRoomRecord> healthRoomRecordList = new ArrayList<HealthRoomRecord>();

	/**
	 * ����һά������Ϊ�б���
	 */
	Object[] columnTitle = new Object[] { "���Һ�", "�����������", "����", "��ע" };

	// ���췽��
	public CheckHealth() {
		init();
	}

	// ��ʼ������
	private void init() {

		// ����
		roomLabel.setBounds(10, 10, 80, 30);
		roomNumBobox.setBounds(100, 10, 80, 30);
		evaluationLabel.setBounds(250, 10, 80, 30);
		evaluationBoBox.setBounds(320, 10, 80, 30);
		remarkLabel.setBounds(10, 64, 80, 30);
		textScrollPane.setBounds(100, 50, 300, 60);
		limitLabel.setFont(new Font("����", Font.PLAIN, 16));
		limitLabel.setBounds(400,50,100,100);
		addHealthRecords.setBounds(450, 10, 300, 40);
		exitBtn.setBounds(550, 70, 80, 40);

		
		// ������
		mainFrame.add(roomLabel);
		mainFrame.add(evaluationLabel);
		mainFrame.add(remarkLabel);
		mainFrame.add(roomNumBobox);
		mainFrame.add(evaluationBoBox);
		mainFrame.add(textScrollPane);
		mainFrame.add(limitLabel);
		mainFrame.add(addHealthRecords);
		mainFrame.add(exitBtn);

		// ע�ᰴť������
		addHealthRecords.addActionListener(new AddHealthRecordsListener());
		exitBtn.addActionListener(new ExitBtnListener());
		remarkText.addKeyListener(new wordsNumberChangerListener());

		
		healthTable.setModel(healthTableModel); // ����JTable��TableModel
		initTable();    //��ʼ��JTable
		healthTableModel.setHealthRoomRecord(healthRoomRecordList);  //��������,��ʾ��JTable��
		healthTable.setRowHeight(30);           //����JTable�и�
		
		// ��ֹ���������֮�佻��
		JTableHeader tableHeader = healthTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		
		//����JTableÿһ�е��п�
		setColumnWidth(0,80,80,80);  
		setColumnWidth(1,150,150,150);  
		setColumnWidth(2,200,200,200);  
		setColumnWidth(3,1000,1000,1000);  
		
		/**
		 * setAutoResizeMode():��������Ĵ�Сʱ�����ñ���Զ�����ģʽ
		 * AUTO_RESIZE_OFF�����Զ������еĿ�ȡ����е��ܿ�ȳ��� Viewport �Ŀ��ʱ��ʹ��ˮƽ����������Ӧ�еĿ�ȡ�
		 * ��� JTable û�з���� JScrollPane �У�����ܵ��¸ñ��һ���ֲ��ɼ��� 
		 */
		healthTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //���ˮƽ����������ʾ������
		
		remarkText.setLineWrap(true);        //���õ���β��ʱ����
		
		initChooser(); // ��ʼ�������б�

		// ���ScrollPane
		tableScrollPane = new JScrollPane(healthTable); // ��ӱ����������
		tableScrollPane.setBounds(10, 120, 770, 330); // ����ScrollPane�ĳߴ�
		mainFrame.add(tableScrollPane);

		// ����ˮƽ��������ʾ��ʽ
		tableScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// ���ô�ֱ��������ʾ��ʽ
		tableScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		

		mainFrame.setLayout(null);
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

	/*//������
	public static void main(String[] args) {
		// ����ȫ������ ����:����,����,��С
		CommonOperate.InitGlobalFont(new Font("����", Font.BOLD, 20));
		new CheckHealth();
	}
*/
	/**
	 * ��չAbstractTableModel�����ڽ�һ��List<HealthRoomRecord>���ϰ�װ��TableModel
	 * JTable��ʵ���ǻ���MVC��, ����JTabel��������ʾ��һ��������model��, JTable��setModel(TableModel
	 * dataModel)����������model��, ����������붯̬��ʾ����, ����Ҫʵ��һ��TableMode
	 */
	class HealthTableModel extends AbstractTableModel {

		// ����һ��HealthRoomRecord(������¼����)���б�
		List<HealthRoomRecord> healthRoomRecordList = new ArrayList<HealthRoomRecord>();

		// ����HealthRoomRecord�б�,ͬʱ֪ͨJTable���ݶ����Ѹ���,�ػ����
		public void setHealthRoomRecord(final List<HealthRoomRecord> list) {
			// invokeLater()����:���� doRun.run() �� AWT �¼�ָ���߳����첽ִ�С������й���� AWT
			// �¼��������ŷ�����
			// �˷���Ӧ����Ӧ�ó����߳���Ҫ���¸� GUI ʱʹ��
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					healthRoomRecordList = list;
					fireTableDataChanged(); // ֪ͨJTable���ݶ����Ѹ���,�ػ����
					//System.out.println("���½���");
				}

			});
		}

		// ����JTable������
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		// ����JTable������
		public int getRowCount() {
			return healthRoomRecordList.size();
		}

		// ��List���ó�rowIndex��columnIndex����ʾ��ֵ �������ø�TableModelָ����Ԫ���ֵ
		public Object getValueAt(int rowIndex, int columnIndex) {
			// ��ȡ��ǰ�е�HealthRoomRecord
			HealthRoomRecord healthRoomRecord = healthRoomRecordList
					.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return healthRoomRecord.getRoomNum(); // �������Һ�
			case 1:
				return healthRoomRecord.getGoodOrBad(); // ��������
			case 2:
				return healthRoomRecord.getDateInfo(); // ��������
			case 3:
				return healthRoomRecord.getRemarks(); // ���ر�ע
			default:
				break;
			}

			return "";
		}

		// ����JTable������ ��������
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
			default:
				break;
			}
			return "��";
		}

		// ��дisCellEditable()����,����false,��ÿ����Ԫ�񲻿ɱ༭
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}

	}

	/**
	 * ����ĳһ�е��п� 
	 * ����:ĳһ��,��ѡ���,�����,��С���
	 */
	private void setColumnWidth(int xColumn,int columnPreferredWidth,int columnMaxWidth,int columnMinWidth){
		TableColumn tempColumn = healthTable.getColumnModel().getColumn(xColumn);   //���JTable��ĳһ��
		tempColumn.setPreferredWidth(columnPreferredWidth);   //������ѡ���
		tempColumn.setMaxWidth(columnMaxWidth);               //���������
		tempColumn.setMinWidth(columnMinWidth);               //������С���
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

		evaluationBoBox.addItem("��");
		evaluationBoBox.addItem("��");
		evaluationBoBox.addItem("��");
	}

	/**
	 * ʵ�������������¼�İ�ť������
	 */
	class AddHealthRecordsListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			
			//�ж��û�����ı�ע�����Ƿ����30����,���ڵĻ�,��ֱ�Ӳ���ӵ����ݿ�
			if (remarkText.getText().length() > 30) {
				JOptionPane.showMessageDialog(null, "��ע����������������,������������30������!\n������"+
			     remarkText.getText().length()+"����");
			} else {
				// �������߳� �����ݿ��в��� ������¼�� ����
				new Thread(new Runnable() {

					public void run() {
						if(addRecords()){   // �����ݿ��в��� ������¼�� ����
							healthRoomRecordList.clear();   //��ӳɹ����֮ǰ���������
							initTable();   //��ȡ���ݿ�������������
							//����HealthRoomRecord�б�,ͬʱ֪ͨJTable���ݶ����Ѹ���,�ػ����
							healthTableModel.setHealthRoomRecord(healthRoomRecordList);  
							mainFrame.add(tableScrollPane);
						}
					}

				}).start();
			}
			
		}

	}

	/**
	 * �˳���ť������
	 */
	class ExitBtnListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			mainFrame.dispose(); // �رյ�ǰ����
		}

	}

	/**
	 * ����������   ʵʱ����JTextArea�û����������
	 */
	class wordsNumberChangerListener implements KeyListener{

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent arg0) {
			int length = remarkText.getText().length();   //��ȡ�û�����ı�ע������
			limitLabel.setText("����30��("+length+")");
		}

		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub
			
		}


	}
	
	
	/**
	 * �����ݿ��в��� ������¼�� ����
	 */
	private boolean addRecords() {

		// ��ȡ��ǰ����ʱ��
		Calendar cal = Calendar.getInstance();
		String dateinfo = cal.get(Calendar.YEAR) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DAY_OF_MONTH) + " "
				+ cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ cal.get(Calendar.MINUTE);

		closeNeedUse();   //�ж�һ����Ҫ�õ���Connection,PreparedStatement,ResultSet ����,�����Ϊ����ر�һ��

		int count = -1; // ִ�����ݿ����֮��,����ִ�гɹ�������

		// ��Ҫִ�е���� ������¼�� ���ݿ����
		String addSql = "insert into hyg_info(roomnum,gob,dateinfo,remarks) values(?,?,?,?)";
		try {
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(addSql);
			preState.setString(1,
					roomNumBobox.getItemAt(roomNumBobox.getSelectedIndex()));
			preState.setString(2, evaluationBoBox.getItemAt(evaluationBoBox
					.getSelectedIndex()));
			preState.setString(3, dateinfo);
			preState.setString(4, remarkText.getText());
			count = preState.executeUpdate();
			if (count > 0) {
				JOptionPane.showMessageDialog(null, "��ӳɹ� ! ! !");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "���ʧ�� ~ ~");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��ӵ����ݿ����~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}

		return false;
	}
	
	/**
	 * �������ݿ�,�����ݿ��е�������Ϣ�ŵ�JTable   �����ݱ��浽List<CheckRoomRecord> checkRoomRecordList  ������
	 */
	private void initTable(){
		String initSql = "select * from hyg_info";
		try {
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(initSql);
			resultSet = preState.executeQuery();    //ִ�����ݿ��ѯ�����,���浽resultSet����
			while(resultSet.next()){    //�ж��Ƿ�����һ��
				HealthRoomRecord healthRoomRecord = new HealthRoomRecord();
				healthRoomRecord.setRoomNum(resultSet.getString("roomnum"));
				healthRoomRecord.setGoodOrBad(resultSet.getString("gob"));
				healthRoomRecord.setDateInfo(resultSet.getString("dateinfo"));
				healthRoomRecord.setRemarks(resultSet.getString("remarks"));
				healthRoomRecordList.add(healthRoomRecord);
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
	
	/**
	 * �ж�һ����Ҫ�õ���Connection,PreparedStatement,ResultSet ����,�����Ϊ����ر�һ��
	 */
	private void closeNeedUse(){
		if(resultSet != null){
			DatabaseConnect.closeResultset(resultSet);
		}
		if(preState != null){
			DatabaseConnect.closeStatement(preState);
		}
		if(conn != null){
			DatabaseConnect.closeConnection(conn);
		}
		
	}
	
	
}
