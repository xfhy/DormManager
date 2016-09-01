package com.model;

/**
 * 2016年9月1日10:49:11 查寝记录的Model 类
 * 
 * @author XFHY
 * 
 */
public class CheckRoomRecord {
	private String roomNum; // 寝室编号
	private String isFullRoom;// 寝室是否人数到齐
	private String dateInfo; // 日期信息

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
