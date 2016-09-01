package com.houseParent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.CommonOperate;
import com.dataControl.DatabaseConnect;
import com.houseParent.StuAdmission.FloorListener;
import com.houseParent.StuAdmission.NumberListener;
import com.houseParent.StuAdmission.RoomListener;
import com.student.StudentOperate;

/**
 * 2016��8��30��9:59:58 ѧ����Ϣ�޸�
 * 
 * @author XFHY
 * 
 */
public class StuRevise {

	JFrame mainFrame = new JFrame("ѧ����Ϣ�޸�");

	//��ӡ��Ҫ����Ϣ
	Logger log = Logger.getLogger(this.getClass().getName());
	
	// �����ǩ
	JLabel idLabel = new JLabel("ѧ��:", JLabel.LEFT);
	JLabel nameLabel = new JLabel("����:", JLabel.LEFT);
	JLabel sexLabel = new JLabel("�Ա�:", JLabel.LEFT);
	JLabel classLabel = new JLabel("�༶:", JLabel.LEFT);
	JLabel collegeLabel = new JLabel("Ժϵ:", JLabel.LEFT);
	JLabel bedLabel = new JLabel("��λ:", JLabel.LEFT);
	JLabel roomLabel = new JLabel("���ұ��:", JLabel.LEFT);

	// �����
	JTextField idText = new JTextField(20); // ѧ��
	JTextField nameText = new JTextField(20); // ����

	JComboBox<String> sexChooser = new JComboBox<String>(); // �Ա��������б�ȽϺ�

	JTextField classText = new JTextField(20); // �༶
	// ����������
	JComboBox<String> collegeChooser = new JComboBox<String>(); // Ժϵѡ��
	JComboBox<String> bedChooser = new JComboBox<String>(); // ��λѡ��
	JLabel roomBedInfo = new JLabel("        ", JLabel.LEFT); // ��ʾ�������Ҵ�λ��Ϣ

	JPanel roomPanel = new JPanel(new BorderLayout()); // ר������װ¥��������������
	JComboBox<String> roomChooser = new JComboBox<String>(); // ����

	//��ť
	JButton queryBtn = new JButton("��ѯ");
	JButton editBtn = new JButton("�༭");
	JButton resetBtn = new JButton("����");
	JButton saveyBtn = new JButton("����");
	JButton exitBtn = new JButton("�˳�");

	Object[] studentInfo;   //��ʱ���ѧ������Ϣ
	boolean isHideUI = false; //����UI����ʾ
	
	// ���췽��
	public StuRevise() {
		
		init();
	}

	// ��ʼ��
	private void init() {	
		
		//JLabel����
		idLabel.setBounds(20, 10, 60, 30);  //x,y,��,��
		nameLabel.setBounds(20, 50, 60, 30);
		sexLabel.setBounds(20, 90, 60, 30);
		classLabel.setBounds(20, 130, 60, 30);
		collegeLabel.setBounds(20, 170, 60, 30);
		roomLabel.setBounds(20, 210, 120, 30);
		bedLabel.setBounds(20, 250, 60, 30);
		
		//���JLabel
		mainFrame.add(idLabel);
		mainFrame.add(nameLabel);
		mainFrame.add(sexLabel);
		mainFrame.add(classLabel);
		mainFrame.add(collegeLabel);
		mainFrame.add(bedLabel);
		mainFrame.add(roomLabel);
		
		idText.setBounds(150, 10, 200, 30);
		nameText.setBounds(150, 50, 200, 30);
		sexChooser.setBounds(150, 90, 200, 30);
		classText.setBounds(150, 130, 200, 30);
		collegeChooser.setBounds(150, 170, 200, 30);  //Ժϵѡ��
		roomChooser.setBounds(150, 210, 200, 30);   //���ұ��
		bedChooser.setBounds(150, 250, 200, 30);
		roomBedInfo.setBounds(400, 250, 400, 30);
		
		//�����������
		mainFrame.add(idText);
		mainFrame.add(nameText);
		mainFrame.add(sexChooser);
		mainFrame.add(classText);
		mainFrame.add(collegeChooser);
		mainFrame.add(bedChooser);
		mainFrame.add(roomChooser);
		mainFrame.add(roomBedInfo);
		
		//���ð�ť����
		queryBtn.setBounds(400,10,80,30);
		editBtn.setBounds(80,360,80,30);
		resetBtn.setBounds(200,360,80,30);
		saveyBtn.setBounds(320,360,80,30);
		exitBtn.setBounds(440,360,80,30);
		
		//��Ӱ�ť
		mainFrame.add(queryBtn);
		mainFrame.add(editBtn);
		mainFrame.add(resetBtn);
		mainFrame.add(saveyBtn);
		mainFrame.add(exitBtn);
		
		idText.addKeyListener(new NumberListener());  //��Ӽ�����(ֻ����������)
		classText.addKeyListener(new NumberListener()); //��Ӽ�����(ֻ����������)
		
		queryBtn.addActionListener(new QueryListener()); //��Ӳ�ѯ��ť������
		editBtn.addActionListener(new EditListener()); //��ӱ༭��ť������
		resetBtn.addActionListener(new ResetListener()); //������ð�ť������
		saveyBtn.addActionListener(new SaveListener()); //��ӱ��水ť������
		exitBtn.addActionListener(new ExitListener()); //����˳���ť������
		
		controlUI();  //�ոս�����ʱ��,��Ҫ����һЩUI
		
		mainFrame.setLayout(null);
		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);
		mainFrame.setResizable(false); // ���ڴ�С���ɱ�
		showUI();
	}

	// ��ʾUI����
	private void showUI() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// ������
	/*public static void main(String[] args) {
		//����ȫ������                                               ����:����,����,��С
		CommonOperate.InitGlobalFont(new Font("����", Font.BOLD, 20));
		new StuRevise();
	}*/
	
	/**
	 * ��ʼ��ʱ��Ҫ����һЩUI
	 */
	private void controlUI(){   
		if (!isHideUI) {
			nameText.setVisible(false);
			sexChooser.setVisible(false);
			classText.setVisible(false);
			collegeChooser.setVisible(false);
			bedChooser.setVisible(false);
			roomChooser.setVisible(false);
			editBtn.setVisible(false);
			resetBtn.setVisible(false);
			saveyBtn.setVisible(false);
		} else {
			nameText.setVisible(true);
			sexChooser.setVisible(true);
			classText.setVisible(true);
			collegeChooser.setVisible(true);
			bedChooser.setVisible(true);
			roomChooser.setVisible(true);
			editBtn.setVisible(true);
			resetBtn.setVisible(true);
			saveyBtn.setVisible(true);
		}
		
	}
	
	/**
	 * �����б� ������ ��������ѡ����Ǹ�������ļ�����
	 */
	class RoomListener implements ItemListener {

		public void itemStateChanged(ItemEvent arg0) {
			List<Integer> list = new ArrayList<Integer>(); // �����洢�Ѿ�����˯�˵Ĵ�λ

			String selectSex = sexChooser.getItemAt(sexChooser
					.getSelectedIndex()); // �û�ѡ����Ա�
			String selectRoom = roomChooser.getItemAt(roomChooser
					.getSelectedIndex()); // �û�ѡ�������

			// �ж��û��Ƿ�ѡ������ȷ������
			if (selectRoom != null && selectRoom.equals("��ѡ������")) { // �����Ա������б�
				roomBedInfo.setText("");
				sexChooser.removeAllItems(); // �Ƴ�����ѡ��
				sexChooser.addItem("��ѡ���Ա�");
				sexChooser.addItem("��");
				sexChooser.addItem("Ů");
			} else if (selectRoom != null) {
				list = bedInfo(selectRoom); // ��ȡ�����ҵ������Ѿ���ռ�õĴ�λ
				StringBuffer str = new StringBuffer();
				for (Integer integer : list) {
					str.append(integer + " ");
					bedChooser.removeItem(integer + ""); // ���Ѿ�����˯�˵Ĵ�λ��
															// ��λ�����б����Ƴ�
				}
				bedChooser.addItem(studentInfo[4].toString());
				
				// �����������˯,��ʾ��λ��Ϣ
				if (list.size() != 0) {
					roomBedInfo.setText("������ҵ�" + str.toString() + "��λ�Ѿ�����˯��");
				} else { // �����������˯,����ʾ
					roomBedInfo.setText("�������Ŀǰ����ס,�����ⰲ�Ŵ�λ");
				}

				String roomSex = roomSexInfo(selectRoom); // ������ҵ��Ա�

				// �жϸ�����ס�޵�������������Ů��
				if (roomSex.equals("Ů")) {
					if (selectSex != null) {
						if (selectSex.equals("��") || selectSex.equals("��ѡ���Ա�")) {
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2 + "  ��������Ů������"); // ��ʾ�û�����Ů������
							sexChooser.removeItem("��"); // �����Ա������б��� �� �Ƴ�
							sexChooser.setSelectedItem("Ů"); // �������Ա������б�Ϊ Ů ѡ��
						}
					}

				} else if (roomSex.equals("��")) {
					if (selectSex != null) {
						if (selectSex.equals("Ů") || selectSex.equals("��ѡ���Ա�")) {
							String str2 = roomBedInfo.getText();
							roomBedInfo.setText(str2 + "  ����������������");
							sexChooser.removeItem("Ů");
							sexChooser.setSelectedItem("��");
						}
					}
				} else if (roomSex.equals("")) {
					sexChooser.removeAllItems();
					sexChooser.addItem("��ѡ���Ա�");
					sexChooser.addItem("��");
					sexChooser.addItem("Ů");
					sexChooser.setSelectedItem("��ѡ���Ա�");
				}
			}

		}

	}
	
	/**
	 * ���������� ����:�������� ����ֻ����������
	 * 
	 */
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
	
	/**
	 * ��ѯ��ť������(����ѧ�Ų�ѯѧ����Ϣ)
	 */
	class QueryListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			final String id = idText.getText();   //��ȡ�û������ѧ��
			
			if(!id.equals("")){  //����û������ѧ��Ϊ�ǿ�
				
				//�����߳�,���߳��и���UI
				new Thread(new Runnable(){

					public void run() {
						//��ѯ��ѧ�Ŷ�Ӧ��ѧ������Ϣ
						studentInfo = StudentOperate.getStudentInfo(id);
						if(studentInfo[0] != null){
							isHideUI = true;  //����UI����ɼ�
							controlUI();
							nameText.setText(studentInfo[0].toString());  //����
							nameText.setEditable(false);
							sexChooser.removeAllItems();  //�Ƴ�����ѡ��
							sexChooser.addItem(studentInfo[1].toString()); //�Ա�
							classText.setText(studentInfo[2].toString());  //�༶
							classText.setEditable(false);
							collegeChooser.removeAllItems();
							collegeChooser.addItem(studentInfo[3].toString()); //Ժϵ
							bedChooser.removeAllItems();
							bedChooser.addItem(studentInfo[4].toString());  //��λ
							roomChooser.removeAllItems();
							roomChooser.addItem(studentInfo[5].toString());
							//log.info(studentInfo[0].toString());
						} else if(studentInfo[0] == null && isHideUI == true){
							nameText.setText("");
							sexChooser.removeAllItems();  //�Ƴ�����ѡ��
							classText.setText("");
							collegeChooser.removeAllItems();
							bedChooser.removeAllItems();
							roomChooser.removeAllItems();
						}
						
					}
					
				}).start();
				
				
			} else {
				JOptionPane.showMessageDialog(null, "������ѧ��! ! !");
			}
		}
		
	}
	
	/**
	 * �༭��ť������
	 * 
	 */
	class EditListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			nameText.setEditable(true);
			classText.setEditable(true);
			sexChooser.removeAllItems();
			collegeChooser.removeAllItems();
			roomChooser.removeAllItems();
			bedChooser.removeAllItems();
			
			//���߳� -> ���������б�
			new Thread(new Runnable(){

				public void run() {
					initChooser();  //��ʼ�������б�
				}
				
			}).start();
			roomChooser.addItemListener(new RoomListener()); //����ѡ����Ǹ�������ļ�����
		}
		
	}
	
	/**
	 * ���ð�ť������
	 */
	class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			idText.setText("");
			nameText.setText("");
			sexChooser.removeAllItems();
			classText.setText("");
			collegeChooser.removeAllItems();
			roomChooser.removeAllItems();
			bedChooser.removeAllItems();
			
			//���߳� -> ���������б�
			new Thread(new Runnable(){

				public void run() {
					initChooser();  //��ʼ�������б�
				}
				
			}).start();
		}
		
	}
	
	/**
	 * ���水ť������
	 */
	class SaveListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if(checkFirst()){   // ִ�����ǰ������� �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
				if(!isOldInfo()){  //���֮ǰ����Ϣ���޸ĺ����Ϣһ��,�����豣��
					alterStu(); // �޸�ѧ����Ϣ�����ݿ�
				} else {
					JOptionPane.showMessageDialog(null,"��δ���κ��޸�,���豣��~ ~");
				}
			}
		}
		
	}
	
	/**
	 * �˳�������
	 */
	class ExitListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();   //�ɵ���ǰ����
		}
		
	}
	
	/**
	 *  ��������û������Ƿ���ȷ δ�������ݿ�
	 *  ִ���޸�ǰ������� �ж��û������Ϣ�Ƿ���ȷ(δ�������ݿ�)
	 * @return
	 */
	private boolean checkFirst() {

		// �ж��û��Ƿ�����һ������ ������һ��ûѡ
		if (idText.getText().equals("") || nameText.getText().equals("")
				|| sexChooser.getSelectedItem().toString().equals("��ѡ���Ա�")
				|| classText.getText().equals("")
				|| collegeChooser.getSelectedItem().toString().equals("��ѡ��ѧԺ")
				|| bedChooser.getSelectedItem().toString().equals("��ѡ��λ")
				|| roomChooser.getSelectedItem().toString().equals("��ѡ������")) {
			JOptionPane.showMessageDialog(null, "��,�������ջ�����һ�ѡŶ~~");
			return false;
		}

		// ������������ʽ ^:�еĿ�ͷ [\u4e00-\u9fa5]{2,8}:����2~8���� |:����
		// [a-zA-Z]{2,16}:Ӣ��2~16����ĸ $:�еĽ�β
		String nameRegex = "^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
		boolean isName = nameText.getText().matches(nameRegex); // �ж��û�������Ƿ�����ȷ������
		if (!isName) {
			JOptionPane.showMessageDialog(null, "��,���������������Ŷ~~");
			return false;
		}
		return true;
	}
	
	/**
	 *  ��ʼ��������
	 */
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
		for (int i = 1; i <= StuAdmission.ROOM_COUNT; i++) {
			bedChooser.addItem(i + "");
		}

		// ��ʼ�� ���� �Ǹ�������
		int temp = 1;
		roomChooser.addItem("��ѡ������");
		for (int d = 1; d <= StuAdmission.DORM_FLOOR; d++) {
			for (int i = 1; i <= StuAdmission.FLOOR_ROOM_COUNT; i++) {
				temp = d * 100 + i;
				if (!isRoomFull(temp + "")) { // �ж���������Ƿ�����
					roomChooser.addItem(temp + "");
				}
			}
		}

	}
	
	/**
	 *  �޸�ѧ����Ϣ�����ݿ�
	 */
	private void alterStu() {

		Connection conn = DatabaseConnect.connDatabase();
		PreparedStatement preState = null;
		int changeRows = 0; // ���ص� SQL ���ݲ������� (DML) ��������
		// ����,����(Ĭ��0000),�Ա�,�༶,Ժϵ,��λ,���ұ��(���)  ѧ��
		String sql = "update student_info set name=?,sex=?,classroom=?,college=?,bed=?,roomnum=? where id=? ";
		// ��Ҫִ�е�sql���
		try {
			preState = conn.prepareStatement(sql);
			
			preState.setString(1, nameText.getText());
			preState.setString(2,
					sexChooser.getItemAt(sexChooser.getSelectedIndex()));
			preState.setString(3, classText.getText());
			preState.setString(4,
					collegeChooser.getItemAt(collegeChooser.getSelectedIndex()));
			preState.setString(5,
					bedChooser.getItemAt(bedChooser.getSelectedIndex()));
			preState.setString(6,
					roomChooser.getItemAt(roomChooser.getSelectedIndex()));
			preState.setString(7, idText.getText());

			changeRows = preState.executeUpdate();
			if (changeRows > 0) { // ������������>0
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�! ! !");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�޸�ʧ��! ! !");
		} finally {
			DatabaseConnect.closeStatement(preState);
			DatabaseConnect.closeConnection(conn);
		}
	}

	/**
	 * �жϸ������Ƿ��Ѿ�����
	 * @param roomNumber ���ұ��
	 * @return �ж���������Ƿ��Ѿ�ס��
	 */
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
				if (num == StuAdmission.ROOM_COUNT) {
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

	/**
	 * ���ظ����ҵ������Ѿ�ռ�˵Ĵ�λ
	 * @param roomNumber ���ұ��
	 * @return ������������Ѿ�����˯�Ĵ�λ
	 */
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

	/**
	 * ����������ҵ��Ա�  ����:��,Ů,""
	 * @param roomNumber ���ұ��
	 * @return ����������ҵ��Ա�  ����:��,Ů,""
	 */
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

	/**
	 * �ж��û��༭֮�����Ϣ�Ƿ���ԭ������Ϣ��ͬ
	 */
	private boolean isOldInfo(){
		//String id = idText.getText();
		String name = nameText.getText();
		String sex = sexChooser.getSelectedItem().toString();
		String classroom = classText.getText();
		String college = collegeChooser.getSelectedItem().toString();
		String bed = bedChooser.getSelectedItem().toString();
		String roomnum = roomChooser.getSelectedItem().toString();
		if(studentInfo[0].toString().equals(name) && studentInfo[1].toString().equals(sex) && 
				studentInfo[2].toString().equals(classroom) && studentInfo[3].toString().equals(college) &&
				studentInfo[4].toString().equals(bed) && studentInfo[5].toString().equals(roomnum)){
			return true;   //���ԭ������Ϣ�����ڵ���Ϣһ��,�����豣��
		}
		return false;
	}
	
}
