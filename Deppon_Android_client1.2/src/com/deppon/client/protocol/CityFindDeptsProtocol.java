package com.deppon.client.protocol;

import java.util.Map;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.Dept;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

/**
 * @城市Id查找网点
 * @author :赵本兵
 * @创建时间：2011-10-12
 */
public class CityFindDeptsProtocol extends BaseProtocol{
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public CityFindDeptsProtocol() {
		context = new ClientContext();
 	}
 	public CityFindDeptsProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("CITYFINDDEPT");
	}
	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String param = (String) context.getBusinessData("cityId");
  		sendRequest(URL + ACTION+"?cityId="+param);
		String result = originalData();
		if (result != null) {
			String[] data = result.trim().split("#");
			if (data[0].trim().equals(StatusCode.STATUS_OK)) {
				if(data[1].trim() !=StatusCode.STATUS_FAIL){
					Map<String,Dept> depts = ParserData.parseDepts(data[1].trim());
	 				context.addBusinessData("cacheDepts", depts);
	 				System.out.println("cacheDepts size--------->" + depts.size());
					return true;
				}
 			    
			}
		}
		return false;
	}
}
