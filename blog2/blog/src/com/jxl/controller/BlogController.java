package com.jxl.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jxl.entity.Blog;
import com.jxl.lucene.BlogIndex;
import com.jxl.service.BlogService;
import com.jxl.service.CommentService;
import com.jxl.util.StringUtil;

/**
 * title:BlogController.java
 * description:ǰ�˲���Controller��
 * time:2019��11��15�� ����2:40:22
 * author:jxl
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private CommentService commentService;
	
	// ��������
	private BlogIndex blogIndex=new BlogIndex();
	
	
	/**
	 * title:BlogController.java
	 * description:������ϸ��Ϣ
	 * time:2019��11��15�� ����9:51:50
	 * author:jxl
	 * @param id
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable Integer id,HttpServletRequest request)throws Exception{
		ModelAndView modelAndView=new ModelAndView();
		
		Blog blog=blogService.findById(id);
		String keyWords=blog.getKeyWord();
		
		if(StringUtil.isNotEmpty(keyWords)){
			String arr[]=keyWords.split(" ");
			modelAndView.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));			
		}else{
			modelAndView.addObject("keyWords",null);			
		}
		
		modelAndView.addObject("blog", blog);
		
		Integer clickHit=blog.getClickHit();
		clickHit = clickHit==null?0:clickHit+1;
		//���͵��������1
		blog.setClickHit(clickHit); 
		blogService.update(blog);
		
		Map<String,Object> map=new HashMap<String,Object>();
		//��ѯ���ͨ��������
		map.put("blogId", blog.getId());
		map.put("state", 1);
		
		//System.out.println(request.getServletContext().getContextPath());
		
		modelAndView.addObject("commentList", commentService.list(map)); 
		modelAndView.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id),blogService.getNextBlog(id),request.getServletContext().getContextPath()));
		modelAndView.addObject("mainPage", "foreground/blog/view.jsp");
		modelAndView.addObject("pageTitle",blog.getTitle());
		modelAndView.setViewName("mainTemp");
		
		//��ѯ���ͨ��������
		return modelAndView;
	}
	
	/**
	 * title:BlogController.java
	 * description:���ݲ�ѯ��Ϣ��ѯ��ز�����Ϣ
	 * time:2019��11��15�� ����11:11:29
	 * author:jxl
	 * @param q
	 * @param page
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false)String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("mainPage", "foreground/blog/result.jsp");
		
		blogIndex.setRequest(request);
		List<Blog> blogList=blogIndex.searchBlog(q.trim());
		Integer pageNo=Integer.parseInt(page);
		Integer pageSize=10;
		Integer toIndex=blogList.size()>=pageNo*pageSize?pageNo*pageSize:blogList.size();
		
		//list��subList�Ƿ�ҳ��һ��:�����б���ָ���� fromIndex������ ���� toIndex����������֮��Ĳ�����ͼ
		mav.addObject("blogList",blogList.subList((pageNo-1)*pageSize, toIndex));
		mav.addObject("pageCode",this.genUpAndDownPageCode(pageNo, blogList.size(), q,10,request.getServletContext().getContextPath()));
		mav.addObject("q",q);
		mav.addObject("resultTotal",blogList.size());
		mav.addObject("pageTitle","�����ؼ���'"+q+"'���ҳ��_Java����֮����ϵͳ");
		mav.setViewName("mainTemp");
		
		return mav;
	}
	
	/**
	 * title:BlogController.java
	 * description: ��ȡ��һƪ���ͺ���һƪ����
	 * time:2019��11��15�� ����10:14:03
	 * author:jxl
	 * @param lastBlog
	 * @param nextBlog
	 * @param projectContext
	 * @return String
	 */
	private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>��һƪ��û����</p>");
		}else{
			pageCode.append("<p>��һƪ��<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>��һƪ��û����</p>");
		}else{
			pageCode.append("<p>��һƪ��<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 * ��ȡ��һҳ����һҳ���� ��ѯ�����õ�(�򵥵ķ�ҳ)
	 * @param page ��ǰҳ
	 * @param totalNum �ܼ�¼��
	 * @param q ��ѯ�ؼ���
	 * @param pageSize ÿҳ��С
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager' >");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>��һҳ</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>��һҳ</a></li>");				
			}else{
				pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");				
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
}
