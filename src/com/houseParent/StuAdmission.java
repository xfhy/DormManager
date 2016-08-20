package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 2016年8月20日16:34:04 新生入住
 * 
 * @author XFHY
 * 
 */
public class StuAdmission {
	
	public static final int ROOM_COUNT = 6;   //一间寝室的人数
	public static final int DORM_FLOOR = 6;   //寝室楼层
	public static final int FLOOR_ROOM_COUNT = 33; //一个楼层的寝室数目
	
	//主窗口
	JFrame mainFrame = new JFrame("新生入住登记");
	
	//定义标签
	JLabel idLabel = new JLabel("学号:",JLabel.RIGHT);
	JLabel nameLabel = new JLabel("姓名:",JLabel.RIGHT);
	JLabel sexLabel = new JLabel("性别:",JLabel.RIGHT);
	JLabel classLabel = new JLabel("班级:",JLabel.RIGHT);
	JLabel collegeLabel = new JLabel("院系:",JLabel.RIGHT);
	JLabel bedLabel = new JLabel("床位:",JLabel.RIGHT);
	JLabel roomLabel = new JLabel("寝室编号:",JLabel.RIGHT);
	
	//定义文本输入框
	JTextField idText = new JTextField(20);
	JTextField nameText = new JTextField(20);
	//JTextField sexText = new JTextField(20);
	JComboBox<String> sexChooser = new JComboBox<String>();   //性别用下拉列表比较好
	JTextField classText = new JTextField(20);
	
	//定义下拉框
	JComboBox<String> collegeChooser = new JComboBox<String>();
	JComboBox<String> bedChooser = new JComboBox<String>();
	JPanel roomPanel = new JPanel(new BorderLayout());  //专门用来装楼层和寝室下拉框的
	JComboBox<String> floorChooser = new JComboBox<String>();  //楼层
	JComboBox<String> roomChooser = new JComboBox<String>(); //寝室
	
	//定义按钮
	JButton addStuBtn = new JButton("添加学生");
	JButton resetBtn = new JButton("重置");
	JButton cancelBtn = new JButton("取消");
	
	//窗口上边的布局
	JPanel labelPanel = new JPanel();   //JLabel区域
	JPanel textPanel = new JPanel();    //JTextField区域
	JPanel topPanel = new JPanel();     //上边的布局
	
	//按钮的布局
	FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,100,20);
	JPanel buttonPanel = new JPanel(flowLayout);
	
	//构造方法
	public StuAdmission() {
		init();
	}

	//初始化
	private void init() {
		//添加按钮到按钮面板
		buttonPanel.add(addStuBtn);
		buttonPanel.add(resetBtn);
		buttonPanel.add(cancelBtn);
		
		//为按钮添加监听器
		addStuBtn.addActionListener(new AddStuListener());
		resetBtn.addActionListener(new ResetListener());
		cancelBtn.addActionListener(new CancelListener());
		
		//添加JLabel到JLabel面板
		labelPanel.setLayout(new GridLayout(7,1,20,20));
		labelPanel.add(idLabel);
		labelPanel.add(nameLabel);
		labelPanel.add(sexLabel);
		labelPanel.add(classLabel);
		labelPanel.add(collegeLabel);
		labelPanel.add(bedLabel);
		labelPanel.add(roomLabel);
		
		//添加JTextField到JTextField面板
		textPanel.setLayout(new GridLayout(7,1,20,20));
		textPanel.add(idText);
		textPanel.add(nameText);
		textPanel.add(sexChooser);
		textPanel.add(classText);
		textPanel.add(collegeChooser);
		textPanel.add(bedChooser);
		roomPanel.add(floorChooser,BorderLayout.LINE_START);  //楼层和寝室下拉框布局设置
		roomPanel.add(roomChooser,BorderLayout.LINE_END);
		textPanel.add(roomPanel);
		
		idText.addKeyListener(new NumberListener());
		classText.addKeyListener(new NumberListener());
		
		initChooser();  //初始化下拉框
		
		//上面的布局  添加JLabel区,JTextField区
		topPanel.setLayout(new GridLayout(1,2,20,20));
		topPanel.add(labelPanel);
		topPanel.add(textPanel);
		
		//整个窗口的布局   上面的布局是LINE_START,下面的是PAGE_END
		mainFrame.add(topPanel,BorderLayout.LINE_START);
		mainFrame.add(buttonPanel,BorderLayout.PAGE_END);
		
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // 窗口大小不可变
		showUI();
	}

	//初始化下拉框
	private void initChooser() {
		
		sexChooser.addItem("请选择性别");
		sexChooser.addItem("男");
		sexChooser.addItem("女");
		
		//初始化   学院 那个下拉框
		collegeChooser.addItem("请选择学院");
		collegeChooser.addItem("计算机科学学院");
		collegeChooser.addItem("物理学院");
		collegeChooser.addItem("化学学院");
		collegeChooser.addItem("生物学院");
		collegeChooser.addItem("数学学院");
		
		//初始化   床位 那个下拉框
		bedChooser.addItem("请选择床位");
		for (int i = 1; i <= ROOM_COUNT; i++) {
			bedChooser.addItem(i+"");
		}
		
		//初始化  楼层 那个下拉框
		floorChooser.addItemListener(new FloorListener());
		floorChooser.addItem("请选择楼层");
		for (int i = 1; i <= DORM_FLOOR; i++) {
			floorChooser.addItem(i+"");
		}
		
		//初始化  寝室 那个下拉框
		int temp = 1;
		roomChooser.addItem("请选择寝室");
		for (int d = 1; d <= DORM_FLOOR; d++) {
			for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
				temp = d*100+i;
				roomChooser.addItem(temp+"");
			}
		}
		
	}

	//显示UI界面
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// 主函数
	public static void main(String[] args) {
		new StuAdmission();
	}

	//按键监听器   功能:控制输入  比如只能输入数字
	class NumberListener implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent arg0) {
			
		}

		//按键的类型
		public void keyTyped(KeyEvent e) {
			//加入下面的代码后    只能输入0~9数字,不能输负号,不能输小数点,可以删除(Backspace)
			
			int keyChar = e.getKeyChar();    //返回与此事件关联的字符
			if( keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 ){
				
			} else {
				e.consume();   //关键,屏蔽掉非法输入
			}
		}
		
	}
	
	//下拉列表 监听器   这是楼层选择的那个下拉框的监听器
	class FloorListener implements ItemListener{

		//当用户选择了楼层之后,后面的 寝室下拉列表 相应的跟着更新一下,比如用户选择1楼,则只显示1楼的寝室编号
		public void itemStateChanged(ItemEvent event) {
			
			roomChooser.removeAllItems();
			
			Object itemObject = event.getItem();
			String floorString = itemObject.toString();
			if(floorString.equals("请选择楼层")){
				//初始化  寝室 那个下拉框
				int temp = 1;
				roomChooser.addItem("请选择寝室");
				for (int d = 1; d <= DORM_FLOOR; d++) {
					for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
						temp = d*100+i;
						roomChooser.addItem(temp+"");
					}
				}
			} else {
				int floor = Integer.parseInt(itemObject.toString());
				int temp = 1;
				for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
					temp = floor*100+i;
					roomChooser.addItem(temp+"");
				}
			}
			
		}
		
	}
	
	    /*-----------------未完成-----------------*/
	//添加学生按钮监听器
	class AddStuListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			//JOptionPane.showMessageDialog(null, "执行添加前初步检查");
			checkFirst();  //执行添加前初步检查   判断用户填的信息是否正确(未连接数据库)
		}
		
	}
	
	//重置按钮监听器
	class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			idText.setText("");
			nameText.setText("");
			sexChooser.removeAllItems();
			classText.setText("");
			
			collegeChooser.removeAllItems();
			bedChooser.removeAllItems();
			floorChooser.removeAllItems();
			roomChooser.removeAllItems();
			initChooser();  //初始化下拉框
		}
		
	}
	
	//取消按钮监听器
	class CancelListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //关闭当前窗口
		}
		
	}
	
	//执行添加前初步检查   判断用户填的信息是否正确(未连接数据库)
	private void checkFirst(){
		
		//判断用户是否有哪一项留空  或者哪一项没选
		if( idText.getText().equals("") || nameText.getText().equals("") || 
				sexChooser.getSelectedItem().toString().equals("请选择性别") || 
				classText.getText().equals("") || 
				collegeChooser.getSelectedItem().toString().equals("请选择学院") ||
				bedChooser.getSelectedItem().toString().equals("请选择床位") ||
				floorChooser.getSelectedItem().toString().equals("请选择楼层") ||
				roomChooser.getSelectedItem().toString().equals("请选择寝室")){
			JOptionPane.showMessageDialog(null, "亲,不能留空或者哪一项不选哦~~");
			return ;
		}
		
		//姓名的正则表达式   ^:行的开头  [\u4e00-\u9fa5]{2,8}:汉字2~8个字  |:或者  [a-zA-Z]{2,16}:英文2~16个字母  $:行的结尾
		String nameRegex = "^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
		boolean isName = nameText.getText().matches(nameRegex);   //判断用户输入的是否是正确的姓名
		if(!isName){
			JOptionPane.showMessageDialog(null, "亲,您输入的姓名有误哦~~");
		}
	}
	
}
