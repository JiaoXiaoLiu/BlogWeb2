package com.jxl.util;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebFileOperationUtil {
	
	/**
	 * title:WebFileOperationUtil.java
	 * description:���ٶȱ༭�������ݵ�ͼƬ�ƶ���ָ����λ�ã������µ�λ���滻�����ж��ڵľ�λ��
	 * time:2019��2��7�� ����7:17:35
	 * author:jxl
	 * @param request
	 * @param content
	 * @return
	 */
	public static String copyImageInUeditor(HttpServletRequest request,String content){
		if (StringUtil.isNotEmpty(content)) {
			Document doc=Jsoup.parse(content);
			
			Elements imageList=doc.select("img"); 
			if (imageList!=null && imageList.size()>0) {
				for(int i=0;i<imageList.size();i++){
					Element image=imageList.get(i);
					String oldImage=image.toString();
					System.out.println(oldImage);
					
					String charIndex="/temporary";
					int index=oldImage.indexOf(charIndex);
					if (index>0) {
						String srcImage=oldImage.substring(index);
						String secIndex="\"";
						String realImagePos=srcImage.substring(0,srcImage.indexOf(secIndex));
						
						String folder="ueditor";
						String newImagePos=WebFileUtil.copyFileForUeditor(request, realImagePos, folder);
						content = content.replace(realImagePos, newImagePos);
					}
				}
			}
		}
		
		return content;
	}
	
	/**
	 * title:WebFileOperationUtil.java
	 * description:ɾ���ٶȱ༭�����ݵ�ͼƬ
	 * time:2019��2��7�� ����9:01:23
	 * author:jxl
	 * @param request
	 * @param content
	 * @throws Exception
	 */
	public static void deleteImagesInUeditor(HttpServletRequest request,String content) throws Exception{
		if (StringUtil.isNotEmpty(content)) {
			Document doc=Jsoup.parse(content);
			
			Elements imageList=doc.select("img"); 
			if (imageList!=null && imageList.size()>0) {
				for(int i=0;i<imageList.size();i++){
					Element image=imageList.get(i);
					String oldImage=image.toString();
					
					//ͼƬsrc�ض��� " ��ʼ,�� " ����
					String charIndex="\"";
					int index=oldImage.indexOf(charIndex);
					if (index>0) {
						String srcImage=oldImage.substring(index+1);
						String delImagePos=srcImage.substring(0,srcImage.indexOf(charIndex));
						System.out.println(delImagePos);
						
						WebFileUtil.deleteFilePath(WebFileUtil.getSystemRootPath(request)+delImagePos);
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		String image="img src=\"/temporary/2019-02-06/image/1486394487364095169.jpg\"";
		String str="/temporary/2019-02-06/image/1486394487364095169.jpg"; //51
		
		String charIndex="/temporary";
		int index=image.indexOf(charIndex);
		System.out.println(index);
		String newStr=image.substring(index, str.length()+index);
		System.out.println(newStr);
	}
	
}









