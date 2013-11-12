package com.deppon.client.protocol;

import java.util.Map;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Order;
import com.deppon.common.beans.User;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

/**
 * @功能描述：订单管理请求
 * @author ：赵本兵
 * @创建日期：2011-10-26
 */
public class OrderManageProtocol extends BaseProtocol {
	//上下文
	private ClientContext context ;
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public OrderManageProtocol() {
		context = new ClientContext();
 	}
	public OrderManageProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("ORDERMANAGE");
 	}
	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
 		// 获取当前用户信息
		User user = (User) context.getBusinessData("CurrentUser");
		if(arg0!=null&&arg0.equals("untreated")){
			addNameValuePair("untreated", arg0.toString().trim());
		}
		if(arg0!=null&&arg0.equals("treated")){
			addNameValuePair("treated", arg0.toString().trim());
		}
		if(arg0!=null&&arg0.equals("last")){
			addNameValuePair("last", arg0.toString().trim());
		}
		if(arg0!=null&&arg0.equals("cancle")){
			addNameValuePair("cancle", arg0.toString().trim());
		}
		if(arg0!=null&&arg0.equals("findCancle")){
			addNameValuePair("findCancle", arg0.toString().trim());
		}
  		addNameValuePair("userId", user.getId()+"");
  		sendRequest(URL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
 				if(arg0!=null&&arg0.equals("findCancle")){
 					Map<String,Order> map = ParserData.parseOrder(result.toString().trim());
 	 				context.addBusinessData("FindCancledOrders", map);
 	 				return true;
 				}else if(arg0!=null&&arg0.equals("untreated")){
 					Map<String,Order> map = ParserData.parseOrder(result.toString().trim());
 	 				context.addBusinessData("UntreatedOrders", map);
 	 				return true;
 				}else if(arg0!=null&&arg0.equals("treated")){
 					Map<String,Order> map = ParserData.parseOrder(result.toString().trim());
 	 				context.addBusinessData("TreatedOrders", map);
 	 				return true;
 				}else if(arg0!=null&&arg0.equals("last")){
 					Map<String,Order> map = ParserData.parseOrder(result.toString().trim());
 	 				context.addBusinessData("LastOrders", map);
 	 				return true;
 				}
 				else{
 					Map<String,Order> map = ParserData.parseOrder(result.toString().trim());
 					context.addBusinessData("AllOrders", map);
 					return true;
 				}
  			}
		}
 		return false;
	}
}
