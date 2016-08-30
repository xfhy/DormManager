package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.dataControl.DatabaseConnect;

/**
 * 连接数据库,获取学生信息
 * 
 * @author XFHY
 * 
 * 
 * 
 */
public class StudentOperate {
	
	/**
	 * 通过连接数据库,查询,获取某个学生信息,确定学生用id
	 * @param id 学生学号
	 *    返回带有学生信息的Object[]数组
	 */
	public static Object[] getStudentInfo(String id){
		Object[] data = new Object[6];  //用于存放学生的数据
		
		//与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		//SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;
		//返回的结果   表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resSet = null;
		String sql = "select * from student_info where id=?";
		try {
			conn = DatabaseConnect.connDatabase();  //得到连接了数据库的Connection实例
			preState = conn.prepareStatement(sql);  //生成PreparedStatement对象
			//setString()方法是给表的列赋值，而不能给直接赋表名~    而且位置是从1开始
			preState.setString(1, id);
			
			//执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null 
			resSet = preState.executeQuery();
			
			/*
			 * 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；
			 * 第二次调用使第二行成为当前行，依此类推。 当调用 next 方法返回 false 时，光标位于最后一行的后面。
			 * */
			if(resSet.next()){  //如果找到了
				data[0] = resSet.getString("name");        //姓名
				data[1] = resSet.getString("sex");         //性别
				data[2] = resSet.getString("classroom");   //班级
				data[3] = resSet.getString("college");  //院系
				data[4] = resSet.getString("bed");      //床位
				data[5] = resSet.getString("roomnum");  //寝室编号
			} else {  
				//既然之前能够登录,则说明之前数据库是可以连接的,现在却查不到该用户,则说明可能用户把数据库关闭了,或者其他原因
				JOptionPane.showMessageDialog(null,"未查询到该学生的信息....");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"数据库访问出错啦! ! !");
		} finally {
			DatabaseConnect.closeResultset(resSet);   //关闭Resultset
			DatabaseConnect.closeStatement(preState); //关闭PreparedStatement对象的
			DatabaseConnect.closeConnection(conn);   //关闭Connection对象的
		}
		return data;  //将带结果的数组返回
	}
	
	
	
}
