package com.model;

/**
 * 2016��9��1��10:49:11 ���޼�¼��Model ��
 * 
 * @author XFHY
 * 
 */
public class CheckRoomRecord {
	private String roomNum; // ���ұ��
	private String isFullRoom;// �����Ƿ���������
	private String dateInfo; // ������Ϣ

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String isFullRoom() {
		return isFullRoom;
	}

	public void setFullRoom(String isFullRoom) {
		this.isFullRoom = isFullRoom;
	}

	public String getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}

}
