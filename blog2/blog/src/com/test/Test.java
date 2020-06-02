package com.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {
	
	public static void main(String[] args) {
		testJsoup();
	}
	
	//Jsoup����html
	public static void testJsoup(){
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		/* ������� 
		 * <html>
			 <head>
			  <title>First parse</title>
			 </head>
			 <body>
			  <p>Parsed HTML into a doc.</p>
			 </body>
		   </html>	 
		 */
	}
}






















