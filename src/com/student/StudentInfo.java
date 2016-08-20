package com.student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 2016年8月16日22:35:45
 * 
 * 显示学生信息
 * 
 * @author XFHY
 * 
 * 使用该类时,需要传入学生学号,通过setAccount()方法
 * 
 */
public class StudentInfo{
	
	//String account = null;  //学生学号
	
	JFrame mainFrame = new JFrame("学生个人信息");
	
	JLabel idLabel = new JLabel("学号");
	JLabel nameLabel = new JLabel("姓名");
	JLabel sexLabel = new JLabel("性别");
	JLabel classLabel = new JLabel("班级");
	JLabel facultyLabel = new JLabel("院系");
	JLabel bedLabel = new JLabel("床位");
	JLabel roomLabel = new JLabel("寝室");
	
	JTextField idText = new JTextField(20);     //学号
	JTextField nameText = new JTextField(20);   //姓名
	JTextField sexText = new JTextField(20);    //性别
	JTextField classText = new JTextField(20);  //班级
	JTextField facultyText = new JTextField(20);//院系
	JTextField bedText = new JTextField(20);    //床位
	JTextField roomText = new JTextField(20);   //寝室编号
	
	JButton alterPasswrd = new JButton("修改密码");
	JButton exitButton = new JButton("退出");
	
	//面板
	JPanel idPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JPanel sexPanel = new JPanel();
	JPanel classPanel = new JPanel();
	JPanel facultyPanel = new JPanel();
	JPanel bedPanel = new JPanel();
	JPanel roomPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	//构造方法
	public StudentInfo(){
		//this.account = account; //先初始化账号
		
		//初始化数据   得到学生数据并设置到控件上  需要在初始化界面之前就要获取到数据
		getStudentData();
		init();
	}
	
	//初始化界面
	private void init() {
		
		idText.setEditable(false);
		nameText.setEditable(false);
		sexText.setEditable(false);
		classText.setEditable(false);
		facultyText.setEditable(false);
		bedText.setEditable(false);
		roomText.setEditable(false);
		
		//将JLabel和JTextField添加到面板
		idPanel.add(idLabel);
		idPanel.add(idText);
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		sexPanel.add(sexLabel);
		sexPanel.add(sexText);
		classPanel.add(classLabel);
		classPanel.add(classText);
		facultyPanel.add(facultyLabel);
		facultyPanel.add(facultyText);
		bedPanel.add(bedLabel);
		bedPanel.add(bedText);
		roomPanel.add(roomLabel);
		roomPanel.add(roomText);
		buttonPanel.add(alterPasswrd);
		buttonPanel.add(exitButton);
		
		//添加按钮监听器
		alterPasswrd.addActionListener(new AlterEventListener());
		exitButton.addActionListener(new ExitEventListener());
		
		
		
		//将面板添加到主容器中
		mainFrame.add(idPanel);
		mainFrame.add(namePanel);
		mainFrame.add(sexPanel);
		mainFrame.add(classPanel);
		mainFrame.add(facultyPanel);
		mainFrame.add(bedPanel);
		mainFrame.add(roomPanel);
		mainFrame.add(buttonPanel);
		
		//设置主容器布局
		mainFrame.setLayout(new GridLayout(8,2));
		
		mainFrame.setSize(500,600);      //设置大小
		mainFrame.setLocation(300,100);  //设置窗口初始位置
		mainFrame.setResizable(false);   //窗口大小不可变
		showUI();
	}
	
	//显示UI界面
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭方式
		mainFrame.setVisible(true);
	}

	
	/**
	 * 得到学生的数据,并设置到控件上
	 */
	private void getStudentData(){
		idText.setText(Student.getAccount());
		
		//如果用户是第一次进来,则需要从数据库加载数据       如果用户是第二次进来(中间耽搁了,比如去修改了密码回来),则去else那里
		if( Student.getName().equals("") ){
			//从GetStudentInfo的静态方法getStudentInfo()中获取带有数据的Object数组
			//getStudentInfo():通过连接数据库,获取某个学生信息,确定学生用id  返回带有学生信息的Object[]数组
			Object[] data = StudentOperate.getStudentInfo(Student.getAccount());
			if(data[0] != null){   //如果数据库连接成功并返回有效数据
				//分别设置控件的值
				nameText.setText(data[0].toString());
				sexText.setText(data[1].toString());
				classText.setText(data[2].toString());
				facultyText.setText(data[3].toString());
				bedText.setText(data[4].toString());
				roomText.setText(data[5].toString());
				
				//将数据放到Student类中暂存    万一有用呢
				Student.setName(nameText.getText());
				Student.setSex(sexText.getText());
				Student.setClassroom(classText.getText());
				Student.setCollege(facultyText.getText());
				Student.setBed(bedText.getText());
				Student.setRoomnum(roomText.getText());
			} else {
				nameText.setText("未获取到数据");
				sexText.setText("未获取到数据");
				classText.setText("未获取到数据");
				facultyText.setText("未获取到数据");
				bedText.setText("未获取到数据");
				roomText.setText("未获取到数据");
			}
		} else {   //如果用户是第二次进来(中间耽搁了,比如去修改了密码回来),则不用再去从数据库中获取数据,直接从Student类中取出来就行
			//分别设置控件的值
			nameText.setText(Student.getName());
			sexText.setText(Student.getSex());
			classText.setText(Student.getClassroom());
			facultyText.setText(Student.getCollege());
			bedText.setText(Student.getBed());
			roomText.setText(Student.getRoomnum());
		}
		
	}
	
	//内部类,实现ActionListener,监听器,用于修改密码按钮的监听器
    class AlterEventListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			 //调用修改密码的界面      参数:用户类型,账号
			new AlterPasswrd(Student.getStaffType(),Student.getAccount());  
		}
    }
    
    //内部类,实现ActionListener,监听器,用于退出按钮的监听器
    class ExitEventListener implements ActionListener{
    	public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //退出当前窗口
		}
    }
}

/*
 *     ---------学生信息表---------
create table student_info
(
   id varchar(10) not null,                                        --学号
   name varchar(10) not null,                                      --姓名
   passwrd varchar(15) default '0000' not null,                    --密码
   sex varchar(2) not null,                                        --性别
   classroom tinyint not null,                                     --班级
   college varchar(20) not null,                                   --院系
   bed tinyint,                                                    --床位
   roomnum varchar(5) not null,                                   --寝室编号
   primary key(id),     --学号是主键
   foreign key(roomnum) references bedroom(roomnum),          --寝室编号是外键
   check(sex='男' or sex='女'),     --性别只有男女
   check(classroom>0)               --班级编号应该大于0
);--有些学校是混寝,男女一起住,需加入性别这个属性
 * 
 * */
 