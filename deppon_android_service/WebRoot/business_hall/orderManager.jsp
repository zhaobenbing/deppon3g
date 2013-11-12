<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	A {
		 TEXT-DECORATION: none
	}
	 
</style>
<script type="text/javascript">
	function show(jsondata){
	        var jsonobjs = eval(jsondata);
	        var table = document.getElementById("personTable");
	        for(var y=0; y<jsonobjs.length; y++){
	        var tr = table.insertRow(table.rows.length);  
	         
	        var td1 = tr.insertCell(0);
	        var td2 = tr.insertCell(1);
	        td2.align = "center";
	        var td3 = tr.insertCell(2);
	     
	        td1.innerHTML = jsonobjs[y].id; 
	        td2.innerHTML = "<a href='javascript:itcast.call(\"5554\")'>"+ jsonobjs[y].name + "</a>"; 
	        td3.innerHTML = jsonobjs[y].phone;
		}
		function alertshow(){
			//document.getElementById("input").innerHTML = "android webView";
			alert("android webView");
		}
	}
</script>
</head>
<body style="font-size:9pt;margin-left:2pt" bgcolor="#020435">
	<center><img alt="德邦物流" src="${pageContext.request.contextPath}/images/logo.gif" style="width:100%;height:25pt;"></center>
	<a href="javascript:order.addOrder()" style="color:blue">新增一个订单</a>&nbsp;
	<a href="javascript:window.location.reload()">刷新</a>
	<table border="0" width="100%" id="personTable" cellspacing="0" cellpadding="0" style="text-align:center;margin-top:2pt;">
		<tr style="color:gray">
			<td>订单号</td><td>下单时间</td><td>货物名称</td><td colspan="2">操作</td>
		</tr>
		<tr>
			<td>1001</td><td>2011-9-23</td><td>书本</td><td><a href="javascript:order.test()">撤消</a></td><td><a href="javascript:order.Detail()">详情</a></td>
		</tr>
		<tr>
			<td>1002</td><td>2011-9-23</td><td>毛笔</td><td><a href="javascript:#">撤消</a></td><td><a href="orderdetail.jsp">详情</a></td>
		</tr>
		<tr>
			<td>1003</td><td>2011-9-23</td><td>笔记本</td><td><a href="javascript:#">撤消</a></td><td><a href="orderdetail.jsp">详情</a></td>
		</tr>
		<tr>
			<td>1004</td><td>2011-9-23</td><td>手机</td><td><a href="http://www.baidu.com">撤消</a></td><td><a href="orderdetail.jsp">详情</a></td>
		</tr>
	</table><br>
	<br>
	<a href="javascript:order.goindex()" style="text-decoration: none;margin-left:70%">首页</a>
    <a href="javascript:order.back()" style="text-decoration: none;">返回</a>
     </body>
</html>
