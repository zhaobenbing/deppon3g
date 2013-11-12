package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

/**
 * @价格时效查询
 * @author :赵本兵
 * @创建时间：2011-10-19
 */
public class PriceSearchProtocol extends BaseProtocol{
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public PriceSearchProtocol() {
		context = new ClientContext();
 	}
 	public PriceSearchProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("PRICESEARCH");
	}
 	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String startCityId = (String) context.getBusinessData("startCityId");
		String endCityId = (String) context.getBusinessData("endCityId");
		String tranportType = (String) context.getBusinessData("tranportType");
 		addNameValuePair("startCityId", startCityId);
		addNameValuePair("endCityId", endCityId);
		addNameValuePair("tranportType", tranportType);
  		sendRequest(URL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
  				context.addBusinessData("PriceSearchInfo", ParserData.parsePriceSearch(result));
  				System.out.println("====PriceSearchInfo===="+ParserData.parsePriceSearch(result));
				return true;
  			}
		}
		return false;
	}
}
