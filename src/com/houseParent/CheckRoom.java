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
 * 2016年9月1日9:47:52 查寝
 * 
 * @author XFHY
 * 
 */
public class CheckRoom {

	JFrame mainFrame = new JFrame("查寝");
	
	JLabel roomNumLabel = new JLabel("寝室号:");
	JLabel isFullCountLabel = new JLabel("人数是否齐全:");
	
	//寝室选择  下拉列表
	JComboBox<String> roomNumBobox = new JComboBox<String>();
	//寝室是否人数到齐
	JComboBox<String> isFullRoomBobox = new JComboBox<String>();
	//添加记录按钮
	JButton addCheckRoRecord = new JButton("添加查寝记录");
	
	JTable roomTable = new JTable();
	private JScrollPane scrollPane;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	public static final int JTABLE_COLUMN_COUNTS = 3;
	RoomTableModel roomTableModel = new RoomTableModel();
	List<CheckRoomRecord> checkRoomRecordList = new ArrayList<CheckRoomRecord>();
	
	/**
	 * 定义一维数组作为列标题
	 */
	Object[] columnTitle = new Object[]{"寝室号","人数是否齐全","日期"};
	
	// 构造方法
	public CheckRoom() {
		init();
	}

	// 初始化
	private void init() {
		
		initChooser();//初始化下拉列表
		initTable();  //连接数据库,将数据库中的查寝信息放到JTable
		roomTableModel.setCheckRoomRecord(checkRoomRecordList);  //设置数据
		
		//布局
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
		
		roomTable.setModel(roomTableModel);   //设置TableModel
		roomTable.setRowHeight(30);           //设置JTable行高
		
		// 禁止表格列与列之间交换
		JTableHeader tableHeader = roomTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);

		scrollPane = new JScrollPane(roomTable); // 添加表到滚动面板内
		scrollPane.setBounds(10, 80, 770, 370);  //设置ScrollPane的尺寸
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
		
		isFullRoomBobox.addItem("是");
		isFullRoomBobox.addItem("否");
	}

	/**
	 * 显示UI界面
	 */
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// 主函数
	public static void main(String[] args) {
		// 设置全局字体 参数:字体,粗体,大小
		CommonOperate.InitGlobalFont(new Font("宋体", Font.BOLD, 20));
		new CheckRoom();
	}

	/**
	 * 扩展AbstractTableModel，用于将一个List<CheckRoomRecord>集合包装成TableModel
	 *  JTable的实现是基于MVC的, 所以JTabel的数据显示是一个独立的model的,
	 *  JTable#setModel(TableModel dataModel)就是来设置model的,
	 *  所以你如果想动态显示数据, 你需要实现一个TableMode
	 */
	class RoomTableModel extends AbstractTableModel{

		//保存一个CheckRoomRecord(查寝记录的类)的列表
		List<CheckRoomRecord> checkRecordList = new ArrayList<CheckRoomRecord>();
		
		//设置CheckRoomRecord列表,同时通知JTable数据对象已更改,重绘界面
		public void setCheckRoomRecord(final List<CheckRoomRecord> list){
			//invokeLater()方法:导致 doRun.run() 在 AWT 事件指派线程上异步执行。在所有挂起的 AWT 事件被处理后才发生。
			//此方法应该在应用程序线程需要更新该 GUI 时使用
			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					checkRecordList = list;
					fireTableDataChanged();  //通知JTable数据对象已更改,重绘界面
					//System.out.println("更新界面");
				}
				
			});
		}
		
		//返回JTable的列数
		public int getColumnCount() {
			return JTABLE_COLUMN_COUNTS;
		}

		//返回JTable的行数
		public int getRowCount() {
			return checkRecordList.size();
		}

		//从List中拿出rowIndex行columnIndex列显示的值     用于设置该TableModel指定单元格的值
		public Object getValueAt(int rowIndex, int columnIndex) {
			CheckRoomRecord checkRoomRecord = checkRecordList.get(rowIndex);  //获得当前行的CheckRoomRecord
			switch (columnIndex) {
			case 0:
				return checkRoomRecord.getRoomNum();  //第一列   寝室编号
			case 1:
				return checkRoomRecord.isFullRoom();  //第二列   寝室是否人数齐
			case 2:
				return checkRoomRecord.getDateInfo(); //第三列   查寝的日期
			default:
				break;
			}
			return null;
		}
		
		/**
		 * 设置JTable的列名      返回列名
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
			return "咦";
		}
		
		//重写isCellEditable()方法,返回false,让每个单元格不可编辑
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}
		
	}
	
	/**
	 * 连接数据库,将数据库中的查寝信息放到JTable   将数据保存到List<CheckRoomRecord> checkRoomRecordList   集合中
	 */
	private void initTable(){
		String sql = "select * from insp_bedr";  //查询全部的查寝记录
		
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
			conn = DatabaseConnect.connDatabase();   //连接数据库
			preState = conn.prepareStatement(sql);   //得到
			resultSet = preState.executeQuery();     //查询数据库
			while(resultSet.next()){   //判断是否有下一个
				CheckRoomRecord checkRoomRecord = new CheckRoomRecord();
				checkRoomRecord.setRoomNum(resultSet.getString("roomnum"));
				checkRoomRecord.setFullRoom(resultSet.getString("complete"));
				checkRoomRecord.setDateInfo(resultSet.getString("dateinfo"));
				checkRoomRecordList.add(checkRoomRecord);   //添加到列表
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
	
	//添加寝室记录 按钮监听器
	class AddCheckRoRecordListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			// 如果添加成功
			if (addRecord()) {
				checkRoomRecordList.clear(); // 清空之前的列表中的数据
				initTable(); // 连接数据库,更新列表中的数据
				roomTableModel.setCheckRoomRecord(checkRoomRecordList); // 设置列表数据
				mainFrame.add(scrollPane); // 再次添加这个新的ScrollPane
			}

		}

	}
	
	/**
	 * 向数据库中添加记录
	 */
	private boolean addRecord(){
		//获取用户选择的寝室
		String roomnum = roomNumBobox.getItemAt(roomNumBobox.getSelectedIndex());
		//获取用户选择的是否寝室已满
		String complete = isFullRoomBobox.getItemAt(isFullRoomBobox.getSelectedIndex());
		
		//获取当前日期时间
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
			conn = DatabaseConnect.connDatabase();   //连接数据库
			preState = conn.prepareStatement(sql);   //得到
			preState.setString(1, roomnum);
			preState.setString(2, complete);
			preState.setString(3, dateinfo);
			
			count = preState.executeUpdate();  //执行sql语句
			if(count > 0){
				JOptionPane.showMessageDialog(null, "添加成功 ! ! !");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "添加失败 ~ ~");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"添加到数据库出错~");
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		return false;
	}
	
}
