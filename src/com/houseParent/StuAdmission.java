package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 2016��8��20��16:34:04 ������ס
 * 
 * @author XFHY
 * 
 */
public class StuAdmission {
	
	public static final int ROOM_COUNT = 6;   //һ�����ҵ�����
	public static final int DORM_FLOOR = 6;   //����¥��
	public static final int FLOOR_ROOM_COUNT = 33; //һ��¥���������Ŀ
	
	//������
	JFrame mainFrame = new JFrame("������ס�Ǽ�");
	
	//�����ǩ
	JLabel idLabel = new JLabel("ѧ��:",JLabel.RIGHT);
	JLabel nameLabel = new JLabel("����:",JLabel.RIGHT);
	JLabel sexLabel = new JLabel("�Ա�:",JLabel.RIGHT);
	JLabel classLabel = new JLabel("�༶:",JLabel.RIGHT);
	JLabel collegeLabel = new JLabel("Ժϵ:",JLabel.RIGHT);
	JLabel bedLabel = new JLabel("��λ:",JLabel.RIGHT);
	JLabel roomLabel = new JLabel("���ұ��:",JLabel.RIGHT);
	
	//�����ı������
	JTextField idText = new JTextField(20);
	JTextField nameText = new JTextField(20);
	//JTextField sexText = new JTextField(20);
	JComboBox<String> sexChooser = new JComboBox<String>();   //�Ա��������б�ȽϺ�
	JTextField classText = new JTextField(20);
	
	//����������
	JComboBox<String> collegeChooser = new JComboBox<String>();
	JComboBox<String> bedChooser = new JComboBox<String>();
	JPanel roomPanel = new JPanel(new BorderLayout());  //ר������װ¥��������������
	JComboBox<String> floorChooser = new JComboBox<String>();  //¥��
	JComboBox<String> roomChooser = new JComboBox<String>(); //����
	
	//���尴ť
	JButton addStuBtn = new JButton("���ѧ��");
	JButton resetBtn = new JButton("����");
	JButton cancelBtn = new JButton("ȡ��");
	
	//�����ϱߵĲ���
	JPanel labelPanel = new JPanel();   //JLabel����
	JPanel textPanel = new JPanel();    //JTextField����
	JPanel topPanel = new JPanel();     //�ϱߵĲ���
	
	//��ť�Ĳ���
	FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,100,20);
	JPanel buttonPanel = new JPanel(flowLayout);
	
	//���췽��
	public StuAdmission() {
		init();
	}

	//��ʼ��
	private void init() {
		//��Ӱ�ť����ť���
		buttonPanel.add(addStuBtn);
		buttonPanel.add(resetBtn);
		buttonPanel.add(cancelBtn);
		
		//Ϊ��ť��Ӽ�����
		addStuBtn.addActionListener(new AddStuListener());
		resetBtn.addActionListener(new ResetListener());
		cancelBtn.addActionListener(new CancelListener());
		
		//���JLabel��JLabel���
		labelPanel.setLayout(new GridLayout(7,1,20,20));
		labelPanel.add(idLabel);
		labelPanel.add(nameLabel);
		labelPanel.add(sexLabel);
		labelPanel.add(classLabel);
		labelPanel.add(collegeLabel);
		labelPanel.add(bedLabel);
		labelPanel.add(roomLabel);
		
		//���JTextField��JTextField���
		textPanel.setLayout(new GridLayout(7,1,20,20));
		textPanel.add(idText);
		textPanel.add(nameText);
		textPanel.add(sexChooser);
		textPanel.add(classText);
		textPanel.add(collegeChooser);
		textPanel.add(bedChooser);
		roomPanel.add(floorChooser,BorderLayout.LINE_START);  //¥������������򲼾�����
		roomPanel.add(roomChooser,BorderLayout.LINE_END);
		textPanel.add(roomPanel);
		
		idText.addKeyListener(new NumberListener());
		classText.addKeyListener(new NumberListener());
		
		initChooser();  //��ʼ��������
		
		//����Ĳ���  ���JLabel��,JTextField��
		topPanel.setLayout(new GridLayout(1,2,20,20));
		topPanel.add(labelPanel);
		topPanel.add(textPanel);
		
		//�������ڵĲ���   ����Ĳ�����LINE_START,�������PAGE_END
		mainFrame.add(topPanel,BorderLayout.LINE_START);
		mainFrame.add(buttonPanel,BorderLayout.PAGE_END);
		
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	//��ʼ��������
	private void initChooser() {
		
		sexChooser.addItem("��ѡ���Ա�");
		sexChooser.addItem("��");
		sexChooser.addItem("Ů");
		
		//��ʼ��   ѧԺ �Ǹ�������
		collegeChooser.addItem("��ѡ��ѧԺ");
		collegeChooser.addItem("�������ѧѧԺ");
		collegeChooser.addItem("����ѧԺ");
		collegeChooser.addItem("��ѧѧԺ");
		collegeChooser.addItem("����ѧԺ");
		collegeChooser.addItem("��ѧѧԺ");
		
		//��ʼ��   ��λ �Ǹ�������
		bedChooser.addItem("��ѡ��λ");
		for (int i = 1; i <= ROOM_COUNT; i++) {
			bedChooser.addItem(i+"");
		}
		
		//��ʼ��  ¥�� �Ǹ�������
		floorChooser.addItemListener(new FloorListener());
		floorChooser.addItem("��ѡ��¥��");
		for (int i = 1; i <= DORM_FLOOR; i++) {
			floorChooser.addItem(i+"");
		}
		
		//��ʼ��  ���� �Ǹ�������
		int temp = 1;
		roomChooser.addItem("��ѡ������");
		for (int d = 1; d <= DORM_FLOOR; d++) {
			for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
				temp = d*100+i;
				roomChooser.addItem(temp+"");
			}
		}
		
	}

	//��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ������
	public static void main(String[] args) {
		new StuAdmission();
	}

	//����������   ����:��������  ����ֻ����������
	class NumberListener implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent arg0) {
			
		}

		//����������
		public void keyTyped(KeyEvent e) {
			//��������Ĵ����    ֻ������0~9����,�����为��,������С����,����ɾ��(Backspace)
			
			int keyChar = e.getKeyChar();    //��������¼��������ַ�
			if( keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 ){
				
			} else {
				e.consume();   //�ؼ�,���ε��Ƿ�����
			}
		}
		
	}
	
	//�����б� ������   ����¥��ѡ����Ǹ�������ļ�����
	class FloorListener implements ItemListener{

		//���û�ѡ����¥��֮��,����� ���������б� ��Ӧ�ĸ��Ÿ���һ��,�����û�ѡ��1¥,��ֻ��ʾ1¥�����ұ��
		public void itemStateChanged(ItemEvent event) {
			
			roomChooser.removeAllItems();
			
			Object itemObject = event.getItem();
			String floorString = itemObject.toString();
			if(floorString.equals("��ѡ��¥��")){
				//��ʼ��  ���� �Ǹ�������
				int temp = 1;
				roomChooser.addItem("��ѡ������");
				for (int d = 1; d <= DORM_FLOOR; d++) {
					for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
						temp = d*100+i;
						roomChooser.addItem(temp+"");
					}
				}
			} else {
				int floor = Integer.parseInt(itemObject.toString());
				int temp = 1;
				for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
					temp = floor*100+i;
					roomChooser.addItem(temp+"");
				}
			}
			
		}
		
	}
	
	    /*-----------------δ���-----------------*/
	//���ѧ����ť������
	class AddStuListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			//JOptionPane.showMessageDialog(null, "ִ�����ǰ�������");
			checkFirst();  //ִ�����ǰ�������   �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
		}
		
	}
	
	//���ð�ť������
	class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			idText.setText("");
			nameText.setText("");
			sexChooser.removeAllItems();
			classText.setText("");
			
			collegeChooser.removeAllItems();
			bedChooser.removeAllItems();
			floorChooser.removeAllItems();
			roomChooser.removeAllItems();
			initChooser();  //��ʼ��������
		}
		
	}
	
	//ȡ����ť������
	class CancelListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //�رյ�ǰ����
		}
		
	}
	
	//ִ�����ǰ�������   �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
	private void checkFirst(){
		
		//�ж��û��Ƿ�����һ������  ������һ��ûѡ
		if( idText.getText().equals("") || nameText.getText().equals("") || 
				sexChooser.getSelectedItem().toString().equals("��ѡ���Ա�") || 
				classText.getText().equals("") || 
				collegeChooser.getSelectedItem().toString().equals("��ѡ��ѧԺ") ||
				bedChooser.getSelectedItem().toString().equals("��ѡ��λ") ||
				floorChooser.getSelectedItem().toString().equals("��ѡ��¥��") ||
				roomChooser.getSelectedItem().toString().equals("��ѡ������")){
			JOptionPane.showMessageDialog(null, "��,�������ջ�����һ�ѡŶ~~");
			return ;
		}
		
		//������������ʽ   ^:�еĿ�ͷ  [\u4e00-\u9fa5]{2,8}:����2~8����  |:����  [a-zA-Z]{2,16}:Ӣ��2~16����ĸ  $:�еĽ�β
		String nameRegex = "^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
		boolean isName = nameText.getText().matches(nameRegex);   //�ж��û�������Ƿ�����ȷ������
		if(!isName){
			JOptionPane.showMessageDialog(null, "��,���������������Ŷ~~");
		}
	}
	
}
