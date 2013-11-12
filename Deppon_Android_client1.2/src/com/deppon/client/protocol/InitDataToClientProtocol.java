package com.deppon.client.protocol;

import java.util.Map;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.City;
import com.deppon.common.beans.Province;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

public class InitDataToClientProtocol extends BaseProtocol {
	// 上下文
	private ClientContext context;
	// 控制器
	private ClientController controller;
	//解析数据
	private ParserData parse ;
	public static String ACTION = "";
	
	public InitDataToClientProtocol() {
		context = new ClientContext();
	}

	public InitDataToClientProtocol(ClientContext context) {
		this();
		this.context = context;
		ACTION = getAction();
		
	}

	private String getAction() {
		return context.getSystemProperty("INITDATA");
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL){
		controller = ClientController.getController();
		context = controller.getContext();
		String result = "";
 		try {
			sendRequest(URL + ACTION);
			result = originalData();
		} catch (Exception e) {
			context.addBusinessData("TimeOut", "TimeOut");
			System.out.println("-----TimeOut-----" );
  			e.printStackTrace();
 			return false;
		}
 		if (result != null) {
			String[] data = result.trim().split("#");
			 System.out.println("return status code--------->" + data[0].trim());
			if (data[0].trim().equals(StatusCode.STATUS_OK)) {
  				Map<String,Province> pros = parse.parseProvinces(data[1].trim());
 			    Map<String,City> citys = parse.parseCitys(data[2].trim());
  			    System.out.println("cacheProvince size--------->" + pros.size());
				System.out.println("cacheCity size--------->" + citys.size());
 				context.addBusinessData("cacheProvinces", pros);
 				context.addBusinessData("cacheCitys", citys);
 				return true;
			}
		}
		return false;
	}
 }
