package com.model;

/**
 * 2016��9��6��18:24:53
 * 
 * ����һ�����ҵ��������  ��װ���� ģ��
 * 
 * @author xfhy
 * 
 *         roomnum varchar(5) not null, --���Һ� 
 *         gob varchar(2) not null, --�����������-������
 *          dateinfo varchar(20),   --������Ϣ(������ ʱ��) 
 *          remarks varchar(50),    --��ע-�е���,����δ��,����δ��..
 * 
 */
public class HealthRoomRecord {
	private String roomNum;
	private String goodOrBad;
	private String dateInfo;
	private String remarks;

	/**
	 * �������Һ�
	 * @return
	 */
	public String getRoomNum() {
		return roomNum;
	}

	/**
	 * �������Һ�
	 * @return
	 */
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	/**
	 * ����  �����������-������
	 * @return
	 */
	public String getGoodOrBad() {
		return goodOrBad;
	}

	/**
	 * ���� �����������-������
	 * @return
	 */
	public void setGoodOrBad(String goodOrBad) {
		this.goodOrBad = goodOrBad;
	}

	/**
	 * ��ȡ ������Ϣ(������ ʱ��) 
	 * @return
	 */
	public String getDateInfo() {
		return dateInfo;
	}

	/**
	 * ���� ������Ϣ(������ ʱ��) 
	 * @param dateInfo
	 */
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}

	/**
	 * ��ȡ��ע
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * ���ñ�ע
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
