package com.model;

/**
 * 2016年9月6日18:24:53
 * 
 * 这是一间寝室的卫生情况  封装的类 模型
 * 
 * @author xfhy
 * 
 *         roomnum varchar(5) not null, --寝室号 
 *         gob varchar(2) not null, --寝室卫生情况-优良差
 *          dateinfo varchar(20),   --日期信息(年月日 时间) 
 *          remarks varchar(50),    --备注-有吊床,垃圾未倒,被套未叠..
 * 
 */
public class HealthRoomRecord {
	private String roomNum;
	private String goodOrBad;
	private String dateInfo;
	private String remarks;

	/**
	 * 返回寝室号
	 * @return
	 */
	public String getRoomNum() {
		return roomNum;
	}

	/**
	 * 设置寝室号
	 * @return
	 */
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	/**
	 * 返回  寝室卫生情况-优良差
	 * @return
	 */
	public String getGoodOrBad() {
		return goodOrBad;
	}

	/**
	 * 设置 寝室卫生情况-优良差
	 * @return
	 */
	public void setGoodOrBad(String goodOrBad) {
		this.goodOrBad = goodOrBad;
	}

	/**
	 * 获取 日期信息(年月日 时间) 
	 * @return
	 */
	public String getDateInfo() {
		return dateInfo;
	}

	/**
	 * 设置 日期信息(年月日 时间) 
	 * @param dateInfo
	 */
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}

	/**
	 * 获取备注
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 设置备注
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
