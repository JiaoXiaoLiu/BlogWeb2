package com.jxl.dao;

import java.util.List;
import java.util.Map;

import com.jxl.entity.Link;

/**
 * title:LinkDao.java
 * description:��������Dao�ӿ�
 * time:2019��1��16�� ����10:31:28
 * author:jxl
 */
public interface LinkDao {

	/**
	 * �����������
	 * @param link
	 * @return
	 */
	public int add(Link link);
	
	/**
	 * �޸���������
	 * @param link
	 * @return
	 */
	public int update(Link link);
	
	/**
	 * ��������������Ϣ
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String,Object> map);	
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ɾ����������
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
