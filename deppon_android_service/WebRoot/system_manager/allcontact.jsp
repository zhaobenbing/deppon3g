<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有联系人</title>
<script type="text/javascript">
	function showAllContact(){
	
	}
</script>
</head>
<body style="font-size:9pt;margin-left:2pt" bgcolor="#020435" onload="window.contact.showAllContact()">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
	<hr color="#000000"/>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="text-align: center;color:#ffffff;">
		<tr style="color:gray"><td width="8%">姓名</td><td width="8%">手机</td><td>详细地址</td><td colspan="2" width="10%">操作</td></tr>
 		<tr><td>张三</td><td>13659855632</td><td>上海市崇明县城桥镇人民路391号</td><td><a href="contact.jsp">M</a></td><td><a href="#">D</a></td></tr>
		<tr><td>李四</td><td>12365984561</td><td>上海市黄浦区复兴东路690-1号
</td><td><a href="contact.jsp">M</a></td><td><a href="#">D</a></td></tr>
		<tr><td>王五</td><td>12365489786</td><td>上海市黄浦区北京东路668号科技京城C区124室</td><td><a href="contact.jsp">M</a></td><td><a href="#">D</a></td></tr>
	</table>
	<hr color="#000000"/>
	<div style="color:#ffffff;">*备注*&nbsp;M:修改&nbsp;&nbsp;D:删除
		&nbsp;&nbsp;<a href="javascript:window.location.reload()" style="text-decoration: none;font-size:10pt;color: #F38120;">刷新</a>
	 </div>
	
</body>
</html>