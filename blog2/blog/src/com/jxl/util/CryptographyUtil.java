package com.jxl.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * title:CryptographyUtil.java
 * description:���ܹ���(��Ҫ��shiro��md5)
 * time:2019��1��16�� ����10:55:01
 * author:jxl
 */
public class CryptographyUtil {

	/**
	 * title:CryptographyUtil.java
	 * description:Md5����
	 * time:2019��1��16�� ����10:55:20
	 * author:jxl
	 * @param str ����
	 * @param salt ��
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	
	public static void main(String[] args) {
		String password="linsen";
		
		System.out.println("Md5���ܣ�"+CryptographyUtil.md5(password, "debug"));
	}
}
