package com.login;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.CommonOperate;
import com.houseParent.Manager;
import com.houseParent.WelManager;
import com.student.Student;
import com.student.StudentInfo;

/**
 * 2016年8月15日12:57:07
 * 
 * 系统管理员,宿舍管理员,学生登录
 * 
 * @author XFHY
 * 
 * 8.18:完成宿舍管理员修改密码,注销,退出,关于作者   新建Manager和PrivaStudent类,用户暂存登录成功用户的属性.注销时销毁(初始化)该类
 * 
 */
public class Login {
	
	//窗口
	JFrame loginJframe = new JFrame("登录界面");
	
	JPanel userPanel = new JPanel();      //用户面板
	JPanel passwdPanel = new JPanel();    //密码面板
	JPanel selectPanel = new JPanel();    //选择面板
	JPanel loginPanel = new JPanel();     //登录面板
	
	JLabel userLabel = null; //创建姓名标签
	JLabel passwdLabel = null; //创建密码标签
	JTextField userText = new JTextField(20); //文本框
	JPasswordField passwdText = new JPasswordField(20); //密码框
	
	
	
	JLabel selectLabel = null;//
	DefaultComboBoxModel<String> selectContent = new DefaultComboBoxModel<String>();   //下拉框的内容
	JComboBox<String> selectBox = null;   //下拉框
	
	//登录按钮
	Icon icon = new ImageIcon("image//loginButtonBack.jpg","登录");
	JButton loginButton = new JButton("",icon);

	//构造方法
	public Login() {
		//设置全局字体                                               参数:字体,粗体,大小
		CommonOperate.InitGlobalFont(new Font("宋体", Font.BOLD, 20));
		init();
	}

	//初始化
	public void init() {
		
		userLabel = new JLabel("账号:"); 
		passwdLabel = new JLabel("密码:");
		selectLabel = new JLabel("请选择身份:   ");
		
		//账户区
		userPanel.add(userLabel);
		userPanel.add(userText);
		
		//密码区
		passwdPanel.add(passwdLabel);
		passwdText.setEchoChar('●');  //设置回显字符
		passwdPanel.add(passwdText);
		
		
		//选择区
		selectContent.addElement("学生");
		selectContent.addElement("宿舍管理员");
		selectContent.addElement("系统管理员");
		selectBox = new JComboBox<String>(selectContent);  //初始化下拉列表
		selectPanel.add(selectLabel);
		selectPanel.add(selectBox);
		
		/*----------------测试数据------------------*/
		userText.setText("d");
		passwdText.setText("d");
		selectBox.setSelectedIndex(1);  //1是第二项   即宿舍管理员
		
		//登录区
		loginButton.setContentAreaFilled(false);  //让按钮透明
		loginButton.setBorderPainted(false);   //去掉按钮边框
		loginPanel.add(loginButton);
		
		   /*-----------注册登录按钮监听事件---------------*/
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				loginProcess();  //判断用户是否为正确的用户
			}
		});
		
		    /*-----------注册点击enter键的事件----------*/
		userText.addKeyListener(enterListener);
		passwdText.addKeyListener(enterListener);
		selectBox.addKeyListener(enterListener);
		loginButton.addKeyListener(enterListener);
		
		//添加面板到主容器
		loginJframe.add(userPanel);
		loginJframe.add(passwdPanel);
		loginJframe.add(selectPanel);
		loginJframe.add(loginPanel);
		
		//设置布局,从上到下
		loginJframe.setLayout(new BoxLayout(loginJframe.getContentPane(), BoxLayout.Y_AXIS));  
		
		// 宽度,高度
		Dimension d = new Dimension(320, 220); // 创建一个Dimension对象,用来设置窗口大小的
		Point p = new Point(100, 100); // 创建一个Point,用来设置窗口的初始位置
		loginJframe.setSize(d); // 设置窗体大小
		loginJframe.setLocation(p);
		loginJframe.setResizable(false); // 设置窗口大小不可变
		showUI();
	}
    
	//显示UI
	public void showUI() {
		loginJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
		loginJframe.setVisible(true); // 设置窗体可见
	}
	
	//主函数
	public static void main(String[] args) {
		new Login();
	}
	
	/**
	 * enter键的监听器
	 */
	KeyListener enterListener = new KeyListener() {

		public void keyTyped(KeyEvent arg0) {
			
		}
		
		public void keyReleased(KeyEvent arg0) {
			
		}
		
		//用户点击键盘时调用
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER){
				loginProcess();  //判断用户是否为正确的用户
			}
		}
	};
	
	
	/**
	 * 判读用户是否为正确的用户
	 * 用户点击了登录按钮或者按下enter键   则执行下面的
	 */
	public void loginProcess() {
		//获取用户下拉框的选择,选择的登录类型
		String selectType = selectBox.getItemAt(selectBox.getSelectedIndex()).toString();
		String username = userText.getText();
		//getPassword():返回此 TextComponent 中所包含的文本
		String password = new String(passwdText.getPassword());  
		
		 //判断用户输入是否为空
		if(username.equals("") || password.equals("")){  
			//实例化Icon对象,Icon是接口,这里是用ImageIcon实例化的,参数:文件名称,图像的简明文本描述
			Icon icon = new ImageIcon("image//warning.jpg","警告");
			try {
				JOptionPane.showMessageDialog(null, "用户名或者密码为空！！！", "警 告 !", 
						JOptionPane.WARNING_MESSAGE, icon);
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		} else {   //如果非空
			
			//isSucceed():判断用户登录是否成功 
			//返回值  1:登录成功,找到了合法的用户     0:不是合法用户      -1:连数据库都没有连接成功
			int loginCode = LoginCheck.isSucceed(selectType, username, password);
			if( loginCode == 1 ){  //登录成功
				//JOptionPane.showMessageDialog(null, "登录成功!!!");
				loginSucceed();   //打开相应的界面
			} else if ( loginCode == 0 ){
				//实例化Icon对象,Icon是接口,这里是用ImageIcon实例化的,参数:文件名称,图像的简明文本描述
				Icon icon = new ImageIcon("image//fail.png","登录失败");
				try {
					JOptionPane.showMessageDialog(null, "登录失败！！！\n请检查用户名和密码是否输入正确",
							"登录失败 !", JOptionPane.WARNING_MESSAGE, icon);
				} catch (HeadlessException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	/**
	 * 登录成功,需要判断一下是何种人员类型,然后打开相应的界面
	 */
	private void loginSucceed(){
		//获取当前下拉框的选择类型
		String type = selectBox.getItemAt(selectBox.getSelectedIndex()).toString();
		
		//判断是何种人员类型
		if(type.equals("学生")){
			loginJframe.dispose();   //关闭此窗口
			//Student.setAccount(userText.getText());
			Student.setTempAccount(userText.getText());
			new StudentInfo();   //调用显示学生信息的界面
		} else if(type.equals("宿舍管理员")){
			loginJframe.dispose();   //关闭此窗口
			Manager.setAccount(userText.getText());   //设置宿舍管理员的账户
			new WelManager();
		} else if(type.equals("系统管理员")){
			JOptionPane.showMessageDialog(null,"系统管理员");
		}
	}
	
}
