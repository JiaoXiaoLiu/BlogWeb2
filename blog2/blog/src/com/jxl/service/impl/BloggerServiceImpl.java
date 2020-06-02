package com.jxl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxl.dao.BloggerDao;
import com.jxl.entity.Blogger;
import com.jxl.service.BloggerService;

/**
 * title:BloggerServiceImpl.java
 * description:����Serviceʵ����
 * time:2019��1��16�� ����10:36:10
 * author:jxl
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

	@Resource
	private BloggerDao bloggerDao;

	public Blogger find() {
		return bloggerDao.find();
	}

	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}
	
	
}
