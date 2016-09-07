package com.houseParent;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.CommonOperate;
import com.houseParent.CheckRoom.RoomTableModel;
import com.model.CheckRoomRecord;
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
	// ����
	private JScrollPane scrollPane;

	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	
	/**
	 * ��������
	 */
	public static final int JTABLE_COLUMN_COUNTS = 4;

	HealthTableModel healthTableModel = new HealthTableModel();
	List<CheckRoomRecord> checkRoomRecordList = new ArrayList<CheckRoomRecord>();

	/**
	 * ����һά������Ϊ�б���
	 */
	Object[] columnTitle = new Object[] { "���Һ�", "�����������", "����", "��ע" };

	// ���췽��
	public CheckHealth() {
		init();
	}

	// ��ʼ��
	private void init() {

		// ����
		roomLabel.setBounds(10, 10, 80, 30);
		roomNumBobox.setBounds(100, 10, 80, 30);
		evaluationLabel.setBounds(250, 10, 80, 30);
		evaluationBoBox.setBounds(320, 10, 80, 30);
		remarkLabel.setBounds(10, 64, 80, 30);
		remarkText.setBounds(100, 50, 300, 60);
		addHealthRecords.setBounds(450, 10, 300, 40);
		exitBtn.setBounds(550, 70, 80, 40);

		// ������
		mainFrame.add(roomLabel);
		mainFrame.add(evaluationLabel);
		mainFrame.add(remarkLabel);
		mainFrame.add(roomNumBobox);
		mainFrame.add(evaluationBoBox);
		mainFrame.add(remarkText);
		mainFrame.add(addHealthRecords);
		mainFrame.add(exitBtn);

		healthTable.setModel(healthTableModel);  //����JTable��TableModel
		initChooser();  //��ʼ�������б�
		
		// ���ScrollPane
		scrollPane = new JScrollPane(healthTable); // ��ӱ����������
		scrollPane.setBounds(10, 120, 770, 330); // ����ScrollPane�ĳߴ�
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

	// ��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		// ����ȫ������ ����:����,����,��С
		CommonOperate.InitGlobalFont(new Font("����", Font.BOLD, 20));
		new CheckHealth();
	}

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
				    System.out.println("���½���");
				}

			});
		}

		//����JTable������
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		//����JTable������
		public int getRowCount() {
			return healthRoomRecordList.size();
		}

		//��List���ó�rowIndex��columnIndex����ʾ��ֵ     �������ø�TableModelָ����Ԫ���ֵ
		public Object getValueAt(int rowIndex, int columnIndex) {
			//��ȡ��ǰ�е�HealthRoomRecord
			HealthRoomRecord healthRoomRecord = healthRoomRecordList.get(rowIndex); 
			switch (columnIndex) {
			case 0:
				return healthRoomRecord.getRoomNum();  //�������Һ�
			case 1:
				return healthRoomRecord.getGoodOrBad(); //��������
			case 2: 
				return healthRoomRecord.getDateInfo();  //��������
			case 3:
				return healthRoomRecord.getRemarks();   //���ر�ע
			default:
				break;
			}
			
			return "";
		}
		
		//����JTable������      ��������
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
		
		evaluationBoBox.addItem(" ��");
		evaluationBoBox.addItem(" ��");
		evaluationBoBox.addItem(" ��");
	}
	
}
