package com.student;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.CommonOperate;

/**
 * 2016��8��17��15:49:36
 * 
 * �����޸�����Ľ��� Ŀǰ����,���ṩȫ������Ա(ѧ��,ϵͳ����Ա,�������Ա)�����޸�����
 * 
 * @author XFHY
 * 
 *         ����������ʹ���޸����������ʱ,��Ҫ�ṩ   �û�����(ѧ��,ϵͳ����Ա,�������Ա),�˺�
 *         
 *    �ж������ǿյİ취:oldPassField.getPassword().length != 0
 */
public class AlterPasswrd {

	String staffType = null; // �û�����
	String account = null; // �˺�

	
	JFrame mainFrame = new JFrame("�޸�����");

	// ��ǩ
	JLabel oldPassLabel = new JLabel("�� �� ��");
	JLabel newPassLabel = new JLabel("�� �� ��");
	JLabel ensurePassLabel = new JLabel("����ȷ��");

	// �����
	JPasswordField oldPassField = new JPasswordField(20);
	JPasswordField newPassField = new JPasswordField(20);
	JPasswordField ensurePassField = new JPasswordField(20);

	// ��ť
	JButton ensureButton = new JButton("ȷ��");
	JButton cancelButton = new JButton("ȡ��");

	// ���
	JPanel oldPanel = new JPanel();
	JPanel newPanel = new JPanel();
	JPanel ensurePanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	// ���췽��
	public AlterPasswrd() {
		init();
	}
	
	/**
	 * ���췽��
	 * @param staffType  �û�����
	 * @param account    �˺�
	 */
	public AlterPasswrd(String staffType,String account) {
		this.staffType = staffType;
		this.account = account;

		init();
	}

	/**
	 * �����û�����
	 * @param staffType  �û�����
	 * 
	 */
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	/**
	 * �����û����˻�
	 * @param account �û����˻�
	 * 
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	// ��ʼ��
	private void init() {

		/*---------�ѿؼ���ӵ����----------------*/
		oldPanel.add(oldPassLabel);  
		oldPassField.setEchoChar('��');  //���û����ַ�
		oldPanel.add(oldPassField);
		
		newPanel.add(newPassLabel);
		newPassField.setEchoChar('��');  //���û����ַ�
		newPanel.add(newPassField);
		
		ensurePanel.add(ensurePassLabel);
		ensurePassField.setEchoChar('��');  //���û����ַ�
		ensurePanel.add(ensurePassField);
		
		buttonPanel.add(ensureButton);
		buttonPanel.add(cancelButton);

		// Ϊ��ť��Ӽ����¼�
		ensureButton.addActionListener(new EnsureEventListener());
		cancelButton.addActionListener(new CancelEventListener());

		// �����嵽������
		mainFrame.add(oldPanel);
		mainFrame.add(newPanel);
		mainFrame.add(ensurePanel);
		mainFrame.add(buttonPanel);

		// ���������ڲ���
		mainFrame.setLayout(new GridLayout(4, 2));

		mainFrame.setSize(350, 250); // ���ô��ڴ�С
		mainFrame.setLocation(300, 100); // ���ó�ʼλ��
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	// ��ʾ����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô��ڹرշ�ʽ
		mainFrame.setVisible(true); // ���ÿɼ�
	}

	// ������
	public static void main(String[] args) {
		new AlterPasswrd();
	}

	/**
	 * @author XFHY ����ȷ���޸�����
	 */
	class EnsureEventListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			//getPassword()�������ص���char[]����,ֱ��toString()�Ļ��õ�����char[]����ĵ�ַ,��Ҫ�����·�ʽת��String���Ͳ���
			String oldPasswrd = new String(oldPassField.getPassword()); 
			String newPasswrd = new String(newPassField.getPassword()); 
			String ensurePasswrd = new String(ensurePassField.getPassword()); 
			
			//��������Ϊ��
			if(oldPasswrd.equals("") || newPasswrd.equals("") || ensurePasswrd.equals("")){
				//ʵ����Icon����,Icon�ǽӿ�,��������ImageIconʵ������,����:�ļ�����,ͼ��ļ����ı�����
				Icon icon = new ImageIcon("image//warning.jpg","����");
				try {
					JOptionPane.showMessageDialog(null, "��,�벻Ҫ����Ŷ������", "�� �� !", 
							JOptionPane.WARNING_MESSAGE, icon);
				} catch (HeadlessException e) {
					e.printStackTrace();
				}
			} else if( !newPasswrd.equals(ensurePasswrd) ){  //�����������ȷ�����벻һ��  
				JOptionPane.showMessageDialog(null,"��������ȷ�����벻һ��");
			} else if(oldPasswrd.equals(newPasswrd)){  //�������������һ��
				JOptionPane.showMessageDialog(null,"�������������һ����,�����޸�....");
			} else if(newPasswrd.length() < 4 || newPasswrd.length() > 15){   //������С��4λ,�򳬹�15λ
				JOptionPane.showMessageDialog(null,"���볤�Ȳ�����Ҫ��,�涨4~15λ�ַ����������");
			} else {  
				//�޸�����
				alterPasswrd(oldPasswrd,newPasswrd);
			}
		}

	}

	/**
	 * �޸�����
	 * @param oldPasswrd  ������
	 * @param newPasswrd  ������
	 */
	private void alterPasswrd(String oldPasswrd,String newPasswrd){
		//isOldPasswrd()���������ж��û�����ľ������Ƿ���ȷ  �������ȷ�򷵻�false
		if(CommonOperate.isOldPasswrd(staffType,account,oldPasswrd)){
			//����alterPasswrd()�����޸�����  �޸ĳɹ��򷵻�true,���򷵻�false
			if( CommonOperate.alterPasswrd(staffType, account, newPasswrd) ){
				mainFrame.dispose();   //�رոô���
				JOptionPane.showMessageDialog(null, "�����޸ĳɹ�! ! !");
				
				    /*--------��ʱ����,��һ����------*/
				if(staffType.equals("ѧ��")){   //�����ǰ�û���ѧ��,�޸���ɷ���ѧ������
					//new StudentInfo(account);  //����ѧ����Ϣ����
				} else if(staffType.equals("�������Ա")){  //�����ǰ�û����������Ա,���ù�,��Ϊ�Ǹ�����δ�رյ�
					//new WelManager();
				}
			} else {
				JOptionPane.showMessageDialog(null, "�����޸�ʧ��! ! !");
			}
		} else {  //�����ƥ��
			JOptionPane.showMessageDialog(null,"������ľ������д�! \n ���֤");
		}
	}
	
	/**
	 * @author XFHY ����ȡ���޸�����,���ص�֮ǰ�Ľ���
	 */
	class CancelEventListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();    //�رյ�ǰ�Ĵ���
			
			   /*-----------------��ʱ����----------------*/
			//new StudentInfo();  //����ѧ����Ϣ����
		}

	}

}
