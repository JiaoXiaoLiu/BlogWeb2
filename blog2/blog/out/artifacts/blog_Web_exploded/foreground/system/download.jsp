<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="data_list">
	<div class="data_list_title">
		<img src="${ctx}/static/images/download_icon.png"/>
		本站源码下载
	</div>
	<div>
	<p>
    <br/>
</p>
<p>
    本站源码使用J2EE开发；
</p>
<p>
    <br/>
</p>
<p>
    使用Spring4+Springmvc+Mybatis3架构，
</p>
<p>
    采用Mysql数据库；
</p>
<p>
    使用Maven3管理项目，使用Shiro作为项目安全框架，使用Lucene作为全文检索，支持restful风格；
</p>
<p>
 (不过后期我舍弃了maven,直接丢进Jar进行管理)
</p>
<p>
    前台网页使用主流的Bootstrap3 UI框架；后台管理使用主流易用的EasyUI轻量级框架；
</p>
<p>
    数据库连接池使用的是阿里巴巴的Druid；
</p>
<p>
    在线编辑器使用了百度的UEditor，支持单图，多图上传，支持截图上传，支持代码高亮特性；
</p>
<p>
    其他的技术包括:<br/>
  jsoup解析html;<br/>
</p>
<p>
    <br/>
</p>
<p>
    V1.0 2019年2月12号发布
</p>
<p>
    <br/>
</p>
<p>
   完整源码和数据库脚本下载地址1：<a href="${ctx}/downloadFile.do?fileUrl=ssm-blog.rar&fileName=blogSystem(ssm)" target="_blank" title="下载源码">本系统源码下载</a>
</p>
<p>
   完整源码和数据库脚本下载地址2：<a href="http://pan.baidu.com/s/1kVPqBRL" target="_blank" title="下载源码">本系统源码下载(密码：ej6f)</a>
</p>
<p>
	修复一些bug;<br/>
	添加评论审核功能;<br/>
	添加友情链接功能;<br/>
	日志类别排序显示功能;<br/>
	博客显示列表加博客缩略图功能;<br/>
	博客加关键字功能;<br/>
	博客加分享链接功能;<br/>
</p>

<p>
    <br/>
</p>
	</div>
</div>