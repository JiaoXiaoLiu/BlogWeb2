package com.jxl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

/**
 * title:WebFileUtil.java 
 * description:�ļ�����(���������ϵ��ļ�)������ 
 * time:2019��1��22��
 * ����9:41:57 
 * author:jxl
 */
public class WebFileUtil {
	
	/**
	 * title:WebFileUtil.java
	 * description:��ȡweb����ʱ��Ŀ�ĸ�·��
	 * time:2019��1��22�� ����10:00:08
	 * author:jxl
	 * @param request
	 * @return
	 */
	public static String getSystemRootPath(HttpServletRequest request){
		String rootPath=request.getServletContext().getRealPath("/");
		//request.getContextPath();
		return rootPath;
	}

	/**
	 * title:WebFileUtil.java
	 * description:�����ļ�(���������ļ�)
	 * time:2019��1��22�� ����9:59:24
	 * author:jxl
	 * @param request
	 * @param response
	 * @param url �ļ�·��
	 * @param fileName �ļ���
	 */
	public static void downloadFile(HttpServletRequest request,HttpServletResponse response, 
			String url,String fileName) {

		String root = getSystemRootPath(request);

		//�ø�����fileName��url�ĺ�׺��ƴ��һ�µ��ַ�����Ϊ�µ��ļ���
		fileName = fileName + url.substring(url.lastIndexOf("."));
		
		OutputStream os = null;
		InputStream fis = null;
		try {
			//������� �ļ�������
			fileName = new String(fileName.getBytes(), "iso8859-1");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + "\"");

			os=response.getOutputStream();
			if (url.startsWith("http:")) {
				URL tmpURL = new URL(url);
				URLConnection conn = tmpURL.openConnection();
				fis = conn.getInputStream();
			} else {
				fis = new FileInputStream(new File(root + url));
			}

			byte[] b = new byte[1024];
			int i = 0;

			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}

			os.flush();
		} catch (Exception e) {
			System.out.println("�����ļ������쳣: "+e.getMessage());
		} finally{
			try {
				os.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * title:WebFileUtil.java
	 * description:�����ļ���
	 * time:2019��2��6�� ����10:50:17
	 * author:jxl
	 * @param dFile
	 */
    public static void createFold(File file) {
		if (!file.exists()) file.mkdirs();
	}
    
    /**
     * title:WebFileUtil.java
     * description:���ưٶȱ༭���е�ͼƬ��ָ�����ļ����£�������ͼƬ�洢��ʵ��·��
     * time:2019��2��6�� ����10:58:58
     * author:jxl
     * @param request
     * @param srcPath
     * @param floder
     * @return
     */
    public static String copyFileForUeditor(HttpServletRequest request,String srcPath, String folder){
    	
    	String newFolder="";
    	String fileName="";
    	
		try {
			File oldFile=new File(getSystemRootPath(request)+srcPath);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			newFolder="/files/"+folder+"/"+sdf.format(new Date())+"/";
			String newFilePath=getSystemRootPath(request)+newFolder;
			createFold(new File(newFilePath));
			
			fileName=oldFile.getName();
			File newFile=new File(newFilePath+fileName);
			FileCopyUtils.copy(oldFile, newFile);
			//FileUtils.copyFile(oldFile, saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newFolder+fileName;
    }
    
    
    /**
     * title:WebFileUtil.java
     * description:ɾ������·���µ��ļ�(�����������ļ�;�ļ���)
     * time:2019��2��6�� ����11:02:12
     * author:jxl
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteFilePath(String path) throws Exception {  
		
		try {
			File file = new File(path);
			
			// ���ҽ����˳���·������ʾ���ļ������� ��һ��Ŀ¼ʱ������ true
			if(!file.isDirectory()){
				file.delete();
			}else if(file.isDirectory()){
				String[] filelist = file.list();
				
				for(int i = 0; i < filelist.length; i++) {
					File delfile = new File(path + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deleteFilePath(path + "/" + filelist[i]);
					}
				}
				
				file.delete();
			}
		} catch (Exception e) {
		}
		
		return true;  
	}
    
}
