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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dataControl.DatabaseConnect;

/**
 * 2016��8��20��16:34:04 ������ס
 * 
 * @author XFHY
 * 
 */
public class StuAdmission {

	public static final int ROOM_COUNT = 6; // һ�����ҵ�����
	public static final int DORM_FLOOR = 6; // ����¥��
	public static final int FLOOR_ROOM_COUNT = 10; // һ��¥���������Ŀ

	// ������
	JFrame mainFrame = new JFrame("������ס�Ǽ�");

	// �����ǩ
	JLabel idLabel = new JLabel("ѧ��:", JLabel.RIGHT);
	JLabel nameLabel = new JLabel("����:", JLabel.RIGHT);
	JLabel sexLabel = new JLabel("�Ա�:", JLabel.RIGHT);
	JLabel classLabel = new JLabel("�༶:", JLabel.RIGHT);
	JLabel collegeLabel = new JLabel("Ժϵ:", JLabel.RIGHT);
	JLabel bedLabel = new JLabel("��λ:", JLabel.RIGHT);
	JLabel roomLabel = new JLabel("���ұ��:", JLabel.RIGHT);
	JLabel roomBedInfo = new JLabel("        ", JLabel.LEFT); // ��ʾ�������Ҵ�λ��Ϣ

	// �����ı������
	JTextField idText = new JTextField(20);
	JTextField nameText = new JTextField(20);
	// JTextField sexText = new JTextField(20);
	JComboBox<String> sexChooser = new JComboBox<String>(); // �Ա��������б�ȽϺ�
	JTextField classText = new JTextField(20);

	// ����������
	JComboBox<String> collegeChooser = new JComboBox<String>();
	JComboBox<String> bedChooser = new JComboBox<String>();
	JPanel roomPanel = new JPanel(new BorderLayout()); // ר������װ¥��������������
	JComboBox<String> floorChooser = new JComboBox<String>(); // ¥��
	JComboBox<String> roomChooser = new JComboBox<String>(); // ����

	// ���尴ť
	JButton addStuBtn = new JButton("���ѧ��");
	JButton resetBtn = new JButton("����");
	JButton cancelBtn = new JButton("ȡ��");

	// �����ϱߵĲ���
	JPanel labelPanel = new JPanel(); // JLabel����
	JPanel textPanel = new JPanel(); // JTextField����
	JPanel topPanel = new JPanel(); // �ϱߵĲ���

	// ��ť�Ĳ���
	FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 100, 20);
	JPanel buttonPanel = new JPanel(flowLayout);

	// ���췽��
	public StuAdmission() {
		init();
	}

	// ��ʼ��
	private void init() {
		// ��Ӱ�ť����ť���
		buttonPanel.add(addStuBtn);
		buttonPanel.add(resetBtn);
		buttonPanel.add(cancelBtn);

		// Ϊ��ť��Ӽ�����
		addStuBtn.addActionListener(new AddStuListener());
		resetBtn.addActionListener(new ResetListener());
		cancelBtn.addActionListener(new CancelListener());

		// ���JLabel��JLabel���
		labelPanel.setLayout(new GridLayout(7, 1, 20, 20));
		labelPanel.add(idLabel);
		labelPanel.add(nameLabel);
		labelPanel.add(sexLabel);
		labelPanel.add(classLabel);
		labelPanel.add(collegeLabel);
		labelPanel.add(bedLabel);
		labelPanel.add(roomLabel);

		// ���JTextField��JTextField���
		textPanel.setLayout(new GridLayout(7, 1, 20, 20));
		textPanel.add(idText);
		textPanel.add(nameText);
		textPanel.add(sexChooser);
		textPanel.add(classText);
		textPanel.add(collegeChooser);
		textPanel.add(bedChooser);
		roomPanel.add(floorChooser, BorderLayout.LINE_START); // ¥������������򲼾�����
		roomPanel.add(roomChooser, BorderLayout.LINE_END);
		textPanel.add(roomPanel);

		idText.addKeyListener(new NumberListener());
		classText.addKeyListener(new NumberListener());

		initChooser(); // ��ʼ��������
		
		// ����Ĳ��� ���JLabel��,JTextField��
		topPanel.setLayout(new GridLayout(1, 2, 20, 20));
		topPanel.add(labelPanel);
		topPanel.add(textPanel);

		// �������ڵĲ��� ����Ĳ�����LINE_START,�������PAGE_END
		mainFrame.add(topPanel, BorderLayout.LINE_START);
		mainFrame.add(buttonPanel, BorderLayout.PAGE_END);
		mainFrame.add(roomBedInfo, BorderLayout.BEFORE_FIRST_LINE);

		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	// ��ʼ��������
	private void initChooser() {

		sexChooser.addItem("��ѡ���Ա�");
		sexChooser.addItem("��");
		sexChooser.addItem("Ů");

		// ��ʼ�� ѧԺ �Ǹ�������
		collegeChooser.addItem("��ѡ��ѧԺ");
		collegeChooser.addItem("�������ѧѧԺ");
		collegeChooser.addItem("����ѧԺ");
		collegeChooser.addItem("��ѧѧԺ");
		collegeChooser.addItem("����ѧԺ");
		collegeChooser.addItem("��ѧѧԺ");

		// ��ʼ�� ��λ �Ǹ�������
		bedChooser.addItem("��ѡ��λ");
		for (int i = 1; i <= ROOM_COUNT; i++) {
			bedChooser.addItem(i + "");
		}

		// ��ʼ�� ¥�� �Ǹ�������
		floorChooser.addItemListener(new FloorListener());
		floorChooser.addItem("��ѡ��¥��");
		for (int i = 1; i <= DORM_FLOOR; i++) {
			floorChooser.addItem(i + "");
		}

		// ��ʼ�� ���� �Ǹ�������
		roomChooser.addItemListener(new RoomListener());
		int temp = 1;
		roomChooser.addItem("��ѡ������");
		for (int d = 1; d <= DORM_FLOOR; d++) {
			for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
				temp = d * 100 + i;
				if (!isRoomFull(temp + "")) { // �ж���������Ƿ�����
					roomChooser.addItem(temp + "");
				}
			}
		}

	}

	// ��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ������
	public static void main(String[] args) {
		new StuAdmission();
	}

	// ���������� ����:�������� ����ֻ����������
	class NumberListener implements KeyListener {

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent arg0) {

		}

		// ����������
		public void keyTyped(KeyEvent e) {
			// ��������Ĵ���� ֻ������0~9����,�����为��,������С����,����ɾ��(Backspace)

			int keyChar = e.getKeyChar(); // ��������¼��������ַ�
			if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

			} else {
				e.consume(); // �ؼ�,���ε��Ƿ�����
			}
		}

	}

	// �����б� ������ ����¥��ѡ����Ǹ�������ļ�����   ����ѡ�����ʱ��aListener ������һ�������� ItemEvent
	class FloorListener implements ItemListener {

		// ���û�ѡ����¥��֮��,����� ���������б� ��Ӧ�ĸ��Ÿ���һ��,�����û�ѡ��1¥,��ֻ��ʾ1¥�����ұ��
		public void itemStateChanged(ItemEvent event) {

			roomChooser.removeAllItems();

			Object itemObject = event.getItem();
			String floorString = itemObject.toString();
			if (floorString.equals("��ѡ��¥��")) {
				// ��ʼ�� ���� �Ǹ�������
				int temp = 1;
				roomChooser.addItem("��ѡ������");
				for (int d = 1; d <= DORM_FLOOR; d++) {
					for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
						temp = d * 100 + i;
						roomChooser.addItem(temp + "");
					}
				}
			} else {
				int floor = Integer.parseInt(itemObject.toString());
				int temp = 1;
				for (int i = 1; i <= FLOOR_ROOM_COUNT; i++) {
					temp = floor * 100 + i;
					roomChooser.addItem(temp + "");
				}
			}

		}

	}

	// �����б� ������ ��������ѡ����Ǹ�������ļ�����
	class RoomListener implements ItemListener {

		public void itemStateChanged(ItemEvent arg0) {
			List<Integer> list = new ArrayList<Integer>(); // �����洢�Ѿ�����˯�˵Ĵ�λ
			
			String selectSex = sexChooser.getItemAt(sexChooser.getSelectedIndex());    //�û�ѡ����Ա�
			String selectRoom = roomChooser.getItemAt(roomChooser.getSelectedIndex());  //�û�ѡ�������
			
			//�ж��û��Ƿ�ѡ������ȷ������
			if(selectRoom != null && selectRoom.equals("��ѡ������")){  //�����Ա������б�
				roomBedInfo.setText("");
				sexChooser.removeAllItems();   //�Ƴ�����ѡ��
				sexChooser.addItem("��ѡ���Ա�");
				sexChooser.addItem("��");
				sexChooser.addItem("Ů");
			} else if(selectRoom != null){
				list = bedInfo(selectRoom); // ��ȡ�����ҵ������Ѿ���ռ�õĴ�λ
				StringBuffer str = new StringBuffer();
				for (Integer integer : list) {
					str.append(integer + " ");
					bedChooser.removeItem(integer + ""); // ���Ѿ�����˯�˵Ĵ�λ�� ��λ�����б����Ƴ�
				}

				// �����������˯,��ʾ��λ��Ϣ
				if (list.size() != 0) {
					roomBedInfo.setText("������ҵ�" + str.toString() + "��λ�Ѿ�����˯��");
				} else { // �����������˯,����ʾ
					roomBedInfo.setText("�������Ŀǰ����ס,�����ⰲ�Ŵ�λ");
				}
				
				String roomSex = roomSexInfo(selectRoom);   //������ҵ��Ա�
				
				//�жϸ�����ס�޵�������������Ů��
				if(roomSex.equals("Ů")){
					if (selectSex != null) {
						if (selectSex.equals("��") || selectSex.equals("��ѡ���Ա�")) {
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2 + "  ��������Ů������"); // ��ʾ�û�����Ů������
							sexChooser.removeItem("��"); // �����Ա������б��� �� �Ƴ�
							sexChooser.setSelectedItem("Ů"); // �������Ա������б�Ϊ Ů ѡ��
						}
					}
					
				} else if(roomSex.equals("��")){
					if(selectSex != null){
						if(selectSex.equals("Ů") || selectSex.equals("��ѡ���Ա�")){
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2+"  ����������������");
							sexChooser.removeItem("Ů");
							sexChooser.setSelectedItem("��");
						}
					}
				} else if(roomSex.equals("")){
					sexChooser.removeAllItems();
					sexChooser.addItem("��ѡ���Ա�");
					sexChooser.addItem("��");
					sexChooser.addItem("Ů");
					sexChooser.setSelectedItem("��ѡ���Ա�");
				}
			}
			
		}

	}
  
	//���ѧ��  ��ť  ������
	/*-----------------δ���-----------------*/
	// ���ѧ����ť������
	class AddStuListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			checkFirst(); // ִ�����ǰ������� �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
			addStu(); // ���ѧ����Ϣ�����ݿ�
		}

	}

	//����  ��ť  ������
	// ���ð�ť������
	class ResetListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			idText.setText("");
			nameText.setText("");
			sexChooser.removeAllItems();
			classText.setText("");

			collegeChooser.removeAllItems();
			bedChooser.removeAllItems();
			floorChooser.removeAllItems();
			roomChooser.removeAllItems();
			initChooser(); // ��ʼ��������
		}

	}

	//ȡ��  ��ť  ������
	// ȡ����ť������
	class CancelListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose(); // �رյ�ǰ����
		}

	}

	//��������û������Ƿ���ȷ   δ�������ݿ�
	// ִ�����ǰ������� �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
	private void checkFirst() {

		// �ж��û��Ƿ�����һ������ ������һ��ûѡ
		if (idText.getText().equals("") || nameText.getText().equals("")
				|| sexChooser.getSelectedItem().toString().equals("��ѡ���Ա�")
				|| classText.getText().equals("")
				|| collegeChooser.getSelectedItem().toString().equals("��ѡ��ѧԺ")
				|| bedChooser.getSelectedItem().toString().equals("��ѡ��λ")
				|| floorChooser.getSelectedItem().toString().equals("��ѡ��¥��")
				|| roomChooser.getSelectedItem().toString().equals("��ѡ������")) {
			JOptionPane.showMessageDialog(null, "��,�������ջ�����һ�ѡŶ~~");
			return;
		}

		// ������������ʽ ^:�еĿ�ͷ [\u4e00-\u9fa5]{2,8}:����2~8���� |:����
		// [a-zA-Z]{2,16}:Ӣ��2~16����ĸ $:�еĽ�β
		String nameRegex = "^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
		boolean isName = nameText.getText().matches(nameRegex); // �ж��û�������Ƿ�����ȷ������
		if (!isName) {
			JOptionPane.showMessageDialog(null, "��,���������������Ŷ~~");
		}
	}

	//���ѧ�������ݿ�
	// ���ѧ����Ϣ�����ݿ�
	private void addStu() {

		Connection conn = DatabaseConnect.connDatabase();
		PreparedStatement preState = null;
		int changeRows = 0; // ���ص� SQL ���ݲ������� (DML) ��������
		// ѧ��,����,����(Ĭ��0000),�Ա�,�༶,Ժϵ,��λ,���ұ��(���)
		String sql = "insert into student_info values(?,?,'0000',?,?,?,?,?)"; // ��Ҫִ�е�sql���
		try {
			preState = conn.prepareStatement(sql);
			preState.setString(1, idText.getText());
			preState.setString(2, nameText.getText());
			preState.setString(3,
					sexChooser.getItemAt(sexChooser.getSelectedIndex()));
			preState.setString(4, classText.getText());
			preState.setString(5,
					collegeChooser.getItemAt(collegeChooser.getSelectedIndex()));
			preState.setString(6,
					bedChooser.getItemAt(bedChooser.getSelectedIndex()));
			preState.setString(7,
					roomChooser.getItemAt(roomChooser.getSelectedIndex()));

			changeRows = preState.executeUpdate();
			if (changeRows > 0) { // ������������>0
				JOptionPane.showMessageDialog(null, "��ӳɹ�! ! !");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���ʧ��! ! !");
		} finally {
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
	}

	//�жϸ������Ƿ��Ѿ�����
	// �ж���������Ƿ��Ѿ�ס��
	private boolean isRoomFull(String roomNumber) {
		// ���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		// SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;
		// ���صĽ�� ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resultSet = null;
		int num = 0; // ��������

		try {
			// select num from bedroom where roomnum='101';
			String sql = "select num from bedroom where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // �������ݿ�
			preState = conn.prepareStatement(sql); // ����PreparedStatement����

			// setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~ ����λ���Ǵ�1��ʼ
			preState.setString(1, roomNumber);
			// ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null
			resultSet = preState.executeQuery();

			if (resultSet.next()) {
				num = resultSet.getInt("num");
				if (num == ROOM_COUNT) {
					return true; // ������������
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeResultset(resultSet); // �ر�ResultSet
			DatabaseConnect.closeStatement(preState); // �ر�PreparedStatement
			DatabaseConnect.closeConnection(conn); // �ر�Connection
		}
		return false;
	}

	//���ظ����ҵ������Ѿ�ռ�˵Ĵ�λ
	// ������������Ѿ�����˯�Ĵ�λ
	private List<Integer> bedInfo(String roomNumber) {
		List<Integer> list = new ArrayList<Integer>(); // �����洢�Ѿ�����˯�˵Ĵ�λ

		// ���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		// SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;
		// ���صĽ�� ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resultSet = null;

		try {
			// select bed from student_info where roomnum='101'; --��ѯ������ҵĴ�λ��Ϣ
			String sql = "select bed from student_info where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // �������ݿ�
			preState = conn.prepareStatement(sql); // ����PreparedStatement����

			// setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~ ����λ���Ǵ�1��ʼ
			preState.setString(1, roomNumber);
			// ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null
			resultSet = preState.executeQuery();

			/*
			 * �����ӵ�ǰλ����ǰ��һ�С�ResultSet ������λ�ڵ�һ��֮ǰ����һ�ε��� next ����ʹ��һ�г�Ϊ��ǰ�У�
			 * �ڶ��ε���ʹ�ڶ��г�Ϊ��ǰ�У��������ơ� ������ next �������� false ʱ�����λ�����һ�еĺ��档
			 */
			while (resultSet.next()) {
				list.add(resultSet.getInt("bed")); // ��λ
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeResultset(resultSet); // �ر�ResultSet
			DatabaseConnect.closeStatement(preState); // �ر�PreparedStatement
			DatabaseConnect.closeConnection(conn); // �ر�Connection
		}

		return list;
	}

	// ����������ҵ��Ա�  ����:��,Ů,""
	private String roomSexInfo(String roomNumber) {
		String sex = null;
		
		// ���ض����ݿ�����ӣ��Ự������������������ִ�� SQL ��䲢���ؽ��
		Connection conn = null;
		// SQL ��䱻Ԥ���벢�洢�� PreparedStatement �����С�Ȼ�����ʹ�ô˶����θ�Ч��ִ�и���䡣
		PreparedStatement preState = null;
		// ���صĽ�� ��ʾ���ݿ����������ݱ�ͨ��ͨ��ִ�в�ѯ���ݿ���������
		ResultSet resultSet = null;

		try {
			// select sex from bedroom where roomnum='101';
			String sql = "select sex from bedroom where roomnum=?";
			conn = DatabaseConnect.connDatabase(); // �������ݿ�
			preState = conn.prepareStatement(sql); // ����PreparedStatement����
			
			// setString()�����Ǹ�����и�ֵ�������ܸ�ֱ�Ӹ�����~ ����λ���Ǵ�1��ʼ
			preState.setString(1, roomNumber);
			// ִ�в�ѯ���,���ذ����ò�ѯ���ɵ����ݵ� ResultSet ���󣻲��᷵�� null
			resultSet = preState.executeQuery();
			
			if(resultSet.next()){
				sex = resultSet.getString("sex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �������Լ��������DatabaseConnect����ľ�̬����,�����ر�������Щ������
			DatabaseConnect.closeResultset(resultSet); // �ر�ResultSet
			DatabaseConnect.closeStatement(preState); // �ر�PreparedStatement
			DatabaseConnect.closeConnection(conn); // �ر�Connection
		}
		
		if(sex != null){
			return sex;
		} else {
			return "";
		}
	}

}
