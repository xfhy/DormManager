package com.student;

/**
 * ����˽�е�ѧ����,û�о�̬����������
 * 
 * @author XFHY
 * 
 */
public class Student {
	private static String tempAccount = ""; // ��ʱѧ���˺�  ��̬����  ȫ�ֿ��õ�
	private String account = ""; // ѧ���˺�
	private static String staffType = "ѧ��"; // �û�����   ��̬����  ȫ�ֿ��õ�
	private String name = ""; // ����
	private String sex = ""; // �Ա�
	private String classroom = "";// �༶
	private String college = ""; // Ժϵ
	private String bed = ""; // ��λ
	private String roomnum = ""; // ���ұ��

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

	// ����ʱ��Ҫ����(����ѧ��ע����) ��ʼ������ľ�̬����
	public static void onDestroy() {
		Student.tempAccount = "";
		Student.staffType = "";
	}
}
