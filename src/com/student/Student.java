package com.student;



/**
 * 2016年8月19日22:44:36
 * 此类是关于学生的
 * @author XFHY
 * 
 * 登录成功,则马上初始化该类,其他类要用到时直接调用静态方式可获取当前学生的属性信息
 * 
 */
public class Student {
	private static String account="";    //学生账号
	private static String staffType="学生";  //用户类型
	private static String name="";     //姓名
	private static String sex="";      //性别
	private static String classroom="";//班级
	private static String college="";  //院系
	private static String bed="";      //床位
	private static String roomnum="";  //寝室编号
	
	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Student.name = name;
	}

	public static String getSex() {
		return sex;
	}

	public static void setSex(String sex) {
		Student.sex = sex;
	}

	public static String getClassroom() {
		return classroom;
	}

	public static void setClassroom(String classroom) {
		Student.classroom = classroom;
	}

	public static String getCollege() {
		return college;
	}

	public static void setCollege(String college) {
		Student.college = college;
	}

	public static String getBed() {
		return bed;
	}

	public static void setBed(String bed) {
		Student.bed = bed;
	}

	public static String getRoomnum() {
		return roomnum;
	}

	public static void setRoomnum(String roomnum) {
		Student.roomnum = roomnum;
	}

	//获取学生账号
	public static String getAccount() {
		return account;
	}
	
	//设置学生账号
	public static void setAccount(String account) {
		Student.account = account;
	}
	
	//获取用户类型
	public static String getStaffType() {
		return staffType;
	}
	
	//销毁时需要调用(比如学生注销了)    初始化该类
	public static void onDestroy(){
		Student.account = "";
		Student.bed = "";
		Student.classroom = "";
		Student.college = "";
		Student.name = "";
		Student.roomnum = "";
		Student.sex = "";
	}
	
}
