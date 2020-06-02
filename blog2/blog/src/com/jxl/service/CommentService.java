package com.jxl.service;

import java.util.List;
import java.util.Map;

import com.jxl.entity.Comment;

/**
 * title:CommentService.java
 * description:����Service�ӿ�
 * time:2019��1��16�� ����10:35:42
 * author:jxl
 */
public interface CommentService {

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
