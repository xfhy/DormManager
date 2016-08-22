package com.login;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.CommonOperate;
import com.houseParent.Manager;
import com.houseParent.WelManager;
import com.student.Student;
import com.student.StudentInfo;

/**
 * 2016��8��15��12:57:07
 * 
 * ϵͳ����Ա,�������Ա,ѧ����¼
 * 
 * @author XFHY
 * 
 * 8.18:����������Ա�޸�����,ע��,�˳�,��������   �½�Manager��PrivaStudent��,�û��ݴ��¼�ɹ��û�������.ע��ʱ����(��ʼ��)����
 * 
 */
public class Login {
	
	//����
	JFrame loginJframe = new JFrame("��¼����");
	
	JPanel userPanel = new JPanel();      //�û����
	JPanel passwdPanel = new JPanel();    //�������
	JPanel selectPanel = new JPanel();    //ѡ�����
	JPanel loginPanel = new JPanel();     //��¼���
	
	JLabel userLabel = null; //����������ǩ
	JLabel passwdLabel = null; //���������ǩ
	JTextField userText = new JTextField(20); //�ı���
	JPasswordField passwdText = new JPasswordField(20); //�����
	
	
	
	JLabel selectLabel = null;//
	DefaultComboBoxModel<String> selectContent = new DefaultComboBoxModel<String>();   //�����������
	JComboBox<String> selectBox = null;   //������
	
	//��¼��ť
	Icon icon = new ImageIcon("image//loginButtonBack.jpg","��¼");
	JButton loginButton = new JButton("",icon);

	//���췽��
	public Login() {
		//����ȫ������                                               ����:����,����,��С
		CommonOperate.InitGlobalFont(new Font("����", Font.BOLD, 20));
		init();
	}

	//��ʼ��
	public void init() {
		
		userLabel = new JLabel("�˺�:"); 
		passwdLabel = new JLabel("����:");
		selectLabel = new JLabel("��ѡ�����:   ");
		
		//�˻���
		userPanel.add(userLabel);
		userPanel.add(userText);
		
		//������
		passwdPanel.add(passwdLabel);
		passwdText.setEchoChar('��');  //���û����ַ�
		passwdPanel.add(passwdText);
		
		
		//ѡ����
		selectContent.addElement("ѧ��");
		selectContent.addElement("�������Ա");
		selectContent.addElement("ϵͳ����Ա");
		selectBox = new JComboBox<String>(selectContent);  //��ʼ�������б�
		selectPanel.add(selectLabel);
		selectPanel.add(selectBox);
		
		/*----------------��������------------------*/
		userText.setText("d");
		passwdText.setText("d");
		selectBox.setSelectedIndex(1);  //1�ǵڶ���   ���������Ա
		
		//��¼��
		loginButton.setContentAreaFilled(false);  //�ð�ť͸��
		loginButton.setBorderPainted(false);   //ȥ����ť�߿�
		loginPanel.add(loginButton);
		
		   /*-----------ע���¼��ť�����¼�---------------*/
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				loginProcess();  //�ж��û��Ƿ�Ϊ��ȷ���û�
			}
		});
		
		    /*-----------ע����enter�����¼�----------*/
		userText.addKeyListener(enterListener);
		passwdText.addKeyListener(enterListener);
		selectBox.addKeyListener(enterListener);
		loginButton.addKeyListener(enterListener);
		
		//�����嵽������
		loginJframe.add(userPanel);
		loginJframe.add(passwdPanel);
		loginJframe.add(selectPanel);
		loginJframe.add(loginPanel);
		
		//���ò���,���ϵ���
		loginJframe.setLayout(new BoxLayout(loginJframe.getContentPane(), BoxLayout.Y_AXIS));  
		
		// ���,�߶�
		Dimension d = new Dimension(320, 220); // ����һ��Dimension����,�������ô��ڴ�С��
		Point p = new Point(100, 100); // ����һ��Point,�������ô��ڵĳ�ʼλ��
		loginJframe.setSize(d); // ���ô����С
		loginJframe.setLocation(p);
		loginJframe.setResizable(false); // ���ô��ڴ�С���ɱ�
		showUI();
	}
    
	//��ʾUI
	public void showUI() {
		loginJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô��ڹرշ�ʽ
		loginJframe.setVisible(true); // ���ô���ɼ�
	}
	
	//������
	public static void main(String[] args) {
		new Login();
	}
	
	/**
	 * enter���ļ�����
	 */
	KeyListener enterListener = new KeyListener() {

		public void keyTyped(KeyEvent arg0) {
			
		}
		
		public void keyReleased(KeyEvent arg0) {
			
		}
		
		//�û��������ʱ����
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER){
				loginProcess();  //�ж��û��Ƿ�Ϊ��ȷ���û�
			}
		}
	};
	
	
	/**
	 * �ж��û��Ƿ�Ϊ��ȷ���û�
	 * �û�����˵�¼��ť���߰���enter��   ��ִ�������
	 */
	public void loginProcess() {
		//��ȡ�û��������ѡ��,ѡ��ĵ�¼����
		String selectType = selectBox.getItemAt(selectBox.getSelectedIndex()).toString();
		String username = userText.getText();
		//getPassword():���ش� TextComponent �����������ı�
		String password = new String(passwdText.getPassword());  
		
		 //�ж��û������Ƿ�Ϊ��
		if(username.equals("") || password.equals("")){  
			//ʵ����Icon����,Icon�ǽӿ�,��������ImageIconʵ������,����:�ļ�����,ͼ��ļ����ı�����
			Icon icon = new ImageIcon("image//warning.jpg","����");
			try {
				JOptionPane.showMessageDialog(null, "�û�����������Ϊ�գ�����", "�� �� !", 
						JOptionPane.WARNING_MESSAGE, icon);
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		} else {   //����ǿ�
			
			//isSucceed():�ж��û���¼�Ƿ�ɹ� 
			//����ֵ  1:��¼�ɹ�,�ҵ��˺Ϸ����û�     0:���ǺϷ��û�      -1:�����ݿⶼû�����ӳɹ�
			int loginCode = LoginCheck.isSucceed(selectType, username, password);
			if( loginCode == 1 ){  //��¼�ɹ�
				//JOptionPane.showMessageDialog(null, "��¼�ɹ�!!!");
				loginSucceed();   //����Ӧ�Ľ���
			} else if ( loginCode == 0 ){
				//ʵ����Icon����,Icon�ǽӿ�,��������ImageIconʵ������,����:�ļ�����,ͼ��ļ����ı�����
				Icon icon = new ImageIcon("image//fail.png","��¼ʧ��");
				try {
					JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ�����\n�����û����������Ƿ�������ȷ",
							"��¼ʧ�� !", JOptionPane.WARNING_MESSAGE, icon);
				} catch (HeadlessException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	/**
	 * ��¼�ɹ�,��Ҫ�ж�һ���Ǻ�����Ա����,Ȼ�����Ӧ�Ľ���
	 */
	private void loginSucceed(){
		//��ȡ��ǰ�������ѡ������
		String type = selectBox.getItemAt(selectBox.getSelectedIndex()).toString();
		
		//�ж��Ǻ�����Ա����
		if(type.equals("ѧ��")){
			loginJframe.dispose();   //�رմ˴���
			//Student.setAccount(userText.getText());
			Student.setTempAccount(userText.getText());
			new StudentInfo();   //������ʾѧ����Ϣ�Ľ���
		} else if(type.equals("�������Ա")){
			loginJframe.dispose();   //�رմ˴���
			Manager.setAccount(userText.getText());   //�����������Ա���˻�
			new WelManager();
		} else if(type.equals("ϵͳ����Ա")){
			JOptionPane.showMessageDialog(null,"ϵͳ����Ա");
		}
	}
	
}
