package com.jxl.service;

import com.jxl.entity.Blogger;

/**
 * title:BloggerService.java
 * description:����Service�ӿ�
 * time:2019��1��16�� ����10:00:20
 * author:jxl
 */
public interface BloggerService {

	/**
	 * ��ѯ������Ϣ
	 * @return
	 */
	public Blogger find();
	
	/**
	 * ͨ���û�����ѯ�û�
	 * @param userName
	 * @return
	 */
	public Blogger getByUserName(String userName);
	
	/**
	 * ���²�����Ϣ
	 * @param blogger
	 * @return
	 */
	public Integer update(Blogger blogger);
}
