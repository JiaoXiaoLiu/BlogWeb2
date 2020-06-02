package com.jxl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * title:DateUtil.java
 * description:���ڴ�������
 * time:2019��1��16�� ����10:38:01
 * author:jxl
 */
public class DateUtil {

	/**
	 * title:DateUtil.java
	 * description:���ڶ���ת�ַ���
	 * time:2019��1��16�� ����10:40:49
	 * author:jxl
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String formatDate(Date date,String format){
		String result=null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			if(date!=null){
				result=sdf.format(date);
			}
		} catch (Exception e) {
			System.out.println("����ת�ַ��������쳣: "+e.getMessage());
		}
		return result;
	}
	
	/**
	 * title:DateUtil.java
	 * description:�ַ���ת���ڶ���
	 * time:2019��1��16�� ����10:43:14
	 * author:jxl
	 * @param str
	 * @param format
	 * @throws Exception
	 */
	public static Date formatString(String str,String format){
		Date date=null;
		try {
			if(StringUtil.isNotEmpty(str)){
				SimpleDateFormat sdf=new SimpleDateFormat(format);
				date=sdf.parse(str);
			}
		} catch (Exception e) {
			System.out.println("�ַ���ת���ڳ���: "+e.getMessage());
		}
		return date;
	}
	
	/**
	 * title:DateUtil.java
	 * description: ��ȡ��ǰ������ʱ������ɵĴ�������������ͼƬ���ļ�
	 * time:2019��1��16�� ����10:48:48
	 * author:jxl
	 * @return String
	 * @throws Exception
	 */
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
	
}
