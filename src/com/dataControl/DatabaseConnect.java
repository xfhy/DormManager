package com.dataControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 2016年8月15日22:20:00 
 * 
 * 用于数据库连接和关闭Connection,PreparedStatement,Resultset
 * 
 * @author XFHY
 * 
 */
public class DatabaseConnect {
	static String drivers;
	static String url;
	static String username;
	static String password;

	//构造方法
	public DatabaseConnect() {
		
	}
	
	/**
	 *  连接数据库
	 * @return
	 */
	public static Connection connDatabase() {
		Connection conn = null;
		/*
		 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。
		 * 属性列表中每个键及其对应值都是一个字符串。 
		 * */
		Properties props = new Properties();
		// 加载数据库驱动
		try {
			//FileInputStream:从文件系统中的某个文件中获得输入字节    将配置文件加载到输入流中
			FileInputStream in = new FileInputStream("conn.ini");
			props.load(in); //从输入流中读取属性列表（键和元素对）。
			in.close();  //关闭输入流
			
			   /*-------从配置文件中加载数据库连接数据---------*/
			//getProperty()用指定的键在此属性列表中搜索属性
			drivers = props.getProperty("jdbc.drivers");
			url = props.getProperty("jdbc.url");
			username = props.getProperty("jdbc.username");
			password = props.getProperty("jdbc.password");
			Class.forName(drivers);   //加载驱动
			
			DriverManager.setLoginTimeout(4);  //设置驱动程序试图连接到某一数据库时将等待的最长时间，以秒为单位
			//试图建立到给定数据库 URL 的连接
			conn = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "驱动加载失败");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库连接失败! ! !\n 请确保已开启数据库服务");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "配置文件未找到..");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "读取配置文件出错..");
		}
		return conn;
	}

	// 专门用来关闭Connection对象的
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // 立即释放此 Connection 对象的数据库和 JDBC 资源，而不是等待它们被自动释放
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "数据库访问错误！！！", "☆★提示信息☆★",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// 关闭PreparedStatement对象的
	public static void closeStatement(PreparedStatement stat) {
		if (stat != null) {
			try {
				// 立即释放此 Statement 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。
				//一般来说，使用完后立即释放资源是一个好习惯，这样可以避免对数据库资源的占用。 
				stat.close(); 
			} catch (Exception e2) {
				System.out.println("PreparedStatement");
				JOptionPane.showMessageDialog(null, "数据库访问错误!!!", "☆★提示信息☆★",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// 关闭Resultset
	public static void closeResultset(ResultSet results) {
		if (results != null) {
			try {
				results.close(); // 立即释放此 ResultSet 对象的数据库和 JDBC 资源，而不是等待该对象自动关闭时发生此操作。 
			} catch (Exception e2) {
				System.out.println("ResultSet");
				JOptionPane.showMessageDialog(null, "数据库访问错误！！！", "☆★提示信息☆★",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
