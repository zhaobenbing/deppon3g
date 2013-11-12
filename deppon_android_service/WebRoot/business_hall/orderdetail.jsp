<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>order detail</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
 <body style="font-size:9pt;margin-left:2pt" bgcolor="#020435">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
  	<center>
  		<p style="color:blue;font-size:12pt">订单详情</p>
     <table border="0" cellpadding="0" cellspacing="0" width="99%" style="text-align:center;margin-top:10pt;">
     	<tr style="margin-top:8pt;"><td width="30%">托运人</td><td>张三</td></tr>
     	<tr style="margin-top:8pt;"><td width="30%">联系电话</td><td>13658521150</td></tr>
     	<tr style="margin-top:8pt;"><td width="30%">货物名称</td><td>笔记本</td></tr>
     	<tr style="margin-top:8pt;"><td width="30%">网点信息</td><td>广州市花都区狮岭镇横坑浭荣发皮具制品厂B座西面</td></tr>
     </table>
    </center>
    <br>
    <a href="javascript:order.goindex()" style="text-decoration: none;margin-left:70%">首页</a>
    <a href="javascript:order.goback()" style="text-decoration: none;">返回</a>
  </body>
</html>
