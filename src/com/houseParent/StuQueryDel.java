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
 * 2016年8月22日17:59:42
 * 
 * 查询删除学生
 * 
 * @author XFHY
 *
 */
public class StuQueryDel {
	
	JFrame mainFrame = new JFrame("查询删除");
	Logger log = Logger.getLogger(this.getClass().getName());
	//上面的布局
	JLabel findRoomLable = new JLabel("查询寝室信息",JLabel.LEFT);
	JLabel roomNumLable = new JLabel("寝室号");
	JTextField roomNumField = new JTextField(6);   //输入框   用来输入需要查询的寝室号
	JButton showAllStuBtn = new JButton("显示全部");
	JButton queryBtn = new JButton("查询");
	JButton deleteBtn = new JButton("删除");
	JButton exitBtn = new JButton("退出");
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
	
	JTable roomTable = new JTable();
	private JScrollPane scrollPane;
	private ResultSet resultSet;
	private Connection conn;
	private PreparedStatement preState;
	public static final int JTABLE_COLUMN_COUNTS = 7;  //JTable列数
	
	/**
	 * 定义一维数组作为列标题
	 */
	Object[] columnTitle = new Object[]{"学号","姓名","性别","班级","学院","床位","寝室号"};
	StudentTableModel studentTableModel = new StudentTableModel();
	List<Student> stuList = new ArrayList<Student>();
	
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
		topPanel.add(showAllStuBtn);
		topPanel.add(deleteBtn);
		topPanel.add(exitBtn);
		
		//设置查询按钮监听器
		queryBtn.addActionListener(new QueryListener());
		//设置删除按钮监听器
		deleteBtn.addActionListener(new DeleteListener());
		//设置显示全部按钮监听器
		showAllStuBtn.addActionListener(new ShowAllStuListener());  
		exitBtn.addActionListener(new ExitListener());
		
		stuList = getAllStuInfo("");   //初始化List集合,这些信息是全部学生的信息(不单指哪个寝室)   需要添加到JTable中的
		studentTableModel.setStudent(stuList);   //初始化TableModel
		roomTable.setModel(studentTableModel);   //设置JTable的TableModel
		roomTable.setRowHeight(30);           //设置JTable行高
		setColumnWidth(4,250,250,250);
		//禁止表格列与列之间交换
		JTableHeader tableHeader =roomTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		
		scrollPane = new JScrollPane(roomTable);  //添加表到滚动面板内
		
		//设置水平滚动条显示形式
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//设置垂直滚动条显示形式
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mainFrame.add(topPanel,BorderLayout.NORTH);
		mainFrame.add(scrollPane,BorderLayout.CENTER);
		mainFrame.setSize(900, 500);
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
	 * 设置某一列的列宽 
	 * 参数:某一列,首选宽度,最大宽度,最小宽度
	 */
	private void setColumnWidth(int xColumn,int columnPreferredWidth,int columnMaxWidth,int columnMinWidth){
		TableColumn tempColumn = roomTable.getColumnModel().getColumn(xColumn);   //获得JTable的某一列
		tempColumn.setPreferredWidth(columnPreferredWidth);   //设置首选宽度
		tempColumn.setMaxWidth(columnMaxWidth);               //设置最大宽度
		tempColumn.setMinWidth(columnMinWidth);               //设置最小宽度
	}
	
	/**
	 * 添加所有的学生信息到JTable   
	 * @return 返回所有的学生元组  每个学生信息就是JTable一行的值
	 */
	private List<Student> getAllStuInfo(String roomNum){
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
	 *  扩展AbstractTableModel，用于将一个List<Student>集合包装成TableModel
	 *  JTable的实现是基于MVC的, 所以JTabel的数据显示是一个独立的model的, JTable#setModel(TableModel dataModel)就是来设置model的,
	 *  所以你如果想动态显示数据, 你需要实现一个TableMode
	 */
	class StudentTableModel extends AbstractTableModel {

		//保存一个Student的列表
		List<Student> stuList = new ArrayList<Student>();

		//设置Student列表,同时通知JTable数据对象已更改,重绘界面
		public void setStudent(final List<Student> list){
			//invokeLater()方法:导致 doRun.run() 在 AWT 事件指派线程上异步执行。在所有挂起的 AWT 事件被处理后才发生。
			//此方法应该在应用程序线程需要更新该 GUI 时使用
			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					stuList = list;
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
			return stuList.size();
		}

		// 从List中拿出rowIndex行columnIndex列显示的值     用于设置该TableModel指定单元格的值
		public Object getValueAt(int rowIndex, int columnIndex) {
			Student student = stuList.get(rowIndex); // 获取当前行的Student
			switch (columnIndex) { // 根据列,返回值
			case 0:
				return student.getAccount();   //第一列 学号
			case 1:
				return student.getName();      //第二列 姓名
			case 2:
				return student.getSex();       //第三列 性别
			case 3:
				return student.getClassroom(); //第四列 班级
			case 4:
				return student.getCollege();   //第五列 学院
			case 5:
				return student.getBed();       //第六列 床位
			case 6:
				return student.getRoomnum();   //第七列 寝室号
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
			return "咦";
		}
		
		
		//重写isCellEditable()方法返回false，让每个单元格不可编辑
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
	}

	/**
	 * 查询按钮监听器
	 */
	class QueryListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String queryRoom = roomNumField.getText();   //获得用户输入的查询的寝室号
			String regex = "^[0-9a-zA-Z]+$";   //判断用户输入的寝室号是否正确,只能输入英文和数字
			
			//判断输入框是否为空                       判断用户输入的寝室号是否合法
			if(queryRoom.equals("") || !queryRoom.matches(regex)){
				JOptionPane.showMessageDialog(null, "请输入正确的寝室号");
			}
			
			List<Student> list = new ArrayList<Student>();
			list = getAllStuInfo(queryRoom);      //获取查询该寝室的学生的信息
			studentTableModel.setStudent(list);   //更新数据
			mainFrame.add(scrollPane,BorderLayout.CENTER);  //将JScrollPane重新添加到JFrame中
			
			roomNumField.requestFocus();    //在查询结束后,让输入框获得焦点
		}
		
	}
	
	/**
	 * 删除按钮 监听器
	 */
	class DeleteListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			
			//getSelectedRows():返回所有JTable选定行的索引
			int selectedRows[] = roomTable.getSelectedRows();  
			
			//判断用户是否选中得有学生信息
			if(selectedRows.length > 0){  
				log.info("选定的行数为:"+selectedRows.length);
				
			      /*-----------将用户选择的学生姓名暂时记录下来---------------*/
				StringBuffer names = new StringBuffer();
				StringBuffer ids = new StringBuffer();
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < selectedRows.length; i++) {  //所有选中的行  循环   取需要的数据
					idList.add(roomTable.getValueAt(selectedRows[i], 0).toString());
					if(i == (selectedRows.length-1)){  //最后一个数据之后不需要加入逗号
						//getValueAt():获取某一行某一列的值
						names = names.append(roomTable.getValueAt(selectedRows[i], 1).toString());
						ids = ids.append(roomTable.getValueAt(selectedRows[i], 0).toString());
					} else {
						names = names.append(roomTable.getValueAt(selectedRows[i], 1).toString()+",");
						ids = ids.append(roomTable.getValueAt(selectedRows[i], 0).toString()+",");
					}
				}
				log.info(names.toString());
				log.info(ids.toString());
				
				Icon icon = new ImageIcon("image//student//确认删除.png", "删除");
				// 确认对话框 返回:指示用户所选选项的 int 选择第一项就返回0,第二项是1
				int select = JOptionPane.showConfirmDialog(mainFrame, "您确定要删除 "+names.toString()+" 的信息吗?",
						"是否删除", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, icon);
				//如果用户选择的是确定,则删除刚刚选择的学生信息
				if(select == 0){
					if(deleteStuById(idList)){
						
						   /*---------------------删除界面上(JTable中)用户选中的行-----------------*/
						for(int i=selectedRows.length-1; i>= 0; i--){
							if(i <= stuList.size()){
								stuList.remove(selectedRows[i]);
							}
						}
						studentTableModel.setStudent(stuList);   //重新设置表格数据,并重绘界面
						
						JOptionPane.showMessageDialog(null, names.toString()+"已经被删除啦~");
					} else {
						JOptionPane.showMessageDialog(null, "删除失败....");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "没有选定的学生信息,请在下表中选中后,再删除");
			}
			 
			
		}
		
	}
	
	/**
	 * 删除学生信息,通过id来删除     
	 * @param idList  存有需要删除的学生的id信息
	 * @return
	 */
	private boolean deleteStuById(List<String> idList){
		  /*----------组装批量删除sql语句--------------*/
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
		
		     /*---------------数据库操作-------------*/
		try {
			conn = DatabaseConnect.connDatabase();
			preState = conn.prepareStatement(sql);
			int counts = preState.executeUpdate();  //可以是 INSERT、UPDATE 或 DELETE 语句   返回int,这里是返回删除的行数
			//判断是否用户选择的学生信息都删除了
			if(counts == idList.size()){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"操作数据库出错,删除失败....");
		} finally {
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
		
		return false;
	}
	
	/**
	 * 显示全部学生信息  按钮监听器
	 */
	class ShowAllStuListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			stuList = getAllStuInfo("");   //获取全部学生信息
			studentTableModel.setStudent(stuList);  //设置TableModel的数据,并重绘界面
			mainFrame.add(scrollPane,BorderLayout.CENTER);  //将JScrollPane重新添加到JFrame中   以达到重新显示JTable的效果
		}
		
	}
	
	/**
	 * 退出 按钮监听器
	 */
	class ExitListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //关闭当前窗口
		}
		
	}
	
}
