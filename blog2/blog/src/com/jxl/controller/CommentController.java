package com.jxl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jxl.entity.Blog;
import com.jxl.entity.Comment;
import com.jxl.service.BlogService;
import com.jxl.service.CommentService;
import com.jxl.util.ResponseUtil;

/**
 * ????Controller??
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private BlogService blogService;
	

	@RequestMapping("/save")
	public String save(Comment comment,@RequestParam("imageCode") String imageCode,
			HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		//???????????????
		String sRand=(String) session.getAttribute("sRand");
		
		JSONObject result=new JSONObject();
		
		//????????????
		int resultTotal=0; 
		if(!imageCode.equals(sRand)){
			result.put("success", false);
			result.put("errorInfo", "???????§Õ????");
		}else{
			//??????IP
			String userIp=request.getRemoteAddr(); 
			comment.setUserIp(userIp);
			if(comment.getId()==null){
				resultTotal=commentService.add(comment);
				//?¨°????????????1
				Blog blog=blogService.findById(comment.getBlog().getId());
				blog.setReplyHit(blog.getReplyHit()+1);
				blogService.update(blog);
			}else{
				
			}
			if(resultTotal>0){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
		return null;
	}

}
