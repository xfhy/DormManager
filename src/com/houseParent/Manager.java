package com.houseParent;

/**
 * 2016年8月19日21:59:52
 * 此类是关于宿舍管理员的
 * @author XFHY
 * 
 * 登录成功,则马上初始化该类,其他类要用到时直接调用静态方式可获取当前宿舍管理员的属性信息
 * 
 */
public class Manager {
	private static String account="";   //账号
	private static String staffType = "宿舍管理员"; //用户类型
	
	//获取宿舍管理员账号
	public static String getAccount() {
		return account;
	}
	
	//设置宿舍管理员账号
	public static void setAccount(String account) {
		Manager.account = account;
	}
	
	//获取用户类型
	public static String getStaffType() {
		return staffType;
	}
	
	//销毁时需要调用(比如宿舍管理员注销了)    初始化该类
	public static void onDestroy(){
		Manager.account = "";
	}
	
}
