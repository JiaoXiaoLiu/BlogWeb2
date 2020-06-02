package com.jxl.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jxl.entity.Blog;
import com.jxl.entity.BlogType;
import com.jxl.entity.Blogger;
import com.jxl.entity.Link;
import com.jxl.service.BlogService;
import com.jxl.service.BlogTypeService;
import com.jxl.service.BloggerService;
import com.jxl.service.LinkService;
import com.jxl.util.ResponseUtil;
import com.jxl.util.WebFileUtil;

/**
 * title:SystemAdminController.java
 * description:����ԱϵͳController��
 * time:2019��11��16�� ����10:34:02
 * author:jxl
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;
	
	@Resource
	private LinkService linkService;
	
	/**
	 * title:SystemAdminController.java
	 * description:ˢ��ϵͳ����  - ��ʵ������������һ��InitComponent��������
	 * time:2019��11��16�� ����10:47:49
	 * author:jxl
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletResponse response,HttpServletRequest request)throws Exception{
		//��ȡ������Ӧ�ó���
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		
		//��ѯ������Ϣ
		Blogger blogger=bloggerService.find(); 
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		//��ѯ��������Լ����͵�����
		List<BlogType> blogTypeCountList=blogTypeService.countList(); 
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		//�������ڷ����ѯ����
		List<Blog> blogCountList=blogService.countList(); 
		application.setAttribute("blogCountList", blogCountList);
		
		//��ȡ������������
		List<Link> linkList=linkService.list(null); 
		application.setAttribute("linkList", linkList);
		
		//�����ʱ����
		String temporaryPath=WebFileUtil.getSystemRootPath(request)+"temporary/";
		System.out.println(temporaryPath);
		WebFileUtil.deleteFilePath(temporaryPath);
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		
		return null;
	}
}
