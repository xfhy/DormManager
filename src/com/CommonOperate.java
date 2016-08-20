package com;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.dataControl.DatabaseConnect;

/**
 * 2016年8月17日19:30:48
 * 
 * 公用的操作: 修改密码
 * 
 * @author XFHY
 * 
 */
public class CommonOperate {

	/**
	 * 根据传入的用户类型,账户,修改密码
	 * 
	 * @param staffType
	 *            用户类型
	 * @param account
	 *            用户账户
	 * @param oldPasswrd
	 *            用户原来的密码
	 * @return 判断一下用户输入的旧密码是否正确,正确则返回true,否则返回false
	 */
	public static boolean isOldPasswrd(String staffType, String account,
			String oldPasswrd) {
		String sql = ""; // 待会儿要执行的SQL语句
		if (staffType.equals("学生")) {
			staffType = "student_info";
			sql = "select * from student_info where id=?";
		} else if (staffType.equals("宿舍管理员")) {
			staffType = "college_admin";
			sql = "select * from college_admin where id=?";
		} else if (staffType.equals("系统管理员")) {
			staffType = "system_admin";
			sql = "select * from system_admin where id=?";
		}

		// 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		// SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;
		// 返回的结果 表示数据库结果集的数据表，通常通过执行查询数据库的语句生成
		ResultSet resultSet = null;
		try {
			conn = DatabaseConnect.connDatabase(); // 连接数据库
			preState = conn.prepareStatement(sql); // 生成PreparedStatement对象
			// setString()方法是给表的列赋值，而不能给直接赋表名~ 而且位置是从1开始
			preState.setString(1, account);
			// 执行查询语句,返回包含该查询生成的数据的 ResultSet 对象；不会返回 null
			resultSet = preState.executeQuery();

			/*
			 * 将光标从当前位置向前移一行。ResultSet 光标最初位于第一行之前；第一次调用 next 方法使第一行成为当前行；
			 * 第二次调用使第二行成为当前行，依此类推。 当调用 next 方法返回 false 时，光标位于最后一行的后面。
			 */
			if (resultSet.next()) { // 如果有值,则找到了合法的用户
				String oldPass = resultSet.getString("passwrd");
				if (oldPass.equals(oldPasswrd)) { // 如果数据库中查询到的密码(旧密码)与用户输入的旧密码匹配
					// JOptionPane.showMessageDialog(null,
					// "数据库中查询到的密码(旧密码)与用户输入的旧密码匹配");
					return true; // 这里即使是return语句,但是还是会先执行下面的finally再return
				} else {
					// JOptionPane.showMessageDialog(null,
					// "数据库中查询到的密码(旧密码)与用户输入的旧密码不匹配");
					return false;
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "数据库访问错误 ! ! !");
		} finally {
			// 下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeResultset(resultSet); // 关闭ResultSet
			DatabaseConnect.closeStatement(preState); // 关闭PreparedStatement
			DatabaseConnect.closeConnection(conn); // 关闭Connection
		}

		return false;
	}

	/**
	 * 修改密码
	 * 
	 * @param staffType
	 *            用户类型
	 * @param account
	 *            用户账户
	 * @param passwrd
	 *            用户新密码
	 * @return 返回是否修改成功
	 */
	public static boolean alterPasswrd(String staffType, String account,
			String passwrd) {
		String sql = "";
		if (staffType.equals("学生")) {
			sql = "update student_info set passwrd = ? where id = ?";
		} else if (staffType.equals("宿舍管理员")) {
			sql = "update college_admin set passwrd=? where id=?";
		} else if (staffType.equals("系统管理员")) {
			sql = "update system_admin set passwrd=? where id=?";
		}

		// 与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		Connection conn = null;
		// SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
		PreparedStatement preState = null;

		// 接收sql语句执行后返回的行数
		int result = 0;

		try {
			conn = DatabaseConnect.connDatabase(); // 连接数据库
			preState = conn.prepareStatement(sql); // 生成PreparedStatement对象

			// setString()方法是给表的列赋值，而不能给直接赋表名~ 而且位置是从1开始
			preState.setString(1, passwrd);
			preState.setString(2, account);

			// 执行查询语句,返回行计数 对于什么都不返回的sql语句则返回0
			result = preState.executeUpdate();

			if (result != 0) { // 返回行数不等于0,则修改成功
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库访问错误 ! ! !");
		} finally {
			// 下面是自己定义的类DatabaseConnect里面的静态方法,用来关闭下面这些东西的
			DatabaseConnect.closeStatement(preState); // 关闭PreparedStatement
			DatabaseConnect.closeConnection(conn); // 关闭Connection
		}

		return false;
	}

	/**
	 * 设置字体   在这个方法之后的新添进来的组件的字体都是font了
	 * @param font 字体
	 */
	public static void InitGlobalFont(Font font) {
		
		// 实现 UIResource 的 java.awt.Font 的子类。设置默认字体属性的 UI 类应该使用此类。
		FontUIResource fontRes = new FontUIResource(font);

		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); // keys():返回此哈希表中的键的枚举
		keys.hasMoreElements();) { // hasMoreElements():测试此枚举是否包含更多的元素。
			Object key = keys.nextElement(); // 下一个
			Object value = UIManager.get(key); // 从默认值返回一个对象

			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	

}