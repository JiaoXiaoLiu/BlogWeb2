package com.jxl.dao;

import com.jxl.entity.Blogger;

/**
 * title:BloggerDao.java
 * description:����Dao�ӿ�
 * time:2019��1��16�� ����10:16:44
 * author:jxl
 */
public interface BloggerDao {

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
