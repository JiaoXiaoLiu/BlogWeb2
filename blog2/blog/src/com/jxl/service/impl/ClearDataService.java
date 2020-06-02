package com.jxl.service.impl;

import org.springframework.stereotype.Service;

import com.jxl.util.WebFileUtil;

@Service("clearDataService")
public class ClearDataService{
	
	
	public void clearData()throws Exception{
		
		//System.out.println("ִ���ˡ���������");
		//�����ʱĿ¼�ļ�
		String path = "/temporary/";
		WebFileUtil.deleteFilePath(path);
	}
	
	public static void main(String[] args) {
		String relatePath=System.getProperty("user.dir");
		System.out.println(relatePath);
	}
	
}



















