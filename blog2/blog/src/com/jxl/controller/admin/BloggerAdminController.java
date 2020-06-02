package com.jxl.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jxl.entity.Blogger;
import com.jxl.service.BloggerService;
import com.jxl.util.CryptographyUtil;
import com.jxl.util.DateUtil;
import com.jxl.util.ResponseUtil;
import com.jxl.util.WebFileUtil;

/**
 * title:BloggerAdminController.java
 * description:����Ա����Controller��
 * time:2019��1��22�� ����10:27:50
 * author:jxl
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * title:BloggerAdminController.java
	 * description:�޸Ĳ�����Ϣ - �첽
	 * time:2019��1��22�� ����10:28:11
	 * author:jxl
	 * @param imageFile
	 * @param blogger
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(@RequestParam("imageFile") MultipartFile imageFile,Blogger blogger,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(!imageFile.isEmpty()){
			//���ϴ����ļ�(����ͷ��)���д���:�ļ�����,תΪFile
			String filePath=WebFileUtil.getSystemRootPath(request);
			String imageName=DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			blogger.setImageName(imageName);
		}
		int resultTotal=bloggerService.update(blogger);
		StringBuffer result=new StringBuffer();
		if(resultTotal>0){
			result.append("<script language='javascript'>alert('�޸ĳɹ���');</script>");
		}else{
			result.append("<script language='javascript'>alert('�޸�ʧ�ܣ�');</script>");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * title:BloggerAdminController.java
	 * description:��ѯ������Ϣ - �첽
	 * time:2019��1��22�� ����10:41:45
	 * author:jxl
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/find")
	public String find(HttpServletResponse response)throws Exception{
		Blogger blogger=bloggerService.find();
		JSONObject jsonObject=JSONObject.fromObject(blogger);
		ResponseUtil.write(response, jsonObject);
		
		return null;
	}
	
	/**
	 * title:BloggerAdminController.java
	 * description:�޸Ĳ�������
	 * time:2019��1��22�� ����11:18:42
	 * author:jxl
	 * @param newPassword
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
		Blogger blogger=new Blogger();
		blogger.setPassword(CryptographyUtil.md5(newPassword, "debug"));
		int resultTotal=bloggerService.update(blogger);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		
		return null;
	}
	
	/**
	 * title:BloggerAdminController.java
	 * description:ע��-�˳���¼
	 * time:2019��1��22�� ����11:18:17
	 * author:jxl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String  logout()throws Exception{
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
