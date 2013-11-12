package com.deppon.client.protocol;

import java.util.Map;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Dept;
import com.deppon.common.util.StatusCode;

/**
 * @城市Id查找网点
 * @author :赵本兵
 * @创建时间：2011-10-13
 */
public class GetDeptInfoProtocol extends BaseProtocol{
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public GetDeptInfoProtocol() {
		context = new ClientContext();
 	}
 	public GetDeptInfoProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("GETDEPTINFO");
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String param = (String) context.getBusinessData("requestDept");
		Map<String,Dept> depts = (Map<String, Dept>) context.getBusinessData("cacheDepts");
		if(depts != null){
			for (Dept d : depts.values()) {
				if(d.getDeptName().equals(param)){
					param = d.getDeptid();
				}
			}
	  		sendRequest(URL + ACTION+"?deptId="+param);
			String result = parse();
			if (result != null) {
	 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
	  				context.addBusinessData("deptInfo", result);
	  				System.out.println("====deptInfo===="+result);
					return true;
	  			}
			}
		}
		return false;
	}
}
