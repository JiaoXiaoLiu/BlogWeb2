package com.jxl.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jxl.entity.BlogType;
import com.jxl.entity.PageBean;
import com.jxl.service.BlogService;
import com.jxl.service.BlogTypeService;
import com.jxl.util.ResponseUtil;

/**
 * title:BlogTypeAdminController.java
 * description:����Ա�������Controller��
 * time:2019��1��23�� ����10:22:24
 * author:jxl
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * title:BlogTypeAdminController.java
	 * description:��ҳ��ѯ���������Ϣ
	 * time:2019��1��23�� ����10:22:43
	 * author:jxl
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogType> blogTypeList=blogTypeService.list(map);
		Long total=blogTypeService.getTotal(map);
		
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		
		return null;
	}
	
	/**
	 * title:BlogTypeAdminController.java
	 * description:��ӻ����޸Ĳ��������Ϣ
	 * time:2019��1��23�� ����10:23:38
	 * author:jxl
	 * @param blogType
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(BlogType blogType,HttpServletResponse response)throws Exception{
		//�����ļ�¼����
		int resultTotal=0;
		
		if(blogType.getId()==null){
			resultTotal=blogTypeService.add(blogType);
		}else{
			resultTotal=blogTypeService.update(blogType);
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
	 * title:BlogTypeAdminController.java
	 * description:ɾ�����������Ϣ
	 * time:2019��1��23�� ����10:24:08
	 * author:jxl
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String[] idsStr=ids.split(",");
		JSONObject result=new JSONObject();
		
		for(int i=0;i<idsStr.length;i++){
			if(blogService.getBlogByTypeId(Integer.parseInt(idsStr[i]))>0){
				result.put("exist", "����������в��ͣ�����ɾ����");
			}else{
				blogTypeService.delete(Integer.parseInt(idsStr[i]));				
			}
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
		
		return null;
	}
}
