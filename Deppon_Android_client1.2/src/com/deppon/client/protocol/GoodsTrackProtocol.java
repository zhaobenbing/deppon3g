package com.deppon.client.protocol;

import com.deppon.client.service.ClientContext;
import com.deppon.client.service.ClientController;
import com.deppon.common.beans.TrackInfo;
import com.deppon.common.util.ParserData;
import com.deppon.common.util.StatusCode;

/**
 * @货物跟踪
 * @author :赵本兵
 * @创建时间：2011-10-17
 */
public class GoodsTrackProtocol extends BaseProtocol{
	//上下文
	private ClientContext context ;
	// 控制器
	private ClientController controller;
	//请求action
	public static String ACTION = "";
	public GoodsTrackProtocol() {
		context = new ClientContext();
 	}
 	public GoodsTrackProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("GOODSTRACK");
	}
 	@Override
	public boolean executeSomeProtocol(Object arg0, Object arg1, String URL)
			throws Exception {
		controller = ClientController.getController();
		context = controller.getContext();
		String param = (String) context.getBusinessData("WayBillNumber");
  		addNameValuePair("WayBillNumber", param);
    	sendRequest(URL + ACTION);
		String result = parse();
		if (result != null) {
 			if (RESULT.trim().equals(StatusCode.STATUS_OK)) {
 				String[] data = result.split("%!%");
  				if(data[0] != null){
  					TrackInfo trackInfo = ParserData.parseTrackInfo(data[0]);
  					context.addBusinessData("TrackBasicInfo", trackInfo); 		
  				}
  				if(data[1] != null){
  					context.addBusinessData("TrackDetail",ParserData.parseTrackDetail(data[1])); 		
  				}
  				return true;
  			}
		}
		return false;
	}
}
