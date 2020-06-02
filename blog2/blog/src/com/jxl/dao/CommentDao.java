package com.jxl.dao;

import java.util.List;
import java.util.Map;

import com.jxl.entity.Comment;

/**
 * title:CommentDao.java
 * description:����Dao�ӿ�
 * time:2019��1��16�� ����10:28:17
 * author:jxl
 */
public interface CommentDao {

	/**
	 * �������
	 * @param comment
	 * @return
	 */
	public int add(Comment comment);
	
	/**
	 * �޸�����
	 * @param comment
	 * @return
	 */
	public int update(Comment comment);
	
	/**
	 * �����û�������Ϣ
	 * @param map
	 * @return
	 */
	public List<Comment> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ɾ��������Ϣ
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	
}
