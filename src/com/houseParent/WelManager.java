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
 * 2016��8��18��21:11:37
 * 
 * �������Ա���� 3�����˵� ��1�����˵�:������ס,��ѯɾ��,�޸���Ϣ ��2�����˵�:����,������� ��3�����˵�:�޸�����,ע��,�˳�,����
 * 
 * 
 * @author XFHY
 * 
 * 
 * 
 */
public class WelManager {
	
	// ������
	JFrame mainFrame = new JFrame("�������Ա����");

	// ������� �������ñ���
	BackgroundPanel backgroundPanel = null;

	JMenuBar mainMenu = new JMenuBar(); // ���˵���

	// �˵� ���¿�����Ӳ˵��� �ں��������ĸ�����ÿ�ݼ�����ʵ����ĸ�»���(alt+��ĸ)
	JMenu stuManaMenu = new JMenu("ѧ������(S)");
	JMenu roomRecoMenu = new JMenu("���Ҽ�¼(R)");
	JMenu otherMenu = new JMenu("����(O)");

	// ѧ����Ϣ���� �˵��� �µ��Ӳ˵�
	JMenuItem stuArriItem = new JMenuItem("������ס");
	JMenuItem stuQuDeItem = new JMenuItem("��ѯɾ��");
	JMenuItem stuAlteItem = new JMenuItem("�޸���Ϣ");

	// ���Ҽ�¼ �˵����µ��Ӳ˵�
	JMenuItem checkRoItem = new JMenuItem("����");
	JMenuItem sanInspItem = new JMenuItem("�������");

	// ���� �˵����µ��Ӳ˵�
	JMenuItem altePassItem = new JMenuItem("�޸�����");
	JMenuItem logOffItem = new JMenuItem("ע��");
	JMenuItem exitItem = new JMenuItem("�˳�");
	JMenuItem aboutItem = new JMenuItem("��������");

	// ���췽��
	public WelManager() {
		init();
	}

	// ��ʼ��
	public void init() {

		addMenu(); // ��Ӳ˵�

		// Ϊ�����ӱ���
		backgroundPanel = new BackgroundPanel(
				(new ImageIcon("image//water.jpg")).getImage());
		mainFrame.add(backgroundPanel);

		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	// ��ʾ����
	public void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ����������ñ��������̳���JPanel
	class BackgroundPanel extends JPanel {
		Image image;

		// ���췽�� ����һ��Image����
		public BackgroundPanel(Image image) {
			this.image = image;
			// ���Ϊ true��������������߽��ڵ��������ء������������ܲ����Ʋ��ֻ��������أ��Ӷ�������ײ�����͸�ӳ�����
			this.setOpaque(true);
		}

		// ���Ǹ����paintComponent()����
		// Draw the back ground. ������
		public void paintComponent(Graphics g) {
			// Graphics ��������ͼ�������ĵĳ�����࣬����Ӧ�ó�����������Ѿ��ڸ����豸��ʵ�֣��Լ�����ͼ���Ͻ��л��ơ�
			super.paintComponents(g);
			/**
			 * img - Ҫ���Ƶ�ָ��ͼ����� img Ϊ null����˷�����ִ���κβ����� x - x ���ꡣ y - y ���ꡣ
			 * width - ���εĿ�ȡ� height - ���εĸ߶ȡ� observer - ת���˸���ͼ��ʱҪ֪ͨ�Ķ���
			 * 
			 * ����ָ��ͼ���������ŵ��ʺ�ָ�������ڲ���ͼ��
			 * 
			 */
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	/**
	 * ��ɲ˵����Ľ������
	 */
	private void addMenu() {

		// ѧ����Ϣ���� �˵��� �µ��Ӳ˵�
		Icon icon = new ImageIcon("image//menuIcon//���.png"); // ʵ����Icon����
																// �������ò˵�ͼ��
		stuArriItem.setIcon(icon); // ���ò˵�ͼ��
		icon = new ImageIcon("image//menuIcon//��ѯ.png");
		stuQuDeItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//�޸�.png");
		stuAlteItem.setIcon(icon);
		stuManaMenu.add(stuArriItem);
		stuManaMenu.add(stuQuDeItem);
		stuManaMenu.add(stuAlteItem);
		stuManaMenu.setMnemonic(KeyEvent.VK_S); // ���ò˵���ݼ� (alt+s)

		// ���Ҽ�¼ �˵��� �µ��Ӳ˵�
		icon = new ImageIcon("image//menuIcon//������.png");
		checkRoItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//���.png");
		sanInspItem.setIcon(icon);
		roomRecoMenu.add(checkRoItem);
		roomRecoMenu.add(sanInspItem);
		roomRecoMenu.setMnemonic(KeyEvent.VK_R); // ���ò˵���ݼ� (alt+r)

		// ���� �˵����µ��Ӳ˵�
		icon = new ImageIcon("image//menuIcon//�޸�����.png");
		altePassItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//ע��.png");
		logOffItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//�˳�.png");
		exitItem.setIcon(icon);
		icon = new ImageIcon("image//menuIcon//����.png");
		aboutItem.setIcon(icon);
		otherMenu.add(altePassItem);
		otherMenu.add(logOffItem);
		otherMenu.add(exitItem);
		otherMenu.add(aboutItem);
		otherMenu.setMnemonic(KeyEvent.VK_O); // ���ò˵���ݼ� (alt+o)

		/*----------��Ӳ˵�������-------------*/
		stuArriItem.addActionListener(new StuArriListener()); // ������ס
		stuQuDeItem.addActionListener(new StuQuDeListener()); // ��ѯɾ��
		stuAlteItem.addActionListener(new StuAlteListener()); // �޸���Ϣ
		checkRoItem.addActionListener(new CheckRoListener()); // ����
		sanInspItem.addActionListener(new SanInspListener()); // �������
		altePassItem.addActionListener(new AltePassListener()); // �޸�����
		logOffItem.addActionListener(new LogOffListener()); // ע��
		exitItem.addActionListener(new ExitListener()); // �˳�
		aboutItem.addActionListener(new AboutListener()); // ��������

		/*----------------���ò˵���ݼ�( Ctrl+? )-------------------*/
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

		// ��Ӳ˵������˵���
		mainMenu.add(stuManaMenu);
		mainMenu.add(roomRecoMenu);
		mainMenu.add(otherMenu);

		// �������˵�������
		mainFrame.setJMenuBar(mainMenu);
	}

	// ������
	public static void main(String[] args) {
		new WelManager();
	}

	// ������ס �˵� ������
	class StuArriListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			//JOptionPane.showMessageDialog(null, "������ס");
			new StuAdmission();
		}

	}

	     /*--------------------δ���---------------------*/
	// ��ѯɾ�� �˵� ������
	class StuQuDeListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "��ѯɾ��");
		}

	}

	     /*--------------------δ���---------------------*/
	// �޸���Ϣ �˵� ������
	class StuAlteListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "�޸���Ϣ");
		}

	}
 
	    /*--------------------δ���---------------------*/
	// ���� �˵� ������
	class CheckRoListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "����");
		}

	}

	    /*--------------------δ���---------------------*/
	// ������� �˵� ������
	class SanInspListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "�������");
		}

	}

	// �޸����� �˵� ������
	class AltePassListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			//�����޸�����Ľ���    ����:�û�����,�˺�
			new AlterPasswrd(Manager.getStaffType(),Manager.getAccount());  
		}

	}

	// ע�� �˵� ������
	class LogOffListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			Icon icon = new ImageIcon("image//menuIcon//ע��.png", "�˳�");
			// ȷ�϶Ի��� ����:ָʾ�û���ѡѡ��� int ѡ���һ��ͷ���0,�ڶ�����1
			int select = JOptionPane.showConfirmDialog(mainFrame, "��ȷ��Ҫע����?",
					"�Ƿ�ע��", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (select == 0) {   //�û�ѡ����  ��ע��
				Manager.onDestroy();
				mainFrame.dispose();   //�رմ���
				new Login();
			} 
			
		}

	}

	// �˳� �˵� ������
	class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			Icon icon = new ImageIcon("image//menuIcon//�˳�.png", "�˳�");
			// ȷ�϶Ի��� ����:ָʾ�û���ѡѡ��� int ѡ���һ��ͷ���0,�ڶ�����1
			int select = JOptionPane.showConfirmDialog(mainFrame, "��ȷ��Ҫ�˳���?",
					"�Ƿ��˳�", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, icon);
			if (select == 0) {   //�û�ѡ����  ���˳�
				mainFrame.dispose();   //�رմ���
			} 
		}

	}

	// �������� �˵� ������
	class AboutListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// ʵ����Icon����,Icon�ǽӿ�,��������ImageIconʵ������,����:�ļ�����,ͼ��ļ����ı�����
			Icon icon = new ImageIcon("image//menuIcon//��������.jpg", "��������");
			try {
				JOptionPane.showMessageDialog(null, "�������ߣ�����", "�� �� ~",
						JOptionPane.INFORMATION_MESSAGE, icon);
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		}

	}

}
