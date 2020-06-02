package com.jxl.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jxl.entity.Blog;
import com.jxl.entity.PageBean;
import com.jxl.lucene.BlogIndex;
import com.jxl.service.BlogService;
import com.jxl.util.ResponseUtil;
import com.jxl.util.StringUtil;
import com.jxl.util.WebFileOperationUtil;

/**
 * title:BlogAdminController.java
 * description:����Ա����Controller��
 * time:2019��1��23�� ����10:15:18
 * author:jxl
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	
	// ��������
	private BlogIndex blogIndex=new BlogIndex();
	
	/**
	 * title:BlogAdminController.java
	 * description:��ӻ����޸Ĳ�����Ϣ
	 * time:2019��1��23�� ����10:15:36
	 * author:jxl
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog,HttpServletResponse response,HttpServletRequest request)throws Exception{
		int resultTotal=0;
		
		String newContent=WebFileOperationUtil.copyImageInUeditor(request, blog.getContent());
		blog.setContent(newContent);
		
		blogIndex.setRequest(request);
		if(blog.getId()==null){
			//��Ӳ�������
			resultTotal=blogService.add(blog);
			blogIndex.addIndex(blog); 
		}else{
			//���²�������
			resultTotal=blogService.update(blog);
			blogIndex.updateIndex(blog); 
		}
		
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
	 * title:BlogAdminController.java
	 * description:��ҳ��ѯ������Ϣ
	 * time:2019��1��23�� ����10:17:41
	 * author:jxl
	 * @param page
	 * @param rows
	 * @param s_blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,Blog s_blog,
			HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		
		List<Blog> blogList=blogService.list(map);
		Long total=blogService.getTotal(map);
		
		JSONObject result=new JSONObject();
		
		//����ר�ŵ������ֶ�ֵ
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * title:BlogAdminController.java
	 * description:ɾ��������Ϣ
	 * time:2019��1��23�� ����10:20:11
	 * author:jxl
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response,
			HttpServletRequest request)throws Exception{
		String[] idsStr=ids.split(",");
		
		blogIndex.setRequest(request);
		for(int i=0;i<idsStr.length;i++){
			Integer currId=Integer.parseInt(idsStr[i]);
			Blog currBlog=blogService.findById(currId);
			
			//ɾ���������������ͼƬ
			WebFileOperationUtil.deleteImagesInUeditor(request, currBlog.getContent());
			
			blogService.delete(currId);
			//ɾ����Ӧ���͵�����
			blogIndex.deleteIndex(idsStr[i]); 
			
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		
		return null;
	}
	
	/**
	 * title:BlogAdminController.java
	 * description:ͨ��ID����ʵ��
	 * time:2019��1��23�� ����10:20:45
	 * author:jxl
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		Blog blog=blogService.findById(Integer.parseInt(id));
		JSONObject jsonObject=JSONObject.fromObject(blog);
		ResponseUtil.write(response, jsonObject);
		
		return null;
	}
	
}
