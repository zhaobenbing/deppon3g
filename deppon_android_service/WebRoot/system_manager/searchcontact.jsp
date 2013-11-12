<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索联系人</title>
<script type="text/javascript">
	function startSearch(){
		var obj = document.getElementById("contactInfo");
		var name = document.getElementById("name").value;
		obj.innerHTML = name;
	}
</script>
</head>
<body style="font-size:9pt;margin-left:2pt" bgcolor="#020435">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
	<form action="">
		<fieldset style="width:95%;height:60%">
			<legend style="color:red">搜索联系人</legend>
			<p style="color:#ffffff">所在地区:&nbsp;&nbsp;
			<select id="province">
				<option value="checkedprovince">请选择省份...</option>
			</select>&nbsp;&nbsp;&nbsp;
			<select id="city">
				<option value="checkedcity">请选择城市...</option>
			</select></p>
			<p style="color:#ffffff">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名:&nbsp;<input type="text" id="name"/></p>
			<p>&nbsp;&nbsp;&nbsp;<input type="button" value="开始搜索" onclick="window.contact.startSearch()"/>
			&nbsp;&nbsp;&nbsp;<a href="javascript:window.location.reload()" style="text-decoration:none;font-size:12pt;color: #F38120;">刷新</a>
			</p>
			
			<div id="contactInfo"></div>
		</fieldset>
	</form>	
</body>
</html>