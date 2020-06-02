package com.jxl.entity;

import java.io.Serializable;

/**
 * title:BlogType.java
 * description: ��������ʵ��
 * time:2019��1��15�� ����9:25:32
 * author:jxl
 */
public class BlogType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;  // ���
	private String typeName; // ������������
	private Integer blogCount; // ����
	private Integer orderNo; // ����  ��С����������ʾ
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
}
