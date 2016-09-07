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
 * 2016年9月6日11:03:04 检查卫生
 * 
 * @author xfhy
 * 
 */
public class CheckHealth {

	JFrame mainFrame = new JFrame("寝室卫生检查");

	JLabel roomLabel = new JLabel("寝室号:");
	JLabel evaluationLabel = new JLabel("评价:");
	JLabel remarkLabel = new JLabel("备注:");

	// 寝室选择 下拉列表
	JComboBox<String> roomNumBobox = new JComboBox<String>();
	// 评价选择
	JComboBox<String> evaluationBoBox = new JComboBox<String>();
	// 文本区域
	JTextArea remarkText = new JTextArea(2, 30);

	// 按钮区
	JButton addHealthRecords = new JButton("记录寝室卫生信息");
	JButton exitBtn = new JButton("退出");

	// 表格
	JTable healthTable = new JTable();
	// 滚动
	private JScrollPane scrollPane;

	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	
	/**
	 * 表格的列数
	 */
	public static final int JTABLE_COLUMN_COUNTS = 4;

	HealthTableModel healthTableModel = new HealthTableModel();
	List<CheckRoomRecord> checkRoomRecordList = new ArrayList<CheckRoomRecord>();

	/**
	 * 定义一维数组作为列标题
	 */
	Object[] columnTitle = new Object[] { "寝室号", "寝室卫生情况", "日期", "备注" };

	// 构造方法
	public CheckHealth() {
		init();
	}

	// 初始化
	private void init() {

		// 布局
		roomLabel.setBounds(10, 10, 80, 30);
		roomNumBobox.setBounds(100, 10, 80, 30);
		evaluationLabel.setBounds(250, 10, 80, 30);
		evaluationBoBox.setBounds(320, 10, 80, 30);
		remarkLabel.setBounds(10, 64, 80, 30);
		remarkText.setBounds(100, 50, 300, 60);
		addHealthRecords.setBounds(450, 10, 300, 40);
		exitBtn.setBounds(550, 70, 80, 40);

		// 添加组件
		mainFrame.add(roomLabel);
		mainFrame.add(evaluationLabel);
		mainFrame.add(remarkLabel);
		mainFrame.add(roomNumBobox);
		mainFrame.add(evaluationBoBox);
		mainFrame.add(remarkText);
		mainFrame.add(addHealthRecords);
		mainFrame.add(exitBtn);

		healthTable.setModel(healthTableModel);  //设置JTable的TableModel
		initChooser();  //初始化下拉列表
		
		// 添加ScrollPane
		scrollPane = new JScrollPane(healthTable); // 添加表到滚动面板内
		scrollPane.setBounds(10, 120, 770, 330); // 设置ScrollPane的尺寸
		mainFrame.add(scrollPane);

		// 设置水平滚动条显示形式
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// 设置垂直滚动条显示形式
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		mainFrame.setLayout(null);
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // 窗口大小不可变
		showUI();
	}

	// 显示UI界面
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		// 设置全局字体 参数:字体,粗体,大小
		CommonOperate.InitGlobalFont(new Font("宋体", Font.BOLD, 20));
		new CheckHealth();
	}

	/**
	 * 扩展AbstractTableModel，用于将一个List<HealthRoomRecord>集合包装成TableModel
	 * JTable的实现是基于MVC的, 所以JTabel的数据显示是一个独立的model的, JTable的setModel(TableModel
	 * dataModel)就是来设置model的, 所以你如果想动态显示数据, 你需要实现一个TableMode
	 */
	class HealthTableModel extends AbstractTableModel {

		// 保存一个HealthRoomRecord(卫生记录的类)的列表
		List<HealthRoomRecord> healthRoomRecordList = new ArrayList<HealthRoomRecord>();

		// 设置HealthRoomRecord列表,同时通知JTable数据对象已更改,重绘界面
		public void setHealthRoomRecord(final List<HealthRoomRecord> list) {
			// invokeLater()方法:导致 doRun.run() 在 AWT 事件指派线程上异步执行。在所有挂起的 AWT
			// 事件被处理后才发生。
			// 此方法应该在应用程序线程需要更新该 GUI 时使用
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					healthRoomRecordList = list;
					fireTableDataChanged(); // 通知JTable数据对象已更改,重绘界面
				    System.out.println("更新界面");
				}

			});
		}

		//返回JTable的列数
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		//返回JTable的行数
		public int getRowCount() {
			return healthRoomRecordList.size();
		}

		//从List中拿出rowIndex行columnIndex列显示的值     用于设置该TableModel指定单元格的值
		public Object getValueAt(int rowIndex, int columnIndex) {
			//获取当前行的HealthRoomRecord
			HealthRoomRecord healthRoomRecord = healthRoomRecordList.get(rowIndex); 
			switch (columnIndex) {
			case 0:
				return healthRoomRecord.getRoomNum();  //返回寝室号
			case 1:
				return healthRoomRecord.getGoodOrBad(); //返回评价
			case 2: 
				return healthRoomRecord.getDateInfo();  //返回日期
			case 3:
				return healthRoomRecord.getRemarks();   //返回备注
			default:
				break;
			}
			
			return "";
		}
		
		//设置JTable的列名      返回列名
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
			return "咦";
		}

		// 重写isCellEditable()方法,返回false,让每个单元格不可编辑
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}
		
	}

	/**
	 * 初始化下拉框
	 */
	private void initChooser() {
		int temp = 1;
		for (int d = 1; d <= StuAdmission.DORM_FLOOR; d++) {
			for (int i = 1; i <= StuAdmission.FLOOR_ROOM_COUNT; i++) {
				temp = d * 100 + i;
				roomNumBobox.addItem(temp + "");
			}
		}
		
		evaluationBoBox.addItem(" 优");
		evaluationBoBox.addItem(" 良");
		evaluationBoBox.addItem(" 差");
	}
	
}
