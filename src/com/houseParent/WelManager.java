package com.houseParent;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.login.Login;
import com.student.AlterPasswrd;

/**
 * 2016年8月18日21:11:37
 * 
 * 宿舍管理员界面 3个主菜单 第1个主菜单:新生入住,查询删除,修改信息 第2个主菜单:查寝,卫生检查 第3个主菜单:修改密码,注销,退出,关于
 * 
 * 
 * @author XFHY
 * 
 * 
 * 
 */
public class WelManager {
	
	// 主窗口
	JFrame mainFrame = new JFrame("宿舍管理员界面");

	// 背景面板 用于设置背景
	BackgroundPanel backgroundPanel = null;

	JMenuBar mainMenu = new JMenuBar(); // 主菜单栏

	// 菜单 其下可以添加菜单项 在后面添加字母并设置快捷键即可实现字母下划线(alt+字母)
	JMenu stuManaMenu = new JMenu("学生管理(S)");
	JMenu roomRecoMenu = new JMenu("寝室记录(R)");
	JMenu otherMenu = new JMenu("其它(O)");

	// 学生信息管理 菜单栏 下的子菜单
	JMenuItem stuArriItem = new JMenuItem("新生入住");
	JMenuItem stuQuDeItem = new JMenuItem("查询删除");
	JMenuItem stuAlteItem = new JMenuItem("修改信息");

	// 寝室记录 菜单栏下的子菜单
	JMenuItem checkRoItem = new JMenuItem("查寝");
	JMenuItem sanInspItem = new JMenuItem("卫生检查");

	// 其他 菜单栏下的子菜单
	JMenuItem altePassItem = new JMenuItem("修改密码");
	JMenuItem logOffItem = new JMenuItem("注销");
	JMenuItem exitItem = new JMenuItem("退出");
	JMenuItem aboutItem = new JMenuItem("关于作者");

	// 构造方法
	public WelManager() {
		init();
	}

	// 初始化
	public void init() {

		addMenu(); // 添加菜单

		// 为面板添加背景
		backgroundPanel = new BackgroundPanel(
				(new ImageIcon("image//water.jpg")).getImage());
		mainFrame.add(backgroundPanel);

		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // 窗口大小不可变
		showUI();
	}

	// 显示界面
	public void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// 这个用来设置背景的面板继承自JPanel
	class BackgroundPanel extends JPanel {
		Image image;

		// 构造方法 接收一个Image对象
		public BackgroundPanel(Image image) {
			this.image = image;
			// 如果为 true，则该组件绘制其边界内的所有像素。否则该组件可能不绘制部分或所有像素，从而允许其底层像素透视出来。
			this.setOpaque(true);
		}

		// 覆盖父类的paintComponent()方法
		// Draw the back ground. 画背景
		public void paintComponent(Graphics g) {
			// Graphics 类是所有图形上下文的抽象基类，允许应用程序在组件（已经在各种设备上实现）以及闭屏图像上进行绘制。
			super.paintComponents(g);
			/**
			 * img - 要绘制的指定图像。如果 img 为 null，则此方法不执行任何操作。 x - x 坐标。 y - y 坐标。
			 * width - 矩形的宽度。 height - 矩形的高度。 observer - 转换了更多图像时要通知的对象。
			 * 
			 * 绘制指定图像中已缩放到适合指定矩形内部的图像
			 * 
			 */
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	/**
	 * 完成菜单栏的界面设计
	 */
	private void addMenu() {

		// 学生信息管理 菜单栏 下的子菜单
		Icon icon = new ImageIcon("image//menuIcon//添加.png"); // 实例化Icon对象
																// 用于设置菜单图标
		stuArriItem.setIcon(icon); // 设置菜单图标
		icon = new ImageIcon("image//menuIcon//查询.png");
		stuQuDeItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//修改.png");
		stuAlteItem.setIcon(icon);
		stuManaMenu.add(stuArriItem);
		stuManaMenu.add(stuQuDeItem);
		stuManaMenu.add(stuAlteItem);
		stuManaMenu.setMnemonic(KeyEvent.VK_S); // 设置菜单快捷键 (alt+s)

		// 寝室记录 菜单栏 下的子菜单
		icon = new ImageIcon("image//menuIcon//查寝室.png");
		checkRoItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//检查.png");
		sanInspItem.setIcon(icon);
		roomRecoMenu.add(checkRoItem);
		roomRecoMenu.add(sanInspItem);
		roomRecoMenu.setMnemonic(KeyEvent.VK_R); // 设置菜单快捷键 (alt+r)

		// 其他 菜单栏下的子菜单
		icon = new ImageIcon("image//menuIcon//修改密码.png");
		altePassItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//注销.png");
		logOffItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//退出.png");
		exitItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//关于.png");
		aboutItem.setIcon(icon);
		otherMenu.add(altePassItem);
		otherMenu.add(logOffItem);
		otherMenu.add(exitItem);
		otherMenu.add(aboutItem);
		otherMenu.setMnemonic(KeyEvent.VK_O); // 设置菜单快捷键 (alt+o)

		/*----------添加菜单监听器-------------*/
		stuArriItem.addActionListener(new StuArriListener()); // 新生入住
		stuQuDeItem.addActionListener(new StuQuDeListener()); // 查询删除
		stuAlteItem.addActionListener(new StuAlteListener()); // 修改信息
		checkRoItem.addActionListener(new CheckRoListener()); // 查寝
		sanInspItem.addActionListener(new SanInspListener()); // 卫生检查
		altePassItem.addActionListener(new AltePassListener()); // 修改密码
		logOffItem.addActionListener(new LogOffListener()); // 注销
		exitItem.addActionListener(new ExitListener()); // 退出
		aboutItem.addActionListener(new AboutListener()); // 关于作者

		/*----------------设置菜单快捷键( Ctrl+? )-------------------*/
		stuArriItem.setAccelerator(KeyStroke.getKeyStroke('N',
				InputEvent.CTRL_MASK)); // Ctrl+N
		stuQuDeItem.setAccelerator(KeyStroke.getKeyStroke('F',
				InputEvent.CTRL_MASK));
		stuAlteItem.setAccelerator(KeyStroke.getKeyStroke('A',
				InputEvent.CTRL_MASK));
		checkRoItem.setAccelerator(KeyStroke.getKeyStroke('C',
				InputEvent.CTRL_MASK));
		sanInspItem.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_MASK));
		altePassItem.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.CTRL_MASK));
		logOffItem.setAccelerator(KeyStroke.getKeyStroke('G',
				InputEvent.CTRL_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke('W',
				InputEvent.CTRL_MASK));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_MASK));

		// 添加菜单到主菜单栏
		mainMenu.add(stuManaMenu);
		mainMenu.add(roomRecoMenu);
		mainMenu.add(otherMenu);

		// 设置主菜单到窗体
		mainFrame.setJMenuBar(mainMenu);
	}

	// 主函数
	public static void main(String[] args) {
		new WelManager();
	}

	// 新生入住 菜单 监听器
	class StuArriListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			//JOptionPane.showMessageDialog(null, "新生入住");
			new StuAdmission();
		}

	}

	     /*--------------------未完成---------------------*/
	// 查询删除 菜单 监听器
	class StuQuDeListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "查询删除");
		}

	}

	     /*--------------------未完成---------------------*/
	// 修改信息 菜单 监听器
	class StuAlteListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "修改信息");
		}

	}
 
	    /*--------------------未完成---------------------*/
	// 查寝 菜单 监听器
	class CheckRoListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "查寝");
		}

	}

	    /*--------------------未完成---------------------*/
	// 卫生检查 菜单 监听器
	class SanInspListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "卫生检查");
		}

	}

	// 修改密码 菜单 监听器
	class AltePassListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			//调用修改密码的界面    参数:用户类型,账号
			new AlterPasswrd(Manager.getStaffType(),Manager.getAccount());  
		}

	}

	// 注销 菜单 监听器
	class LogOffListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			Icon icon = new ImageIcon("image//menuIcon//注销.png", "退出");
			// 确认对话框 返回:指示用户所选选项的 int 选择第一项就返回0,第二项是1
			int select = JOptionPane.showConfirmDialog(mainFrame, "您确定要注销吗?",
					"是否注销", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (select == 0) {   //用户选择是  则注销
				Manager.onDestroy();
				mainFrame.dispose();   //关闭窗口
				new Login();
			} 
			
		}

	}

	// 退出 菜单 监听器
	class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			Icon icon = new ImageIcon("image//menuIcon//退出.png", "退出");
			// 确认对话框 返回:指示用户所选选项的 int 选择第一项就返回0,第二项是1
			int select = JOptionPane.showConfirmDialog(mainFrame, "您确定要退出吗?",
					"是否退出", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (select == 0) {   //用户选择是  则退出
				mainFrame.dispose();   //关闭窗口
			} 
		}

	}

	// 关于作者 菜单 监听器
	class AboutListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// 实例化Icon对象,Icon是接口,这里是用ImageIcon实例化的,参数:文件名称,图像的简明文本描述
			Icon icon = new ImageIcon("image//menuIcon//关于作者.jpg", "关于作者");
			try {
				JOptionPane.showMessageDialog(null, "我是作者！！！", "作 者 ~",
						JOptionPane.INFORMATION_MESSAGE, icon);
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		}

	}

}
