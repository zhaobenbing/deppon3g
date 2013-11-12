package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.StatusCode;

/**
 * @功能描述：保存个人资料
 * @author 赵本兵
 * @创建日期：2011-11-3
 */
public class SavePersonInfoProtocol extends BaseProtocol {
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public SavePersonInfoProtocol() {
		context = new ClientContext();
 	}
	public SavePersonInfoProtocol(ClientContext context) {
		this.context = context;
		ACTION = getAction();
 	}
	private String getAction() {
		return context.getSystemProperty("SAVEPERSONINFO");
	}
	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		User user = (User) context.getBusinessData("PersonDatum");
		addNameValuePair("PersonDatum", user.toString());
		sendRequest(URL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
				return true;
  			}
		}
		return false;
	}
}
