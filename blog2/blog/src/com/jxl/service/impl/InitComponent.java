package com.jxl.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.jxl.entity.Blog;
import com.jxl.entity.BlogType;
import com.jxl.entity.Blogger;
import com.jxl.entity.Link;
import com.jxl.service.BlogService;
import com.jxl.service.BlogTypeService;
import com.jxl.service.BloggerService;
import com.jxl.service.LinkService;

/**
 * title:InitComponent.java
 * description:��ʼ����� �Ѳ�����Ϣ ���ݲ�����������Ϣ �������ڹ鵵������Ϣ 
 * 		                   ��ŵ�application�У������ṩҳ����������
 * time:2019��1��16�� ����10:36:50
 * author:jxl
 */
@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	//��ʵ�������spring��IOC����-spring������Ӧ�ó���
	private static ApplicationContext applicationContext;
	
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//application������Ӧ�õ�������Ӧ�ó���,�������˶����а�һЩ������Ҫ���ʵĶ���
		ServletContext application=servletContextEvent.getServletContext();
		
		//Spring IOC�����л�ȡ����bean
		BloggerService bloggerService=(BloggerService) applicationContext.getBean("bloggerService");
		Blogger blogger=bloggerService.find(); // ��ѯ������Ϣ
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		BlogTypeService blogTypeService=(BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList=blogTypeService.countList(); // ��ѯ��������Լ����͵�����
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		BlogService blogService=(BlogService) applicationContext.getBean("blogService");
		List<Blog> blogCountList=blogService.countList(); // �������ڷ����ѯ����
		application.setAttribute("blogCountList", blogCountList);
		
		LinkService linkService=(LinkService) applicationContext.getBean("linkService");
		List<Link> linkList=linkService.list(null); // ��ѯ���е�����������Ϣ
		application.setAttribute("linkList", linkList);
		
		String ctx=application.getRealPath("/");
		System.out.println("��·��: "+ctx);
		application.setAttribute("ctx", ctx);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
