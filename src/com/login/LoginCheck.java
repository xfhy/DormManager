package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.dataControl.DatabaseConnect;

/**
 * 2016年8月16日16:58:01
 * 
 * 用来检查用户登录是否成功   专门为Login类服务
 * 
 * @author XFHY
 * 
 * 连接数据库,并检查用户登录是否成功
 * 
 */
public class LoginCheck {
	
	/**
	 * 判断用户登录是否成功
	 * @param table   表名
	 * @param id      用户id
	 * @param passwrd 用户密码
	 * @return        返回值  1:登录成功,找到了合法的用户     0:不是合法用户      -1:连数据库都没有连接成功
	 */
	public static int isSucceed(String table,String id,String passwrd){
		String sql = ""; //待会儿要执行的SQL语句
		if(table.equals("学生")){
			table = "student_info";
			sql = "select * from student_info where id=? and passwrd=?";
		} else if(table.equals("宿舍管理员")) {
			table = "college_admin";
			sql = "select * from college_admin where id=? and passwrd=?";
		} else if(table.equals("系统管理员")) {
			table = "system_admin";
			sql = "select * from system_admin where id=? and passwrd=?";
		}
		
		//与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		//SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。 
		PreparedStatement preState = null; 
		//返回的结果   表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resultSet = null;
		try {
			conn = DatabaseConnect.connDatabase();   //连接数据库
			//如果这里连接数据都失败了,那么下面的代码不必执行,除了finally
			if(conn == null){
				return -1;   
			}
			
			preState = conn.prepareStatement(sql);   //生成PreparedStatement对象
			//setString()方法是给表的列赋值，而不能给直接赋表名~    而且位置是从1开始
			preState.setString(1, id);
			preState.setString(2, passwrd);
			//执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null 
			resultSet = preState.executeQuery();   
			
			/*
			 * 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；
			 * 第二次调用使第二行成为当前行，依此类推。 当调用 next 方法返回 false 时，光标位于最后一行的后面。
			 * */
			if(resultSet.next()){   //如果有值,则找到了合法的用户
				return 1;  //这里即使是return语句,但是还是会先执行下面的finally再return
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "数据库访问错误 ! ! !");
		} catch (Exception e1){
			e1.printStackTrace();
		} finally {
			//下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeResultset(resultSet);  //关闭ResultSet
			DatabaseConnect.closeStatement(preState);   //关闭PreparedStatement
			DatabaseConnect.closeConnection(conn);      //关闭Connection
		}
		return 0;
	}
	
}
