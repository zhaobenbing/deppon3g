<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>联系人管理</title> 
	<script src="../js/procity.js" charset="utf-8"></script>
<script type="text/javascript">
	function showAddress(){
		var obj = document.getElementById("address");
		alert("你选择的地址为："+obj.value);
	}
</script>
</head>
<body style="font-size:9pt;margin-left:2pt" bgcolor="#020435" onload="loadprovinces();">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
	<br>
	<br><br>
	<form action="" method="post">
		<fieldset>
			<legend style="color:red">添加联系人</legend>
			<P style="font-weight: 800px;color:#ffffff;">带*为必填项&nbsp;&nbsp;
			<a href="javascript:window.location.reload()" style="text-decoration:none;font-size: 13pt;color: #F38120;margin-left: 10%;">刷新</a></P>
			<p style="color:#ffffff;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*姓名:<input type="text" id="name" value=""/></p>
			<p  style="color:#ffffff;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*手机:<input type="text" id="mobile" value=""/></p>
			<p style="color:#ffffff;">固定电话:<input type="text" id="phone" value=""/></p>
			<P  style="color:#ffffff;">*详细地址:
			<select id="province" style="width:90px" onchange="loadCitys(this);">
				<option value="province" selected="selected">请选择省份</option>
			</select>&nbsp;
			<select id="city">
				<option value="city" selected="selected">请选择城市</option>
			</select>
			<input type="text" id="address" value="请输入详细地址"  onfocus="if(value=='请输入详细地址'){value=''}" onblur="if(value==''){value='请输入详细地址'}"/>&nbsp;<font color="red" style="font-size: 7pt;"></font>
			</P>
			<input type="button" id="addcontact" value="添加联系人" onclick="window.contact.showAddress()"/>
			<input type="button" id="selectcontact" value="搜索联系人" onclick="window.contact.searchContact()"/>
			<input type="button" id="allcontact" value="所有联系人" onclick="window.contact.allContact()"/> 
		</fieldset>
	</form>
</body>
</html>