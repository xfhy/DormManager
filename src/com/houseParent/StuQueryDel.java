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
 * 2016年8月22日17:59:42
 * 
 * 查询删除学生
 * 
 * @author XFHY
 *
 */
public class StuQueryDel {
	
	JFrame mainFrame = new JFrame("查询删除");
	
	//上面的布局
	JLabel findRoomLable = new JLabel("查询寝室信息",JLabel.LEFT);
	JLabel roomNumLable = new JLabel("寝室号");
	JTextField roomNumField = new JTextField(6);   //输入框   用来输入需要查询的寝室号
	JButton queryBtn = new JButton("查询");
	JButton deleteBtn = new JButton("删除");
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
	
	JTable roomTable = null;
	private JScrollPane scrollPane;
	//private ResultSetTableModel model;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	//定义一维数组作为列标题
	Object[] columnTitle = new Object[]{"学号","姓名","性别","班级","学院","床位","寝室号"};
	
	// 构造方法
	public StuQueryDel() {
		init();
	}

	// 初始化
	private void init() {
		topPanel.add(findRoomLable);
		topPanel.add(roomNumLable);
		topPanel.add(roomNumField);
		topPanel.add(queryBtn);
		topPanel.add(deleteBtn);
		
		List<Student> stuList = new ArrayList<Student>();
		stuList = getAllStuInfo();   //初始化List集合,这些信息是学生的信息   需要添加到JTable中的
		
		roomTable = new JTable(getRowInfo(stuList),columnTitle);
		
		scrollPane = new JScrollPane(roomTable);  //添加表到滚动面板内
		
		//设置水平滚动条显示形式
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//设置垂直滚动条显示形式
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mainFrame.add(topPanel,BorderLayout.NORTH);
		mainFrame.add(scrollPane,BorderLayout.CENTER);
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

	// 主函数
	public static void main(String[] args) {
		new StuQueryDel();
	}
	
	/**
	 * 获取需要插入到JTable中的所有行的信息
	 * @param stuList  装有学生信息的List集合
	 * @return  返回封装成二维数组的学生信息Object[][]数组
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
	 * 添加所有的学生信息到JTable   
	 * @return 返回所有的学生元组  每个学生信息就是JTable一行的值
	 */
	private List<Student> getAllStuInfo(){
		List<Student> stuList = new ArrayList<Student>();
		try {
			// 如果装载JTable的JScrollPane不为空
			if (scrollPane != null) {
				// 从主窗口中删除表格
				mainFrame.remove(scrollPane);
			}
			
			// 如果结果集不为空，则关闭结果集
			if (resultSet != null) {
				resultSet.close();
			}
			
			//select * from student_info where roomnum=?
			String query = "select * from student_info";
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(query);
			// 查询数据表
			resultSet = preState.executeQuery();
			while(resultSet.next()){
				Student student = new Student();
				student.setAccount(resultSet.getString("id"));   //获得学号
				student.setName(resultSet.getString("name"));    //获得姓名
				student.setSex(resultSet.getString("sex"));      //性别
				student.setClassroom(resultSet.getString("classroom")); //班级
				student.setCollege(resultSet.getString("college"));     //院系
				student.setBed(resultSet.getString("bed"));             //床位
				student.setRoomnum(resultSet.getString("roomnum"));     //寝室编号
				stuList.add(student);  //添加这个学生信息到stuList列表中
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"查询数据库出错~");
			return null;
		} finally {
			DatabaseConnect.closeResultset(resultSet);
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		return stuList;
	}
	
	/**
	 * 扩展AbstractTableModel,用于将一个ResultSet包装成TableModel     
	 * TableModel接口指定了 JTable 用于询问表格式数据模型的方法。
	 *
	 */
	class ResultSetTableModel extends AbstractTableModel{
		private ResultSet rs;
		private ResultSetMetaData rsmd;   //可用于获取关于 ResultSet 对象中列的类型和属性信息的对象
		
		//构造器,初始化rs和rsmd两个属性
		public ResultSetTableModel(ResultSet aResultSet){
			rs = aResultSet;
			try {
				rsmd = rs.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//重写getColumnName()方法,这个方法获取指定列的名称    用于为该TableModel设置列名
		@Override
		public String getColumnName(int c) {
			try {
				return rsmd.getColumnName(c + 1);
			} catch (SQLException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		//重写getColumnCount()方法,返回该模型中的列数     用于设置该TableModel的列数
		public int getColumnCount() {
			try {
				return rsmd.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

		//重写getRowCount()方法,返回该模型中的行数    用于设置该TableModel的行数
		public int getRowCount() {
			try {
				//获取最后一行的编号   于是得到模型中的行数
				rs.last();        //将光标移动到此 ResultSet 对象的最后一行。
				return rs.getRow();   //获取当前行编号       
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

		//重写getValueAt()方法,返回 columnIndex 和 rowIndex 位置的单元格值。    用于设置该TableModel指定单元格的值
		public Object getValueAt(int r, int c) {
			try {
				rs.absolute(r + 1);    //将光标移动到此 ResultSet 对象的给定行编号
				return rs.getObject(c + 1);  //以 Java 编程语言中 Object 的形式获取此 ResultSet 对象的当前行中指定列的值。
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//重写isCellEditable()方法,单元格是否可编辑    让单元格不可编辑
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			//如果 rowIndex 和 columnIndex 位置的单元格是可编辑的，则返回 true。否则，在该单元格上调用 setValueAt 不会更改该单元格的值
			return false;    
		}
		
		
		
	}
	
	
	
}
