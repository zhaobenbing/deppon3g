package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.StatusCode;

/**
 * @功能描述：注册协议
 * @author Administrator赵本兵 
 * @ 创建时间：2011-8-15
 */
public class RegistProtocol extends BaseProtocol {
	// 请求action
	public static String ACTION = "";
	// private ClientContext context = new ClientContext();
	// 声明控制器ClientController
	private ClientController controller;
	// 声明客户端上下文
	private ClientContext context;

	public RegistProtocol() {
		context = new ClientContext();
	}

	public RegistProtocol(ClientContext context) {
		this.context = context;
		ACTION = context.getSystemProperty("REGIST");
	}

 	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String baseURL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		User temp = (User) context.getBusinessData("RegistUser");
		System.out.println("temp--->" + temp.toString());
		sendRequest(baseURL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.equals(StatusCode.STATUS_OK)) {
				return true;
			}
 			if(RESULT.equals(StatusCode.STATUS_EXISTED)){
				context.addBusinessData("RegistEmailExisted",StatusCode.TEXT_EXISTED);
				return false;
			}
		}
		return false;
	}
}
