package com.student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 2016��8��16��22:35:45
 * 
 * ��ʾѧ����Ϣ
 * 
 * @author XFHY
 * 
 * ʹ�ø���ʱ,��Ҫ����ѧ��ѧ��,ͨ��setAccount()����
 * 
 */
public class StudentInfo{
	
	//String account = null;  //ѧ��ѧ��
	
	JFrame mainFrame = new JFrame("ѧ��������Ϣ");
	
	JLabel idLabel = new JLabel("ѧ��");
	JLabel nameLabel = new JLabel("����");
	JLabel sexLabel = new JLabel("�Ա�");
	JLabel classLabel = new JLabel("�༶");
	JLabel facultyLabel = new JLabel("Ժϵ");
	JLabel bedLabel = new JLabel("��λ");
	JLabel roomLabel = new JLabel("����");
	
	JTextField idText = new JTextField(20);     //ѧ��
	JTextField nameText = new JTextField(20);   //����
	JTextField sexText = new JTextField(20);    //�Ա�
	JTextField classText = new JTextField(20);  //�༶
	JTextField facultyText = new JTextField(20);//Ժϵ
	JTextField bedText = new JTextField(20);    //��λ
	JTextField roomText = new JTextField(20);   //���ұ��
	
	JButton alterPasswrd = new JButton("�޸�����");
	JButton exitButton = new JButton("�˳�");
	
	//���
	JPanel idPanel = new JPanel();
	JPanel namePanel = new JPanel();
	JPanel sexPanel = new JPanel();
	JPanel classPanel = new JPanel();
	JPanel facultyPanel = new JPanel();
	JPanel bedPanel = new JPanel();
	JPanel roomPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	//���췽��
	public StudentInfo(){
		//this.account = account; //�ȳ�ʼ���˺�
		
		//��ʼ������   �õ�ѧ�����ݲ����õ��ؼ���  ��Ҫ�ڳ�ʼ������֮ǰ��Ҫ��ȡ������
		getStudentData();
		init();
	}
	
	//��ʼ������
	private void init() {
		
		idText.setEditable(false);
		nameText.setEditable(false);
		sexText.setEditable(false);
		classText.setEditable(false);
		facultyText.setEditable(false);
		bedText.setEditable(false);
		roomText.setEditable(false);
		
		//��JLabel��JTextField��ӵ����
		idPanel.add(idLabel);
		idPanel.add(idText);
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		sexPanel.add(sexLabel);
		sexPanel.add(sexText);
		classPanel.add(classLabel);
		classPanel.add(classText);
		facultyPanel.add(facultyLabel);
		facultyPanel.add(facultyText);
		bedPanel.add(bedLabel);
		bedPanel.add(bedText);
		roomPanel.add(roomLabel);
		roomPanel.add(roomText);
		buttonPanel.add(alterPasswrd);
		buttonPanel.add(exitButton);
		
		//��Ӱ�ť������
		alterPasswrd.addActionListener(new AlterEventListener());
		exitButton.addActionListener(new ExitEventListener());
		
		
		
		//�������ӵ���������
		mainFrame.add(idPanel);
		mainFrame.add(namePanel);
		mainFrame.add(sexPanel);
		mainFrame.add(classPanel);
		mainFrame.add(facultyPanel);
		mainFrame.add(bedPanel);
		mainFrame.add(roomPanel);
		mainFrame.add(buttonPanel);
		
		//��������������
		mainFrame.setLayout(new GridLayout(8,2));
		
		mainFrame.setSize(500,600);      //���ô�С
		mainFrame.setLocation(300,100);  //���ô��ڳ�ʼλ��
		mainFrame.setResizable(false);   //���ڴ�С���ɱ�
		showUI();
	}
	
	//��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô��ڹرշ�ʽ
		mainFrame.setVisible(true);
	}

	
	/**
	 * �õ�ѧ��������,�����õ��ؼ���
	 */
	private void getStudentData(){
		idText.setText(Student.getAccount());
		
		//����û��ǵ�һ�ν���,����Ҫ�����ݿ��������       ����û��ǵڶ��ν���(�м䵢����,����ȥ�޸����������),��ȥelse����
		if( Student.getName().equals("") ){
			//��GetStudentInfo�ľ�̬����getStudentInfo()�л�ȡ�������ݵ�Object����
			//getStudentInfo():ͨ���������ݿ�,��ȡĳ��ѧ����Ϣ,ȷ��ѧ����id  ���ش���ѧ����Ϣ��Object[]����
			Object[] data = StudentOperate.getStudentInfo(Student.getAccount());
			if(data[0] != null){   //������ݿ����ӳɹ���������Ч����
				//�ֱ����ÿؼ���ֵ
				nameText.setText(data[0].toString());
				sexText.setText(data[1].toString());
				classText.setText(data[2].toString());
				facultyText.setText(data[3].toString());
				bedText.setText(data[4].toString());
				roomText.setText(data[5].toString());
				
				//�����ݷŵ�Student�����ݴ�    ��һ������
				Student.setName(nameText.getText());
				Student.setSex(sexText.getText());
				Student.setClassroom(classText.getText());
				Student.setCollege(facultyText.getText());
				Student.setBed(bedText.getText());
				Student.setRoomnum(roomText.getText());
			} else {
				nameText.setText("δ��ȡ������");
				sexText.setText("δ��ȡ������");
				classText.setText("δ��ȡ������");
				facultyText.setText("δ��ȡ������");
				bedText.setText("δ��ȡ������");
				roomText.setText("δ��ȡ������");
			}
		} else {   //����û��ǵڶ��ν���(�м䵢����,����ȥ�޸����������),������ȥ�����ݿ��л�ȡ����,ֱ�Ӵ�Student����ȡ��������
			//�ֱ����ÿؼ���ֵ
			nameText.setText(Student.getName());
			sexText.setText(Student.getSex());
			classText.setText(Student.getClassroom());
			facultyText.setText(Student.getCollege());
			bedText.setText(Student.getBed());
			roomText.setText(Student.getRoomnum());
		}
		
	}
	
	//�ڲ���,ʵ��ActionListener,������,�����޸����밴ť�ļ�����
    class AlterEventListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			 //�����޸�����Ľ���      ����:�û�����,�˺�
			new AlterPasswrd(Student.getStaffType(),Student.getAccount());  
		}
    }
    
    //�ڲ���,ʵ��ActionListener,������,�����˳���ť�ļ�����
    class ExitEventListener implements ActionListener{
    	public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //�˳���ǰ����
		}
    }
}

/*
 *     ---------ѧ����Ϣ��---------
create table student_info
(
   id varchar(10) not null,                                        --ѧ��
   name varchar(10) not null,                                      --����
   passwrd varchar(15) default '0000' not null,                    --����
   sex varchar(2) not null,                                        --�Ա�
   classroom tinyint not null,                                     --�༶
   college varchar(20) not null,                                   --Ժϵ
   bed tinyint,                                                    --��λ
   roomnum varchar(5) not null,                                   --���ұ��
   primary key(id),     --ѧ��������
   foreign key(roomnum) references bedroom(roomnum),          --���ұ�������
   check(sex='��' or sex='Ů'),     --�Ա�ֻ����Ů
   check(classroom>0)               --�༶���Ӧ�ô���0
);--��ЩѧУ�ǻ���,��Ůһ��ס,������Ա��������
 * 
 * */
 