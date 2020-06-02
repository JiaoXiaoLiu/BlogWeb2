package com.jxl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * title:StringUtil.java
 * description:�ַ����򵥴�������
 * time:2019��1��16�� ����10:44:02
 * author:jxl
 */
public class StringUtil {

	/**
	 * title:StringUtil.java
	 * description:�ж��Ƿ��ǿ�
	 * time:2019��1��16�� ����10:44:21
	 * author:jxl
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * title:StringUtil.java
	 * description:�ж��Ƿ��ǿ�
	 * time:2019��1��16�� ����10:44:44
	 * author:jxl
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * title:StringUtil.java
	 * description: ��ʽ��ģ����ѯ
	 * time:2019��1��16�� ����10:45:09
	 * author:jxl
	 * @param str
	 * @return String
	 */
	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
	
	/**
	 * title:StringUtil.java
	 * description:���˵�������Ŀո�
	 * time:2019��1��16�� ����10:45:34
	 * author:jxl
	 * @param list
	 * @return List<String>
	 */
	public static List<String> filterWhite(List<String> list){
		List<String> resultList=new ArrayList<String>();
		for(String str:list){
			if(isNotEmpty(str)){
				resultList.add(str);
			}
		}
		return resultList;
	}

}
