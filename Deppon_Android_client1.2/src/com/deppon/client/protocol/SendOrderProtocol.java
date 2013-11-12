package com.deppon.client.protocol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Order;
import com.deppon.common.beans.User;
import com.deppon.common.util.StatusCode;

/**
 * @功能描述：下单协议
 * @author 赵本兵 
 * @ 创建时间：2011-10-13
 */
public class SendOrderProtocol extends BaseProtocol {
	// 请求action
	public static String ACTION = "";
	// private ClientContext context = new ClientContext();
	// 声明控制器ClientController
	private ClientController controller;
	// 声明客户端上下文
	private ClientContext context;

	public SendOrderProtocol() {
		context = new ClientContext();
	}

	public SendOrderProtocol(ClientContext context) {
		this.context = context;
		ACTION = context.getSystemProperty("SENDORDER");
	}

 	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String baseURL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String consignor =(String) context.getBusinessData("consignor");
		String phone = (String) context.getBusinessData("phone");
		String goodsName = (String) context.getBusinessData("goodsName");
		String OrderDeptId = (String)context.getBusinessData("OrderDeptId");
		String userId = ((User)context.getBusinessData("CurrentUser")).getId()+"";
		addNameValuePair("consignor", consignor);
		addNameValuePair("phone", phone);
		addNameValuePair("goodsName", goodsName);
		addNameValuePair("OrderDeptId", OrderDeptId);
		addNameValuePair("OrderUserId", userId);
 		sendRequest(baseURL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.equals(StatusCode.STATUS_OK)) {
 				JSONObject json = new JSONObject(result);
 				Order order = new Order();
 				order.setDeptId(json.getString("deptId"));
 				order.setGoodsName(json.getString("goodsName"));
 				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 				//解析日期
 				JSONObject jsonObject = new JSONObject(json.getString("orderDate"));
 				String year = (1900+Integer.parseInt(jsonObject.getString("year")))+"";
				String month = (1+Integer.parseInt(jsonObject.getString("month")))+"";
				String date = jsonObject.getString("date");
				String hour = jsonObject.getString("hours");
				String minutes = jsonObject.getString("minutes");
				String seconds = jsonObject.getString("seconds");
				String tempString = year+"-"+month+"-"+date+" " +hour+":"+minutes+":"+seconds;
 				
 				order.setOrderDate((df.parse(tempString)));
 				order.setOrderNumber(json.getString("orderNumber"));
 				order.setState(json.getInt("state"));
 				order.setUserId(json.getString("userId"));
 				System.out.println("===orderInfo===="+result);
 				context.addBusinessData("UserOrderInfo", order);
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
}
