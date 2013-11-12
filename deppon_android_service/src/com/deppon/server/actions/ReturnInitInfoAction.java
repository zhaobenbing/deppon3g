package com.deppon.server.actions;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Province;
import com.deppon.common.util.StatusCode;
import com.deppon.server.services.ServerContext;

public class ReturnInitInfoAction extends BaseAction{
	private static final long serialVersionUID = -5570362277228360195L;
 	private HttpServletRequest request;
	private HttpServletResponse response;
 	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		super.setServletRequest(arg0);
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		super.setServletResponse(arg0);
	}
	private void setCharacter(){
  		try {
 			this.response.setCharacterEncoding("utf-8");
 	 		this.response.setContentType("text/html;charset=utf-8");
			this.request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
		}
	}
	//初始化的数据返回给客户端
 	public void initDataToClient(){
 		setCharacter();
 		try {
 			//从缓存获取省份，城市，营业网点
 			Map<String,Province> pros = ServerContext.provinceCache;
 			JSONObject jsonP = new JSONObject();
 			jsonP.putAll(pros);
 			
 			Map<String,City> citys = ServerContext.cityCache;
 			JSONObject jsonC = new JSONObject();
 			jsonC.putAll(citys);
 			System.out.println("jsonP size============="+jsonP.toString().trim().length());
 			System.out.println("jsonC size============="+jsonC.toString().trim().length());
 			System.out.println("total size============="+(jsonP.toString().trim().length()+jsonC.toString().trim().length()));
 			System.out.println("please init data success");
			this.response.getWriter().write(StatusCode.STATUS_OK+"#"+jsonP+"#"+jsonC+"#");
		} catch (Exception e) {
 			e.printStackTrace();
		}
 	}
}
