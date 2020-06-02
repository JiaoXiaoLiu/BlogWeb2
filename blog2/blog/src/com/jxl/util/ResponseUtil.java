package com.jxl.util;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * title:ResponseUtil.java
 * description: �����ݶ���Objectд����Ӧ����������js�첽���ã�
 * time:2019��1��16�� ����10:52:43
 * author:jxl
 */
public class ResponseUtil {

	/**
	 * title:ResponseUtil.java
	 * description: �����ݶ���Objectд����Ӧ��
	 * time:2019��1��16�� ����10:53:23
	 * author:jxl
	 * @param response
	 * @param o
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response,Object o)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
	
	public static void writeExcel(HttpServletResponse response,Workbook wb,String fileName) throws Exception{
		//��Ҫ������Ӧͷ
		response.setHeader("Content-Disposition" ,"attachment;filename="+new String(fileName.getBytes("utf-8"),"iso-8859-1"));
		
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
}
