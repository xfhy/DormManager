package com.houseParent;

/**
 * 2016��8��19��21:59:52
 * �����ǹ����������Ա��
 * @author XFHY
 * 
 * ��¼�ɹ�,�����ϳ�ʼ������,������Ҫ�õ�ʱֱ�ӵ��þ�̬��ʽ�ɻ�ȡ��ǰ�������Ա��������Ϣ
 * 
 */
public class Manager {
	private static String account="";   //�˺�
	private static String staffType = "�������Ա"; //�û�����
	
	//��ȡ�������Ա�˺�
	public static String getAccount() {
		return account;
	}
	
	//�����������Ա�˺�
	public static void setAccount(String account) {
		Manager.account = account;
	}
	
	//��ȡ�û�����
	public static String getStaffType() {
		return staffType;
	}
	
	//����ʱ��Ҫ����(�����������Աע����)    ��ʼ������
	public static void onDestroy(){
		Manager.account = "";
	}
	
}
