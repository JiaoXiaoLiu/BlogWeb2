package com.jxl.entity;

/**
 * title:Link.java
 * description:��������ʵ��
 * time:2019��1��15�� ����9:25:58
 * author:jxl
 */
public class Link {

	private Integer id; // ���
	private String linkName; // ��������
	private String linkUrl; // ���ӵ�ַ
	private Integer orderNo; // ������� ��С��������
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
