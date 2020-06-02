package com.jxl.util;
  
import java.util.Date;  
import java.util.List;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  

import com.jxl.entity.Blog;
  
/** 
 * Excel�ļ���������: ����������ݵ���ͨexcel��ģ��excel�ļ�,��Ԫ���ʽ���� 
 */  
public class ExcelUtil {  
  
    /** 
     * ������ݵ���ͨ��excel�ļ��� 
     * @param rs 
     * @param wb 
     * @param headers 
     * @throws Exception 
     */  
    public static void fillExcelData(List<Blog> dataList,Workbook wb,String[] headers)throws Exception{  
        Sheet sheet=wb.createSheet();  
        Row row=sheet.createRow(0);  
          
        //������ͷ��"���","����","��������","�������","�ظ�����","�ؼ���"
        for(int i=0;i<headers.length;i++){  
            row.createCell(i).setCellValue(headers[i]);  
        }  
        
        //���������:���ݵ�λ����Ҫ���趨�ı�ͷһһ��Ӧ   String headers[]={"���","����","��������","�������","�ظ�����","�ؼ���"}; 
        int rowIndex=1;  
        for(Blog blog:dataList){
        	row=sheet.createRow(rowIndex++);  
        	
    		row.createCell(0).setCellValue(blog.getId()+""); //"���"
    		row.createCell(1).setCellValue(manageCell(blog.getTitle())); //"����"
    		row.createCell(2).setCellValue(manageCell(blog.getReleaseDate())); //"��������"
    		row.createCell(3).setCellValue(manageCell(blog.getClickHit())); //"�������"
    		row.createCell(4).setCellValue(manageCell(blog.getReplyHit())); //"�ظ�����"
    		row.createCell(5).setCellValue(manageCell(blog.getKeyWord())); //"�ؼ���"
        }
    }  
    
    //������������
    public static String manageCell(Object obj){ 
    	String res="";
    	if (obj==null) {
			res="";
		}else if (obj instanceof Date) {
			res=DateUtil.formatDate((Date)obj,"yyyy-MM-dd");
		}else{
			res=obj.toString();
		}
    	return res;
    }
      
}  