package com.jxl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jxl.entity.Blog;
import com.jxl.entity.PageBean;
import com.jxl.service.BlogService;
import com.jxl.util.ExcelUtil;
import com.jxl.util.PageUtil;
import com.jxl.util.ResponseUtil;
import com.jxl.util.StringUtil;
import com.jxl.util.WebFileUtil;

/**
 * title:IndexController.java
 * description:ǰ����ҳController
 * time:2019��1��21�� ����10:28:50
 * author:jxl
 */
@Controller
public class IndexController {

	@Resource
	private BlogService blogService;
	
	@RequestMapping("/")
	public String defaultPage(){
		return "redirect:/index";
	}
	
	/**
	 * title:IndexController.java
	 * description:������ҳ
	 * time:2019��1��21�� ����10:30:17
	 * author:jxl
	 * @param page
	 * @param typeId
	 * @param releaseDateStr
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value="page",required=false)String page,
			@RequestParam(value="typeId",required=false)String typeId,
			@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,
			HttpServletRequest request)throws Exception{
		
		ModelAndView mav=new ModelAndView();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Integer pageNo=Integer.parseInt(page);
		
		PageBean pageBean=new PageBean(pageNo,10);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		List<Blog> blogList=blogService.list(map);
		
		for(Blog blog:blogList){
			//���ڴ�Ų������������ͼƬ,�Ӷ���������ͼ-����ǰ��ҳ��չʾ
			List<String> imagesList=blog.getImagesList();
			
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			
			//������չ����jpg��ͼƬ������ҳ����Ҫ��n��ͼƬ,��������3��
			Elements jpgs=doc.select("img[src$=.jpg]"); 
			if (jpgs!=null && jpgs.size()>0) {
				for(int i=0;i<jpgs.size();i++){
					Element jpg=jpgs.get(i);
					imagesList.add(jpg.toString());
					if(i==2){
						break;
					}
				}
			}
			
		}
		mav.addObject("blogList", blogList);
		
		//��ѯ����
		StringBuffer param=new StringBuffer(); 
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId="+typeId+"&");
		}
		if(StringUtil.isNotEmpty(releaseDateStr)){
			param.append("releaseDateStr="+releaseDateStr+"&");
		}
		
		Long totalRecord=blogService.getTotal(map);
		Integer pageSize=10;
		String genPagination=PageUtil.genPagination(request.getContextPath()+"/index.html",totalRecord,pageNo,pageSize,param.toString());
		
		//��ҳ����ҳ��ҳ��title����ת��ҳ���ַ  
		mav.addObject("pageCode",genPagination);
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.addObject("pageTitle","Java��Դ����ϵͳ");
		mav.setViewName("mainTemp");
		
		return mav;
	}
	
	/**
	 * title:IndexController.java
	 * description:ϵͳ���ܽ���ҳ��
	 * time:2019��1��22�� ����8:48:10
	 * author:jxl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download()throws Exception{
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("mainPage", "foreground/system/download.jsp");
		mav.addObject("pageTitle","��վԴ������ҳ��_Java��Դ����ϵͳ");
		mav.setViewName("mainTemp");
		
		return mav;
	}
	
	/**
	 * title:IndexController.java
	 * description:����ϵͳԴ��
	 * time:2019��1��22�� ����9:56:12
	 * author:jxl
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(@RequestParam(value="fileUrl")String fileUrl,
			@RequestParam(value="fileName")String fileName,HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		WebFileUtil.downloadFile(request, response, fileUrl, fileName);
	}
	
	//ֻ��Ҫ����  poi-3.9-20121203.jar
	@RequestMapping("/exportFile")
	public String export(HttpServletResponse response) throws Exception{
        try {  
            Workbook wb=new HSSFWorkbook();  
            //����excel��ͷ��������ҵ����Ҫ���и�д
            String headers[]={"���","����","��������","�������","�ظ�����","�ؼ���"}; 
            //��ȡ��Ҫ�����������б�
            List<Blog> blogList=blogService.list(null);
            //����
            ExcelUtil.fillExcelData(blogList, wb, headers);
            //���ر�
            ResponseUtil.writeExcel(response, wb, "�����б�.xls");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return null;  
		
	}
	
}
















