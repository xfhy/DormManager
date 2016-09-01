package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.CommonOperate;
import com.dataControl.DatabaseConnect;
import com.houseParent.StuAdmission.FloorListener;
import com.houseParent.StuAdmission.NumberListener;
import com.houseParent.StuAdmission.RoomListener;
import com.student.StudentOperate;

/**
 * 2016年8月30日9:59:58 学生信息修改
 * 
 * @author XFHY
 * 
 */
public class StuRevise {

	JFrame mainFrame = new JFrame("学生信息修改");

	//打印需要的信息
	Logger log = Logger.getLogger(this.getClass().getName());
	
	// 定义标签
	JLabel idLabel = new JLabel("学号:", JLabel.LEFT);
	JLabel nameLabel = new JLabel("姓名:", JLabel.LEFT);
	JLabel sexLabel = new JLabel("性别:", JLabel.LEFT);
	JLabel classLabel = new JLabel("班级:", JLabel.LEFT);
	JLabel collegeLabel = new JLabel("院系:", JLabel.LEFT);
	JLabel bedLabel = new JLabel("床位:", JLabel.LEFT);
	JLabel roomLabel = new JLabel("寝室编号:", JLabel.LEFT);

	// 输入框
	JTextField idText = new JTextField(20); // 学号
	JTextField nameText = new JTextField(20); // 姓名

	JComboBox<String> sexChooser = new JComboBox<String>(); // 性别用下拉列表比较好

	JTextField classText = new JTextField(20); // 班级
	// 定义下拉框
	JComboBox<String> collegeChooser = new JComboBox<String>(); // 院系选择
	JComboBox<String> bedChooser = new JComboBox<String>(); // 床位选择
	JLabel roomBedInfo = new JLabel("        ", JLabel.LEFT); // 显示本间寝室床位信息

	JPanel roomPanel = new JPanel(new BorderLayout()); // 专门用来装楼层和寝室下拉框的
	JComboBox<String> roomChooser = new JComboBox<String>(); // 寝室

	//按钮
	JButton queryBtn = new JButton("查询");
	JButton editBtn = new JButton("编辑");
	JButton resetBtn = new JButton("重置");
	JButton saveyBtn = new JButton("保存");
	JButton exitBtn = new JButton("退出");

	Object[] studentInfo;   //暂时存放学生的信息
	boolean isHideUI = false; //控制UI的显示
	
	// 构造方法
	public StuRevise() {
		
		init();
	}

	// 初始化
	private void init() {	
		
		//JLabel布局
		idLabel.setBounds(20, 10, 60, 30);  //x,y,宽,高
		nameLabel.setBounds(20, 50, 60, 30);
		sexLabel.setBounds(20, 90, 60, 30);
		classLabel.setBounds(20, 130, 60, 30);
		collegeLabel.setBounds(20, 170, 60, 30);
		roomLabel.setBounds(20, 210, 120, 30);
		bedLabel.setBounds(20, 250, 60, 30);
		
		//添加JLabel
		mainFrame.add(idLabel);
		mainFrame.add(nameLabel);
		mainFrame.add(sexLabel);
		mainFrame.add(classLabel);
		mainFrame.add(collegeLabel);
		mainFrame.add(bedLabel);
		mainFrame.add(roomLabel);
		
		idText.setBounds(150, 10, 200, 30);
		nameText.setBounds(150, 50, 200, 30);
		sexChooser.setBounds(150, 90, 200, 30);
		classText.setBounds(150, 130, 200, 30);
		collegeChooser.setBounds(150, 170, 200, 30);  //院系选择
		roomChooser.setBounds(150, 210, 200, 30);   //寝室编号
		bedChooser.setBounds(150, 250, 200, 30);
		roomBedInfo.setBounds(400, 250, 400, 30);
		
		//添加输入区域
		mainFrame.add(idText);
		mainFrame.add(nameText);
		mainFrame.add(sexChooser);
		mainFrame.add(classText);
		mainFrame.add(collegeChooser);
		mainFrame.add(bedChooser);
		mainFrame.add(roomChooser);
		mainFrame.add(roomBedInfo);
		
		//设置按钮布局
		queryBtn.setBounds(400,10,80,30);
		editBtn.setBounds(80,360,80,30);
		resetBtn.setBounds(200,360,80,30);
		saveyBtn.setBounds(320,360,80,30);
		exitBtn.setBounds(440,360,80,30);
		
		//添加按钮
		mainFrame.add(queryBtn);
		mainFrame.add(editBtn);
		mainFrame.add(resetBtn);
		mainFrame.add(saveyBtn);
		mainFrame.add(exitBtn);
		
		idText.addKeyListener(new NumberListener());  //添加监听器(只能输入数字)
		classText.addKeyListener(new NumberListener()); //添加监听器(只能输入数字)
		
		queryBtn.addActionListener(new QueryListener()); //添加查询按钮监听器
		editBtn.addActionListener(new EditListener()); //添加编辑按钮监听器
		resetBtn.addActionListener(new ResetListener()); //添加重置按钮监听器
		saveyBtn.addActionListener(new SaveListener()); //添加保存按钮监听器
		exitBtn.addActionListener(new ExitListener()); //添加退出按钮监听器
		
		controlUI();  //刚刚进来的时候,需要隐藏一些UI
		
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

	// 主函数
	/*public static void main(String[] args) {
		//设置全局字体                                               参数:字体,粗体,大小
		CommonOperate.InitGlobalFont(new Font("宋体", Font.BOLD, 20));
		new StuRevise();
	}*/
	
	/**
	 * 初始化时需要隐藏一些UI
	 */
	private void controlUI(){   
		if (!isHideUI) {
			nameText.setVisible(false);
			sexChooser.setVisible(false);
			classText.setVisible(false);
			collegeChooser.setVisible(false);
			bedChooser.setVisible(false);
			roomChooser.setVisible(false);
			editBtn.setVisible(false);
			resetBtn.setVisible(false);
			saveyBtn.setVisible(false);
		} else {
			nameText.setVisible(true);
			sexChooser.setVisible(true);
			classText.setVisible(true);
			collegeChooser.setVisible(true);
			bedChooser.setVisible(true);
			roomChooser.setVisible(true);
			editBtn.setVisible(true);
			resetBtn.setVisible(true);
			saveyBtn.setVisible(true);
		}
		
	}
	
	/**
	 * 下拉列表 监听器 这是寝室选择的那个下拉框的监听器
	 */
	class RoomListener implements ItemListener {

		public void itemStateChanged(ItemEvent arg0) {
			List<Integer> list = new ArrayList<Integer>(); // 用来存储已经有人睡了的床位

			String selectSex = sexChooser.getItemAt(sexChooser
					.getSelectedIndex()); // 用户选择的性别
			String selectRoom = roomChooser.getItemAt(roomChooser
					.getSelectedIndex()); // 用户选择的寝室

			// 判断用户是否选择了正确的寝室
			if (selectRoom != null && selectRoom.equals("请选择寝室")) { // 重置性别下拉列表
				roomBedInfo.setText("");
				sexChooser.removeAllItems(); // 移除所有选项
				sexChooser.addItem("请选择性别");
				sexChooser.addItem("男");
				sexChooser.addItem("女");
			} else if (selectRoom != null) {
				list = bedInfo(selectRoom); // 获取该寝室的所有已经被占用的床位
				StringBuffer str = new StringBuffer();
				for (Integer integer : list) {
					str.append(integer + " ");
					bedChooser.removeItem(integer + ""); // 将已经有人睡了的床位从
															// 床位下拉列表中移除
				}
				bedChooser.addItem(studentInfo[4].toString());
				
				// 这间寝室有人睡,显示床位信息
				if (list.size() != 0) {
					roomBedInfo.setText("这间寝室的" + str.toString() + "床位已经有人睡啦");
				} else { // 这间寝室无人睡,不显示
					roomBedInfo.setText("这间寝室目前无人住,可任意安排床位");
				}

				String roomSex = roomSexInfo(selectRoom); // 这间寝室的性别

				// 判断该寝室住宿的人是男生还是女生
				if (roomSex.equals("女")) {
					if (selectSex != null) {
						if (selectSex.equals("男") || selectSex.equals("请选择性别")) {
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2 + "  该寝室是女生寝室"); // 提示用户这是女生寝室
							sexChooser.removeItem("男"); // 并把性别下拉列表中 男 移除
							sexChooser.setSelectedItem("女"); // 并设置性别下拉列表为 女 选项
						}
					}

				} else if (roomSex.equals("男")) {
					if (selectSex != null) {
						if (selectSex.equals("女") || selectSex.equals("请选择性别")) {
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2 + "  该寝室是男生寝室");
							sexChooser.removeItem("女");
							sexChooser.setSelectedItem("男");
						}
					}
				} else if (roomSex.equals("")) {
					sexChooser.removeAllItems();
					sexChooser.addItem("请选择性别");
					sexChooser.addItem("男");
					sexChooser.addItem("女");
					sexChooser.setSelectedItem("请选择性别");
				}
			}

		}

	}
	
	/**
	 * 按键监听器 功能:控制输入 比如只能输入数字
	 * 
	 */
	class NumberListener implements KeyListener {

			public void keyPressed(KeyEvent e) {

			}

			public void keyReleased(KeyEvent arg0) {

			}

			// 按键的类型
			public void keyTyped(KeyEvent e) {
				// 加入下面的代码后 只能输入0~9数字,不能输负号,不能输小数点,可以删除(Backspace)

				int keyChar = e.getKeyChar(); // 返回与此事件关联的字符
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

				} else {
					e.consume(); // 关键,屏蔽掉非法输入
				}
			}

		}
	
	/**
	 * 查询按钮监听器(根据学号查询学生信息)
	 */
	class QueryListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			final String id = idText.getText();   //获取用户输入的学号
			
			if(!id.equals("")){  //如果用户输入的学号为非空
				
				//开启线程,在线程中更新UI
				new Thread(new Runnable(){

					public void run() {
						//查询该学号对应的学生的信息
						studentInfo = StudentOperate.getStudentInfo(id);
						if(studentInfo[0] != null){
							isHideUI = true;  //设置UI界面可见
							controlUI();
							nameText.setText(studentInfo[0].toString());  //姓名
							nameText.setEditable(false);
							sexChooser.removeAllItems();  //移除所有选项
							sexChooser.addItem(studentInfo[1].toString()); //性别
							classText.setText(studentInfo[2].toString());  //班级
							classText.setEditable(false);
							collegeChooser.removeAllItems();
							collegeChooser.addItem(studentInfo[3].toString()); //院系
							bedChooser.removeAllItems();
							bedChooser.addItem(studentInfo[4].toString());  //床位
							roomChooser.removeAllItems();
							roomChooser.addItem(studentInfo[5].toString());
							//log.info(studentInfo[0].toString());
						} else if(studentInfo[0] == null && isHideUI == true){
							nameText.setText("");
							sexChooser.removeAllItems();  //移除所有选项
							classText.setText("");
							collegeChooser.removeAllItems();
							bedChooser.removeAllItems();
							roomChooser.removeAllItems();
						}
						
					}
					
				}).start();
				
				
			} else {
				JOptionPane.showMessageDialog(null, "请输入学号! ! !");
			}
		}
		
	}
	
	/**
	 * 编辑按钮监听器
	 * 
	 */
	class EditListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			nameText.setEditable(true);
			classText.setEditable(true);
			sexChooser.removeAllItems();
			collegeChooser.removeAllItems();
			roomChooser.removeAllItems();
			bedChooser.removeAllItems();
			
			//子线程 -> 更新下拉列表
			new Thread(new Runnable(){

				public void run() {
					initChooser();  //初始化下拉列表
				}
				
			}).start();
			roomChooser.addItemListener(new RoomListener()); //寝室选择的那个下拉框的监听器
		}
		
	}
	
	/**
	 * 重置按钮监听器
	 */
	class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			idText.setText("");
			nameText.setText("");
			sexChooser.removeAllItems();
			classText.setText("");
			collegeChooser.removeAllItems();
			roomChooser.removeAllItems();
			bedChooser.removeAllItems();
			
			//子线程 -> 更新下拉列表
			new Thread(new Runnable(){

				public void run() {
					initChooser();  //初始化下拉列表
				}
				
			}).start();
		}
		
	}
	
	/**
	 * 保存按钮监听器
	 */
	class SaveListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if(checkFirst()){   // 执行添加前初步检查 判断用户填的信息是否正确(未连接数据库)
				if(!isOldInfo()){  //如果之前的信息和修改后的信息一致,则无需保存
					alterStu(); // 修改学生信息到数据库
				} else {
					JOptionPane.showMessageDialog(null,"您未做任何修改,无需保存~ ~");
				}
			}
		}
		
	}
	
	/**
	 * 退出监听器
	 */
	class ExitListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //干掉当前窗口
		}
		
	}
	
	/**
	 *  初步检查用户输入是否正确 未连接数据库
	 *  执行修改前初步检查 判断用户填的信息是否正确(未连接数据库)
	 * @return
	 */
	private boolean checkFirst() {

		// 判断用户是否有哪一项留空 或者哪一项没选
		if (idText.getText().equals("") || nameText.getText().equals("")
				|| sexChooser.getSelectedItem().toString().equals("请选择性别")
				|| classText.getText().equals("")
				|| collegeChooser.getSelectedItem().toString().equals("请选择学院")
				|| bedChooser.getSelectedItem().toString().equals("请选择床位")
				|| roomChooser.getSelectedItem().toString().equals("请选择寝室")) {
			JOptionPane.showMessageDialog(null, "亲,不能留空或者哪一项不选哦~~");
			return false;
		}

		// 姓名的正则表达式 ^:行的开头 [\u4e00-\u9fa5]{2,8}:汉字2~8个字 |:或者
		// [a-zA-Z]{2,16}:英文2~16个字母 $:行的结尾
		String nameRegex = "^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
		boolean isName = nameText.getText().matches(nameRegex); // 判断用户输入的是否是正确的姓名
		if (!isName) {
			JOptionPane.showMessageDialog(null, "亲,您输入的姓名有误哦~~");
			return false;
		}
		return true;
	}
	
	/**
	 *  初始化下拉框
	 */
	private void initChooser() {

		sexChooser.addItem("请选择性别");
		sexChooser.addItem("男");
		sexChooser.addItem("女");

		// 初始化 学院 那个下拉框
		collegeChooser.addItem("请选择学院");
		collegeChooser.addItem("计算机科学学院");
		collegeChooser.addItem("物理学院");
		collegeChooser.addItem("化学学院");
		collegeChooser.addItem("生物学院");
		collegeChooser.addItem("数学学院");

		// 初始化 床位 那个下拉框
		bedChooser.addItem("请选择床位");
		for (int i = 1; i <= StuAdmission.ROOM_COUNT; i++) {
			bedChooser.addItem(i + "");
		}

		// 初始化 寝室 那个下拉框
		int temp = 1;
		roomChooser.addItem("请选择寝室");
		for (int d = 1; d <= StuAdmission.DORM_FLOOR; d++) {
			for (int i = 1; i <= StuAdmission.FLOOR_ROOM_COUNT; i++) {
				temp = d * 100 + i;
				if (!isRoomFull(temp + "")) { // 判断这间寝室是否已满
					roomChooser.addItem(temp + "");
				}
			}
		}

	}
	
	/**
	 *  修改学生信息到数据库
	 */
	private void alterStu() {

		Connection conn = DatabaseConnect.connDatabase();
		PreparedStatement preState = null;
		int changeRows = 0; // 返回的 SQL 数据操作语言 (DML) 语句的行数
		// 姓名,密码(默认0000),性别,班级,院系,床位,寝室编号(外键)  学号
		String sql = "update student_info set name=?,sex=?,classroom=?,college=?,bed=?,roomnum=? where id=? ";
		// 需要执行的sql语句
		try {
			preState = conn.prepareStatement(sql);
			
			preState.setString(1, nameText.getText());
			preState.setString(2,
					sexChooser.getItemAt(sexChooser.getSelectedIndex()));
			preState.setString(3, classText.getText());
			preState.setString(4,
					collegeChooser.getItemAt(collegeChooser.getSelectedIndex()));
			preState.setString(5,
					bedChooser.getItemAt(bedChooser.getSelectedIndex()));
			preState.setString(6,
					roomChooser.getItemAt(roomChooser.getSelectedIndex()));
			preState.setString(7, idText.getText());

			changeRows = preState.executeUpdate();
			if (changeRows > 0) { // 如果插入的行数>0
				JOptionPane.showMessageDialog(null, "修改成功! ! !");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "修改失败! ! !");
		} finally {
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
	}

	/**
	 * 判断该寝室是否已经满了
	 * @param roomNumber 寝室编号
	 * @return 判断这间寝室是否已经住满
	 */
	private boolean isRoomFull(String roomNumber) {
		// 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		// SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;
		// 返回的结果 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resultSet = null;
		int num = 0; // 寝室人数

		try {
			// select num from bedroom where roomnum='101';
			String sql = "select num from bedroom where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // 连接数据库
			preState = conn.prepareStatement(sql); // 生成PreparedStatement对象

			// setString()方法是给表的列赋值，而不能给直接赋表名~ 而且位置是从1开始
			preState.setString(1, roomNumber);
			// 执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null
			resultSet = preState.executeQuery();

			if (resultSet.next()) {
				num = resultSet.getInt("num");
				if (num == StuAdmission.ROOM_COUNT) {
					return true; // 寝室人数已满
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeResultset(resultSet); // 关闭ResultSet
			DatabaseConnect.closeStatement(preState); // 关闭PreparedStatement
			DatabaseConnect.closeConnection(conn); // 关闭Connection
		}
		return false;
	}

	/**
	 * 返回该寝室的所有已经占了的床位
	 * @param roomNumber 寝室编号
	 * @return 返回这间寝室已经有人睡的床位
	 */
	private List<Integer> bedInfo(String roomNumber) {
		List<Integer> list = new ArrayList<Integer>(); // 用来存储已经有人睡了的床位

		// 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		// SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;
		// 返回的结果 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resultSet = null;

		try {
			// select bed from student_info where roomnum='101'; --查询这间寝室的床位信息
			String sql = "select bed from student_info where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // 连接数据库
			preState = conn.prepareStatement(sql); // 生成PreparedStatement对象

			// setString()方法是给表的列赋值，而不能给直接赋表名~ 而且位置是从1开始
			preState.setString(1, roomNumber);
			// 执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null
			resultSet = preState.executeQuery();

			/*
			 * 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；
			 * 第二次调用使第二行成为当前行，依此类推。 当调用 next 方法返回 false 时，光标位于最后一行的后面。
			 */
			while (resultSet.next()) {
				list.add(resultSet.getInt("bed")); // 床位
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeResultset(resultSet); // 关闭ResultSet
			DatabaseConnect.closeStatement(preState); // 关闭PreparedStatement
			DatabaseConnect.closeConnection(conn); // 关闭Connection
		}

		return list;
	}

	/**
	 * 返回这间寝室的性别  返回:男,女,""
	 * @param roomNumber 寝室编号
	 * @return 返回这间寝室的性别  返回:男,女,""
	 */
	private String roomSexInfo(String roomNumber) {
		String sex = null;
		
		// 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		// SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;
		// 返回的结果 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resultSet = null;

		try {
			// select sex from bedroom where roomnum='101';
			String sql = "select sex from bedroom where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // 连接数据库
			preState = conn.prepareStatement(sql); // 生成PreparedStatement对象
			
			// setString()方法是给表的列赋值，而不能给直接赋表名~ 而且位置是从1开始
			preState.setString(1, roomNumber);
			// 执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null
			resultSet = preState.executeQuery();
			
			if(resultSet.next()){
				sex = resultSet.getString("sex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeResultset(resultSet); // 关闭ResultSet
			DatabaseConnect.closeStatement(preState); // 关闭PreparedStatement
			DatabaseConnect.closeConnection(conn); // 关闭Connection
		}
		
		if(sex != null){
			return sex;
		} else {
			return "";
		}
	}

	/**
	 * 判断用户编辑之后的信息是否与原来的信息相同
	 */
	private boolean isOldInfo(){
		//String id = idText.getText();
		String name = nameText.getText();
		String sex = sexChooser.getSelectedItem().toString();
		String classroom = classText.getText();
		String college = collegeChooser.getSelectedItem().toString();
		String bed = bedChooser.getSelectedItem().toString();
		String roomnum = roomChooser.getSelectedItem().toString();
		if(studentInfo[0].toString().equals(name) && studentInfo[1].toString().equals(sex) && 
				studentInfo[2].toString().equals(classroom) && studentInfo[3].toString().equals(college) &&
				studentInfo[4].toString().equals(bed) && studentInfo[5].toString().equals(roomnum)){
			return true;   //如果原来的信息和现在的信息一样,则无需保存
		}
		return false;
	}
	
}
