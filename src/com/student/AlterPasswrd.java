package com.student;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.CommonOperate;

/**
 * 2016年8月17日15:49:36
 * 
 * 用于修改密码的界面 目前来看,可提供全部的人员(学生,系统管理员,宿舍管理员)拿来修改密码
 * 
 * @author XFHY
 * 
 *         当其他类想使用修改密码这个类时,需要提供   用户类型(学生,系统管理员,宿舍管理员),账号
 *         
 *    判断密码框非空的办法:oldPassField.getPassword().length != 0
 */
public class AlterPasswrd {

	String staffType = null; // 用户类型
	String account = null; // 账号

	
	JFrame mainFrame = new JFrame("修改密码");

	// 标签
	JLabel oldPassLabel = new JLabel("旧 密 码");
	JLabel newPassLabel = new JLabel("新 密 码");
	JLabel ensurePassLabel = new JLabel("密码确认");

	// 密码框
	JPasswordField oldPassField = new JPasswordField(20);
	JPasswordField newPassField = new JPasswordField(20);
	JPasswordField ensurePassField = new JPasswordField(20);

	// 按钮
	JButton ensureButton = new JButton("确认");
	JButton cancelButton = new JButton("取消");

	// 面板
	JPanel oldPanel = new JPanel();
	JPanel newPanel = new JPanel();
	JPanel ensurePanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	// 构造方法
	public AlterPasswrd() {
		init();
	}
	
	/**
	 * 构造方法
	 * @param staffType  用户类型
	 * @param account    账号
	 */
	public AlterPasswrd(String staffType,String account) {
		this.staffType = staffType;
		this.account = account;

		init();
	}

	/**
	 * 设置用户类型
	 * @param staffType  用户类型
	 * 
	 */
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	/**
	 * 设置用户的账户
	 * @param account 用户的账户
	 * 
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	// 初始化
	private void init() {

		/*---------把控件添加到面板----------------*/
		oldPanel.add(oldPassLabel);  
		oldPassField.setEchoChar('●');  //设置回显字符
		oldPanel.add(oldPassField);
		
		newPanel.add(newPassLabel);
		newPassField.setEchoChar('●');  //设置回显字符
		newPanel.add(newPassField);
		
		ensurePanel.add(ensurePassLabel);
		ensurePassField.setEchoChar('●');  //设置回显字符
		ensurePanel.add(ensurePassField);
		
		buttonPanel.add(ensureButton);
		buttonPanel.add(cancelButton);

		// 为按钮添加监听事件
		ensureButton.addActionListener(new EnsureEventListener());
		cancelButton.addActionListener(new CancelEventListener());

		// 添加面板到主窗口
		mainFrame.add(oldPanel);
		mainFrame.add(newPanel);
		mainFrame.add(ensurePanel);
		mainFrame.add(buttonPanel);

		// 设置主窗口布局
		mainFrame.setLayout(new GridLayout(4, 2));

		mainFrame.setSize(350, 250); // 设置窗口大小
		mainFrame.setLocation(300, 100); // 设置初始位置
		mainFrame.setResizable(false); // 窗口大小不可变
		showUI();
	}

	// 显示界面
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
		mainFrame.setVisible(true); // 设置可见
	}

	// 主函数
	public static void main(String[] args) {
		new AlterPasswrd();
	}

	/**
	 * @author XFHY 用于确定修改密码
	 */
	class EnsureEventListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			//getPassword()方法返回的是char[]数组,直接toString()的话得到的是char[]数组的地址,需要像如下方式转成String类型才行
			String oldPasswrd = new String(oldPassField.getPassword()); 
			String newPasswrd = new String(newPassField.getPassword()); 
			String ensurePasswrd = new String(ensurePassField.getPassword()); 
			
			//如果密码框为空
			if(oldPasswrd.equals("") || newPasswrd.equals("") || ensurePasswrd.equals("")){
				//实例化Icon对象,Icon是接口,这里是用ImageIcon实例化的,参数:文件名称,图像的简明文本描述
				Icon icon = new ImageIcon("image//warning.jpg","警告");
				try {
					JOptionPane.showMessageDialog(null, "亲,请不要留空哦！！！", "警 告 !", 
							JOptionPane.WARNING_MESSAGE, icon);
				} catch (HeadlessException e) {
					e.printStackTrace();
				}
			} else if( !newPasswrd.equals(ensurePasswrd) ){  //如果新密码与确认密码不一样  
				JOptionPane.showMessageDialog(null,"新密码与确认密码不一致");
			} else if(oldPasswrd.equals(newPasswrd)){  //新密码与旧密码一样
				JOptionPane.showMessageDialog(null,"新密码与旧密码一样的,何需修改....");
			} else if(newPasswrd.length() < 4 || newPasswrd.length() > 15){   //新密码小于4位,或超过15位
				JOptionPane.showMessageDialog(null,"密码长度不符合要求,规定4~15位字符或数字组合");
			} else {  
				//修改密码
				alterPasswrd(oldPasswrd,newPasswrd);
			}
		}

	}

	/**
	 * 修改密码
	 * @param oldPasswrd  旧密码
	 * @param newPasswrd  新密码
	 */
	private void alterPasswrd(String oldPasswrd,String newPasswrd){
		//isOldPasswrd()方法用于判断用户输入的旧密码是否正确  如果不正确则返回false
		if(CommonOperate.isOldPasswrd(staffType,account,oldPasswrd)){
			//调用alterPasswrd()方法修改密码  修改成功则返回true,否则返回false
			if( CommonOperate.alterPasswrd(staffType, account, newPasswrd) ){
				mainFrame.dispose();   //关闭该窗口
				JOptionPane.showMessageDialog(null, "密码修改成功! ! !");
				
				    /*--------暂时保留,万一有用------*/
				if(staffType.equals("学生")){   //如果当前用户是学生,修改完成返回学生界面
					//new StudentInfo(account);  //返回学生信息界面
				} else if(staffType.equals("宿舍管理员")){  //如果当前用户是宿舍管理员,则不用管,因为那个窗口未关闭的
					//new WelManager();
				}
			} else {
				JOptionPane.showMessageDialog(null, "密码修改失败! ! !");
			}
		} else {  //如果不匹配
			JOptionPane.showMessageDialog(null,"您输入的旧密码有错! \n 请查证");
		}
	}
	
	/**
	 * @author XFHY 用于取消修改密码,返回到之前的界面
	 */
	class CancelEventListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();    //关闭当前的窗口
			
			   /*-----------------暂时不用----------------*/
			//new StudentInfo();  //返回学生信息界面
		}

	}

}
