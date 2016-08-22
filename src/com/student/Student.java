package com.student;

/**
 * 这是私有的学生类,没有静态方法和属性
 * 
 * @author XFHY
 * 
 */
public class Student {
	private static String tempAccount = ""; // 临时学生账号  静态属性  全局可用的
	private String account = ""; // 学生账号
	private static String staffType = "学生"; // 用户类型   静态属性  全局可用的
	private String name = ""; // 姓名
	private String sex = ""; // 性别
	private String classroom = "";// 班级
	private String college = ""; // 院系
	private String bed = ""; // 床位
	private String roomnum = ""; // 寝室编号

	public static String getTempAccount() {
		return tempAccount;
	}

	public static void setTempAccount(String tempAccount) {
		Student.tempAccount = tempAccount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}

	public static String getStaffType() {
		return staffType;
	}

	// 销毁时需要调用(比如学生注销了) 初始化该类的静态属性
	public static void onDestroy() {
		Student.tempAccount = "";
		Student.staffType = "";
	}
}
