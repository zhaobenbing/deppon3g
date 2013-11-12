package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

/**
 * @网点查询
 * @author :赵本兵
 * @创建时间：2011-10-14
 */
public class DeptSearchProtocol extends BaseProtocol{
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public DeptSearchProtocol() {
		context = new ClientContext();
 	}
 	public DeptSearchProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("DEPTSEARCH");
	}
 	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String param = (String) context.getBusinessData("searchDept");
		String searchDeptCityId = (String)context.getBusinessData("searchDeptCityId");
		addNameValuePair("searchDept", param);
		addNameValuePair("searchDeptCityId", searchDeptCityId);
  		sendRequest(URL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
  				context.addBusinessData("searchDeptResult", ParserData.parseDepts(result));
  				System.out.println("====searchDeptResult===="+ParserData.parseDepts(result));
				return true;
  			}
		}
		return false;
	}
}
