package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.User;
import com.deppon.common.util.StatusCode;

/**
 * @功能描述：修改密码协议
 * @author 赵本兵
 * @创建日期：2011-11-1
 */
public class ModifyPasswordProtocol extends BaseProtocol {
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public ModifyPasswordProtocol() {
		context = new ClientContext();
 	}
	public ModifyPasswordProtocol(ClientContext context) {
		this.context = context;
		ACTION = getAction();
 	}
	private String getAction() {
		return context.getSystemProperty("MODIFYPWD");
	}

	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		User user = (User) context.getBusinessData("CurrentUser");
		String oldpwd = (String) context.getBusinessData("oldpwd");
		String newpwd = (String) context.getBusinessData("newpwd");
		System.out.println("==CurrentUser==="+user.getId());
		System.out.println("==oldpwd==="+oldpwd);
		System.out.println("==newpwd==="+newpwd);
 		addNameValuePair("currUserId", user.getId()+"");
		addNameValuePair("oldpwd", oldpwd);
		addNameValuePair("newpwd", newpwd);
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
