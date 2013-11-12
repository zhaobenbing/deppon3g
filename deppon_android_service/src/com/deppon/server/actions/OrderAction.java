package com.deppon.server.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Order;
import com.deppon.common.beans.PriceBean;
import com.deppon.common.beans.TransPropertyEnum;
import com.deppon.common.util.StatusCode;
import com.deppon.server.services.ServerContext;
import com.deppon.server.services.impl.ServiceImpl;
import com.deppon.server.services.intfce.IService;

public class OrderAction extends BaseAction{
	private static final long serialVersionUID = -5570362277228360195L;
 	private HttpServletRequest request;
	private HttpServletResponse response;
	 private IService service = new ServiceImpl();
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		super.setServletRequest(arg0);
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		super.setServletResponse(arg0);
	}
	private void setCharacter(){
  		try {
 			this.response.setCharacterEncoding("utf-8");
 	 		this.response.setContentType("text/html;charset=utf-8");
 			this.request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
		}
	}
 	//营业网点请求，返回营业网点
 	public void returnDepts(){
 		setCharacter();
 		String cityId = this.request.getParameter("cityId");
 		System.out.println("===parameter cityId="+cityId);
 		try {
			Map<String,Dept> depts = ServerContext.findDeptByCityId(cityId);
			if(depts != null){
				JSONObject json = new JSONObject();
				json.putAll(depts);
	 			System.out.println("=======depts size=="+json.toString().length());
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+(json==null?StatusCode.STATUS_FAIL:json));
			}
 		} catch (IOException e) {
 			e.printStackTrace();
		}
 	}
 	//营业网点信息
 	public void returnDeptInfo(){
 		setCharacter();
 		String deptId = this.request.getParameter("deptId");
 		try {
 			String deptInfo = service.getDeptService().findStationInfo(deptId);
 			System.out.println("===parameter deptId===="+deptId);
 			System.out.println("===deptInfo "+deptInfo);
 			if(deptInfo!=null||"".equals(deptInfo)){
 				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+deptInfo);
  			}else{
				this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+StatusCode.EXECPTION);
  			}
		} catch (IOException e) {
 			e.printStackTrace();
		}
 	}
 	//创建定单
 	public void createOrder(){
 		setCharacter();
 		String consignor = this.request.getParameter("consignor").trim();
 		String phone = this.request.getParameter("phone").trim();
 		String goodsName = this.request.getParameter("goodsName").trim();
 		String orderDeptId = this.request.getParameter("OrderDeptId").trim();
 		String orderUserId = this.request.getParameter("OrderUserId").trim();
 		try {
 			Order order = new Order();
 	 		order.setDeptId(orderDeptId);
 	 		order.setGoodsName(goodsName);
 	 		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	 		String date = df.format(new Date());
 			order.setOrderDate(df.parse(date));
 			order.setOrderNumber(System.currentTimeMillis()+"");
 			order.setState(0);
 			order.setUserId(orderUserId);
 			System.out.println("====orderDeptId==="+orderDeptId);
 			System.out.println("====consignor==="+consignor);
			System.out.println("====phone==="+phone);
			System.out.println("====goodsName==="+goodsName);
 			boolean isSuccess = service.getOrderService().addOrder(order);
 			if(isSuccess){
 				boolean isExisted = service.getUserService().checkUserNameExisted(Integer.parseInt(orderUserId));
 				System.out.println("==isExisted=="+isExisted);
 				if(!isExisted){
 					System.out.println("==该用户没有填写过基本信息=="+isExisted);
 					service.getUserService().updateUserName(Integer.parseInt(orderUserId), consignor);
 				}
 				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+order.toString());
 				System.out.println("====生成定单成功=====");
 			}else{
 				this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+StatusCode.STATUS_FAIL);
 				System.out.println("====生成定单失败=====");
 			}
   		} catch (Exception e) {
   			e.printStackTrace();
 		}
 	}
 	//网点查询请求
 	public void deptSearch(){
 		setCharacter();
 		String searchDept = this.request.getParameter("searchDept");
 		String cityId = this.request.getParameter("searchDeptCityId");
 		System.out.println("======= deptName search dept=="+searchDept);
 		//没有输入关键字，返回全部网点
   		if(searchDept==null||"".equals(searchDept)){
   			try {
   				Map<String,Dept> depts = ServerContext.findDeptByCityId(cityId);
   				if(depts != null){
   					JSONObject json = new JSONObject();
   	   				json.putAll(depts);
   	   	 			System.out.println("=======in cityId search dept=="+json.toString().length());
   	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+(json==null?StatusCode.STATUS_FAIL:json));
   				}
 			} catch (Exception e) {
 				e.printStackTrace();
			}
 		}else{
 			try {
   				Map<String,Dept> depts = ServerContext.findDeptByName(searchDept);
   				if(depts != null){
   					JSONObject json = new JSONObject();
   	   				json.putAll(depts);
   	   	 			System.out.println("=======in deptName search dept=="+json.toString().length());
   	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+(json==null?StatusCode.STATUS_FAIL:json));
   				}
 			} catch (Exception e) {
 				e.printStackTrace();
			}
 		}
 	}
 	//运单查询请求
 	public void goodsTrack(){
 		setCharacter();
 		try {
 			String param = this.request.getParameter("WayBillNumber");
			System.out.println("=======WayBillNumber=="+param);
			String result = service.getOrderService().findWayBill(param);
  			if(result != null){
 				System.out.println("=======result=="+result);
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+result);
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}
 	}
 	//价格时效查询请求
 	@SuppressWarnings("unchecked")
	public void priceSearch(){
 		setCharacter();
 		try {
 			String startCityId = this.request.getParameter("startCityId").trim();
 			String endCityId = this.request.getParameter("endCityId").trim();
 			String trtype = this.request.getParameter("tranportType").trim();
 			ArrayList<TransPropertyEnum> tranportType = TransPropertyEnum.getEnum();
 			int type = 0 ;
  			for (int i = 0; i < tranportType.size(); i++) {
				if(tranportType.get(i).getEnumName().equals(trtype)){
					type = tranportType.get(i).getEnumValue();
					System.out.println("=======getEnumName=="+tranportType.get(i).getEnumName());	 		
		 			System.out.println("=======getEnumValue=="+tranportType.get(i).getEnumValue()); 
				}
			}	 
 			//转换成字符Id,进行查询	 
 			System.out.println("=======startCityId=="+startCityId);
 			System.out.println("=======endCityId=="+endCityId);
 			System.out.println("=======trtype=="+trtype);
 			
 			PriceBean pb = service.getOrderService().priceSearch(startCityId, endCityId, type, 0, 0);
  			if(pb != null){
 				System.out.println("=======PriceBean=="+pb.toString());
				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+pb.toString());
			}
		} catch (IOException e) {
 			e.printStackTrace();
		}
 	}
 	/**
 	 * 订单管理
 	 * 查询某个用户的未受理或受理或取消定单
 	 *  0:表示未受理 1:表示已受理 -1:表示该订单已经取消
 	 */
 	public void orderManage(){
 		setCharacter();
 		String userId = this.request.getParameter("userId");
 		System.out.println("------userId---------"+userId);
 		String untreated = this.request.getParameter("untreated");
 		String treated = this.request.getParameter("treated");
 		String last = this.request.getParameter("last");
 		String updateOrderNum = this.request.getParameter("updateOrderNum");
 		String cancle = this.request.getParameter("cancle");
 		String findCancle = this.request.getParameter("findCancle");
 		if(findCancle != null){
 			Map<String,Order> map = ServerContext.findCancleOrders(userId, -1);
 	    	try {
 	   			if(map != null){
 	   				JSONObject json = new JSONObject();
 	   				json.putAll(map);
  	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+json.toString());
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"without cancled order");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}else if(cancle != null){
 			boolean isUpdate= service.getOrderService().cancleOrder(updateOrderNum);
 	    	try {
 	   			if(isUpdate){
 	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+"updated Order status");
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"updated order fail");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}else if(last != null){
 			Map<String,Order> map = ServerContext.findLastOrder(userId, 1,1);
 	    	try {
 	   			if(map != null){
 	   				JSONObject json = new JSONObject();
 	   				json.putAll(map);
 	   				System.out.println("------orders----------"+json.toString());
 	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+json.toString());
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"without last order");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}else if(treated != null){
 			Map<String,Order> map = ServerContext.findStatusOrders(userId, 1);
 	    	try {
 	   			if(map != null){
 	   				JSONObject json = new JSONObject();
 	   				json.putAll(map);
 	   				System.out.println("------orders----------"+json.toString());
 	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+json.toString());
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"without untreated order");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}else if(untreated != null){
 			Map<String,Order> map = ServerContext.findStatusOrders(userId, 0);
 	    	try {
 	   			if(map != null){
 	   				JSONObject json = new JSONObject();
 	   				json.putAll(map);
 	   				System.out.println("------orders----------"+json.toString());
 	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+json.toString());
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"without untreated order");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}else{
 			Map<String,Order> map = ServerContext.findAllOrders(userId);
 	    	try {
 	   			if(map != null){
 	   				JSONObject json = new JSONObject();
 	   				json.putAll(map);
 	   				System.out.println("------orders----------"+json.toString());
 	   				this.response.getWriter().write(StatusCode.STATUS_OK+"#"+json.toString());
 	    		}else{
 	    			this.response.getWriter().write(StatusCode.STATUS_FAIL+"#"+"without order");
 	    		}
 			} catch (IOException e) {
 	 			e.printStackTrace();
 			}
 		}	
 	}
}
