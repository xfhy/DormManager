package com.student;



/**
 * 2016��8��19��22:44:36
 * �����ǹ���ѧ����
 * @author XFHY
 * 
 * ��¼�ɹ�,�����ϳ�ʼ������,������Ҫ�õ�ʱֱ�ӵ��þ�̬��ʽ�ɻ�ȡ��ǰѧ����������Ϣ
 * 
 */
public class Student {
	private static String account="";    //ѧ���˺�
	private static String staffType="ѧ��";  //�û�����
	private static String name="";     //����
	private static String sex="";      //�Ա�
	private static String classroom="";//�༶
	private static String college="";  //Ժϵ
	private static String bed="";      //��λ
	private static String roomnum="";  //���ұ��
	
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

	//��ȡѧ���˺�
	public static String getAccount() {
		return account;
	}
	
	//����ѧ���˺�
	public static void setAccount(String account) {
		Student.account = account;
	}
	
	//��ȡ�û�����
	public static String getStaffType() {
		return staffType;
	}
	
	//����ʱ��Ҫ����(����ѧ��ע����)    ��ʼ������
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
