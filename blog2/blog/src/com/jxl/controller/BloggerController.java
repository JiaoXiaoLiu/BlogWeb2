package com.jxl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jxl.entity.Blogger;
import com.jxl.service.BloggerService;
import com.jxl.util.CryptographyUtil;

/**
 * title:BloggerController.java
 * description:����Controller��
 * time:2019��11��16�� ����10:01:10
 * author:jxl
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * title:BloggerController.java
	 * description:�û���¼
	 * time:2019��11��16�� ����08:26:29
	 * author:jxl
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger,HttpServletRequest request){
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "debug"));
		try{
			//��¼��֤
			subject.login(token); 
			return "redirect:/admin/main.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "�û������������");
			return "login";
		}
	}
	
	/**
	 * title:BloggerController.java
	 * description:���Ҳ�����Ϣ
	 * time:2019��11��16�� ����10:12:45
	 * author:jxl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("blogger",bloggerService.find());
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.addObject("pageTitle","���ڲ���_Java��Դ����ϵͳ");
		mav.setViewName("mainTemp");
		
		return mav;
	}
}
