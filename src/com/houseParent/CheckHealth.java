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
	JLabel limitLabel = new JLabel("至多30字");

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
	// 表格滚动
	private JScrollPane tableScrollPane;
	//备注(文本区域滚动)
	private JScrollPane textScrollPane = new JScrollPane(remarkText);

	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;

	/**
	 * 表格的列数
	 */
	public static final int JTABLE_COLUMN_COUNTS = 4;

	// JTable需要用到的TableModel HealthTableModel是自己定义的继承自AbstractTableModel的类
	HealthTableModel healthTableModel = new HealthTableModel();
	// 这是存储JTable的每一行的数据的集合 一行就是一个元素
	List<HealthRoomRecord> healthRoomRecordList = new ArrayList<HealthRoomRecord>();

	/**
	 * 定义一维数组作为列标题
	 */
	Object[] columnTitle = new Object[] { "寝室号", "寝室卫生情况", "日期", "备注" };

	// 构造方法
	public CheckHealth() {
		init();
	}

	// 初始化界面
	private void init() {

		// 布局
		roomLabel.setBounds(10, 10, 80, 30);
		roomNumBobox.setBounds(100, 10, 80, 30);
		evaluationLabel.setBounds(250, 10, 80, 30);
		evaluationBoBox.setBounds(320, 10, 80, 30);
		remarkLabel.setBounds(10, 64, 80, 30);
		textScrollPane.setBounds(100, 50, 300, 60);
		limitLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		limitLabel.setBounds(400,50,100,100);
		addHealthRecords.setBounds(450, 10, 300, 40);
		exitBtn.setBounds(550, 70, 80, 40);

		
		// 添加组件
		mainFrame.add(roomLabel);
		mainFrame.add(evaluationLabel);
		mainFrame.add(remarkLabel);
		mainFrame.add(roomNumBobox);
		mainFrame.add(evaluationBoBox);
		mainFrame.add(textScrollPane);
		mainFrame.add(limitLabel);
		mainFrame.add(addHealthRecords);
		mainFrame.add(exitBtn);

		// 注册按钮监听器
		addHealthRecords.addActionListener(new AddHealthRecordsListener());
		exitBtn.addActionListener(new ExitBtnListener());
		remarkText.addKeyListener(new wordsNumberChangerListener());

		
		healthTable.setModel(healthTableModel); // 设置JTable的TableModel
		initTable();    //初始化JTable
		healthTableModel.setHealthRoomRecord(healthRoomRecordList);  //设置数据,显示到JTable上
		healthTable.setRowHeight(30);           //设置JTable行高
		
		// 禁止表格列与列之间交换
		JTableHeader tableHeader = healthTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		
		//设置JTable每一列的列宽
		setColumnWidth(0,80,80,80);  
		setColumnWidth(1,150,150,150);  
		setColumnWidth(2,200,200,200);  
		setColumnWidth(3,1000,1000,1000);  
		
		/**
		 * setAutoResizeMode():当调整表的大小时，设置表的自动调整模式
		 * AUTO_RESIZE_OFF：不自动调整列的宽度。当列的总宽度超过 Viewport 的宽度时，使用水平滚动条来适应列的宽度。
		 * 如果 JTable 没有封闭在 JScrollPane 中，则可能导致该表的一部分不可见。 
		 */
		healthTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //解决水平滚动条不显示的问题
		
		remarkText.setLineWrap(true);        //设置到行尾部时换行
		
		initChooser(); // 初始化下拉列表

		// 添加ScrollPane
		tableScrollPane = new JScrollPane(healthTable); // 添加表到滚动面板内
		tableScrollPane.setBounds(10, 120, 770, 330); // 设置ScrollPane的尺寸
		mainFrame.add(tableScrollPane);

		// 设置水平滚动条显示形式
		tableScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// 设置垂直滚动条显示形式
		tableScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textScrollPane
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

	/*//主函数
	public static void main(String[] args) {
		// 设置全局字体 参数:字体,粗体,大小
		CommonOperate.InitGlobalFont(new Font("宋体", Font.BOLD, 20));
		new CheckHealth();
	}
*/
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
					//System.out.println("更新界面");
				}

			});
		}

		// 返回JTable的列数
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		// 返回JTable的行数
		public int getRowCount() {
			return healthRoomRecordList.size();
		}

		// 从List中拿出rowIndex行columnIndex列显示的值 用于设置该TableModel指定单元格的值
		public Object getValueAt(int rowIndex, int columnIndex) {
			// 获取当前行的HealthRoomRecord
			HealthRoomRecord healthRoomRecord = healthRoomRecordList
					.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return healthRoomRecord.getRoomNum(); // 返回寝室号
			case 1:
				return healthRoomRecord.getGoodOrBad(); // 返回评价
			case 2:
				return healthRoomRecord.getDateInfo(); // 返回日期
			case 3:
				return healthRoomRecord.getRemarks(); // 返回备注
			default:
				break;
			}

			return "";
		}

		// 设置JTable的列名 返回列名
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
	 * 设置某一列的列宽 
	 * 参数:某一列,首选宽度,最大宽度,最小宽度
	 */
	private void setColumnWidth(int xColumn,int columnPreferredWidth,int columnMaxWidth,int columnMinWidth){
		TableColumn tempColumn = healthTable.getColumnModel().getColumn(xColumn);   //获得JTable的某一列
		tempColumn.setPreferredWidth(columnPreferredWidth);   //设置首选宽度
		tempColumn.setMaxWidth(columnMaxWidth);               //设置最大宽度
		tempColumn.setMinWidth(columnMinWidth);               //设置最小宽度
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

		evaluationBoBox.addItem("优");
		evaluationBoBox.addItem("良");
		evaluationBoBox.addItem("差");
	}

	/**
	 * 实现添加卫生检查记录的按钮监听器
	 */
	class AddHealthRecordsListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			
			//判断用户输入的备注字数是否大于30个字,大于的话,则直接不添加到数据库
			if (remarkText.getText().length() > 30) {
				JOptionPane.showMessageDialog(null, "备注输入字数超过限制,请限制字数在30字以内!\n现在是"+
			     remarkText.getText().length()+"个字");
			} else {
				// 创建子线程 向数据库中插入 卫生记录的 数据
				new Thread(new Runnable() {

					public void run() {
						if(addRecords()){   // 向数据库中插入 卫生记录的 数据
							healthRoomRecordList.clear();   //添加成功则把之前的数据清空
							initTable();   //获取数据库中卫生的数据
							//设置HealthRoomRecord列表,同时通知JTable数据对象已更改,重绘界面
							healthTableModel.setHealthRoomRecord(healthRoomRecordList);  
							mainFrame.add(tableScrollPane);
						}
					}

				}).start();
			}
			
		}

	}

	/**
	 * 退出按钮监听器
	 */
	class ExitBtnListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			mainFrame.dispose(); // 关闭当前窗口
		}

	}

	/**
	 * 按键监听器   实时监听JTextArea用户输入的字数
	 */
	class wordsNumberChangerListener implements KeyListener{

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent arg0) {
			int length = remarkText.getText().length();   //获取用户输入的备注的字数
			limitLabel.setText("至多30字("+length+")");
		}

		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub
			
		}


	}
	
	
	/**
	 * 向数据库中插入 卫生记录的 数据
	 */
	private boolean addRecords() {

		// 获取当前日期时间
		Calendar cal = Calendar.getInstance();
		String dateinfo = cal.get(Calendar.YEAR) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DAY_OF_MONTH) + " "
				+ cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ cal.get(Calendar.MINUTE);

		closeNeedUse();   //判断一下需要用到的Connection,PreparedStatement,ResultSet 对象,如果不为空则关闭一下

		int count = -1; // 执行数据库语句之后,返回执行成功的行数

		// 需要执行的添加 卫生记录的 数据库语句
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
				JOptionPane.showMessageDialog(null, "添加成功 ! ! !");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "添加失败 ~ ~");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "添加到数据库出错~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}

		return false;
	}
	
	/**
	 * 连接数据库,将数据库中的卫生信息放到JTable   将数据保存到List<CheckRoomRecord> checkRoomRecordList  集合中
	 */
	private void initTable(){
		String initSql = "select * from hyg_info";
		try {
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(initSql);
			resultSet = preState.executeQuery();    //执行数据库查询的语句,并存到resultSet里面
			while(resultSet.next()){    //判断是否有下一个
				HealthRoomRecord healthRoomRecord = new HealthRoomRecord();
				healthRoomRecord.setRoomNum(resultSet.getString("roomnum"));
				healthRoomRecord.setGoodOrBad(resultSet.getString("gob"));
				healthRoomRecord.setDateInfo(resultSet.getString("dateinfo"));
				healthRoomRecord.setRemarks(resultSet.getString("remarks"));
				healthRoomRecordList.add(healthRoomRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"查询数据库出错~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
	}
	
	/**
	 * 判断一下需要用到的Connection,PreparedStatement,ResultSet 对象,如果不为空则关闭一下
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
