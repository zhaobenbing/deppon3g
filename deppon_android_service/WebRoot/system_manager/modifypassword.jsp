<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<script type="text/javascript">
	function check(){
		var obj = document.getElementById("oldPwd");
		var pwd = document.getElementById("newPwd");
		var repwd = document.getElementById("confirmPwd");
		if(obj.value.length==0){
			alert("请输入旧密码");
			return false;
		}
		if(pwd.value.length==0){
			alert("请输入新密码");
			return false;
		}
		if(pwd.value!=repwd.value){
			alert("两次输入的密码不一致");
			return false;
		}
		return obj.value+","+pwd.value+","+repwd.value;
	}
</script>

</head>
<body style="font-size:9pt;margin-left:2pt" bgcolor="#020435">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
	 <form name="modify" method="post">
	 	<fieldset style="width:95%;height:60%">
			<legend style="color:red">修改密码</legend>
			<p>&nbsp;
			<font size="2px" style="color:#ffffff">原密码:</font>
			&nbsp;<input type="text" id="oldPwd"/></p>
			<p>&nbsp;
			<font size="2px" style="color:#ffffff">新密码:</font>
			&nbsp;<input type="text" id="newPwd"/></p>
			<p>&nbsp;
			<font size="2px" style="color:#ffffff">确认码:</font>
			&nbsp;<input type="text" id="confirmPwd"/></p>
			<p>&nbsp;<input type="button" value="修改"   onclick="javascript:window.modifypassword.showInfo(check())"/>&nbsp;&nbsp;
			<a href="javascript:window.location.reload()" style="text-decoration:none;font-size: 13pt;color: #F38120">刷新</a></p>
	 	</fieldset>
	 </form>
 </body>
</html>