<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>个人资料</title>
		<script src="../js/procity.js" charset="utf-8"></script>
		<script type="text/javascript">
	function check() {
		var name = document.getElementById("name");
		var phone = document.getElementById("phone");
		var address = document.getElementById("address");
		var selepro = document.getElementById("province");
		var prov = selepro.options[selepro.selectedIndex].value;
		var selecity = document.getElementById("city");
		var cityv = selecity.options[selecity.selectedIndex].value;
		var fixphone = document.getElementById("fixphone");
		if (name.value.length == 0) {
			alert("请输入姓名");
			return false;
		}
		if (phone.value.length == 0||phone.value.length!=11) {
			alert("请输入11位数字");
			return false;
		}
		 
		var partten = /^1[3,5]\d{9}$/;
        if(!partten.test(phone.value)){ 
			alert("您的手机号码格式不正确，请重新输入");
          	return false; 
        } 
		if (prov=="province") {
			alert("请选择省份");
			return false;
		}
		if (cityv=="city") {
			alert("请选择城市");
			return false;
		}
		if (address.value.length == 0) {
			alert("请输入地址");
			return false;
		}
		var temp ;
		if(fixphone.value.length==0){
			 temp  = "without";
		}else{
			temp = fixphone.value;
		}
		return name.value+"#"+phone.value+"#"+temp+"#"+prov+"#"+cityv+"#"+address.value;
	}
	
	function initInfo(json){
		var name = document.getElementById("name");
		var phone = document.getElementById("phone");
		var fixphone = document.getElementById("fixphone");
		var arr = json.split("#");
		name.value = arr[0];
		phone.value = arr[1];
		if(arr[2]!=null){
			fixphone.value = arr[2];
		}
	}
</script>
	</head>
	<body style="font-size: 9pt; margin-left: 2pt" bgcolor="#020435" onload="loadprovinces();javascript:window.system.initInfo();">
		<center>
			<img alt="德邦物流"
				src="${pageContext.request.contextPath}/images/logo.gif"
				style="width: 100%; height: 25pt;">
		</center>
		<form method="post" onsubmit="" action="">
			<fieldset style="width: 95%">
				<legend style="color: red">
					基本信息
				</legend>
				<p style="color: #c12cc2">
					<font style="font-size: 8pt;">您填写的基本资料将作为我司托运客户的基本信息,请注意带*是必填项</font>
				</p>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2px" style="color:red">*</font>
					<font size="2px" style="color:#ffffff">姓名:</font>
					<input type="text" id="name" value="" />
				</p>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2px" style="color:red">*</font>
					<font size="2px" style="color:#ffffff">手机:</font>
					<input type="text" id="phone" value="" />
				</p>
				<p>
					<font size="2px" style="color:#ffffff">固定电话:</font>
					<input type="text" id="fixphone" value="" />
				</p>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2px" style="color:red">*</font>
					<font size="2px" style="color:#ffffff">地址:</font>
					<select id="province" style="width:90px" onchange="loadCitys(this);">
						<option value="province" selected="selected">
							请选择省份
						</option>
					</select>
					&nbsp;
					<select id="city">
						<option value="city" selected="selected">
							请选择城市
						</option>
					</select>
					<input type="text" id="address" value="" />
					<font color="red" style="font-size:10px;">请写明详细地址方便您的发货</font>
				</p>
				&nbsp;&nbsp;&nbsp;
				<input type="button" id="submit" value="保存" onclick="javascript:window.system.resultInfo(check());" />
				&nbsp;&nbsp;&nbsp;
				<a href="javascript:window.location.reload()" style="text-decoration:none;font-size: 13pt;color: #F38120;margin-left: 10%;">刷新</a>
			</fieldset>
		</form>
	</body>
</html>