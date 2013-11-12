package com.deppon.client.protocol;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.deppon.client.service.ClientContext;
import com.deppon.common.beans.User;
import com.deppon.common.util.StatusCode;

/**
 * 登陆协议
 * @author ：赵本兵
 * @ 创建时间：2011-8-13
 */
public class LoginProtocol extends BaseProtocol {
	//上下文
	private ClientContext context ;
	//请求action
	public static String ACTION = "";
	public LoginProtocol() {
		context = new ClientContext();
 	}
 	public LoginProtocol(ClientContext context) {
 		this.context = context;
 		ACTION = getAction();
	}
 	//从配置文件中读取请求ACTION
	private String getAction() {
		return context.getSystemProperty("LOGIN");
	}
	@Override
	public boolean executeSomeProtocol(Object email,Object password,String baseUrl){
		
 		String tempUrl = baseUrl+ACTION;
		try {
			addNameValuePair("email", email.toString());
			addNameValuePair("password", password.toString());
			sendRequest(tempUrl);
			String json = parse();	
			boolean isEquals = RESULT.equalsIgnoreCase(StatusCode.STATUS_OK);
			if(isEquals) {
				//将返回结果转换为JSON
				//System.out.println("getJSON------>"+getJSON());
				JSONObject job = new JSONObject(json);
			   	System.out.println("JSONObject------>"+job.toString());
				User user = new User();
				user.setEmail(job.getString("email"));
				user.setPassword(job.getString("password"));
				user.setTelephone(job.getString("telephone"));
				user.setId(job.getInt("id"));
				user.setAddress(job.getString("address"));
				user.setCity(job.getString("city"));
				user.setMobilephone(job.getString("mobilephone"));
				user.setPostCode(job.getString("postCode"));
				user.setName(job.getString("name"));
				user.setLoginName(job.getString("loginName"));
				user.setProvince(job.getString("province"));
				user.setLastActionTime(new Date(System.currentTimeMillis()));
				user.setLastUpdatetime(new Date(System.currentTimeMillis()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject jsonObject = new JSONObject(job.getString("regiterTime"));
				String year = (1900+Integer.parseInt(jsonObject.getString("year")))+"";
				String month = (1+Integer.parseInt(jsonObject.getString("month")))+"";
				String date = jsonObject.getString("date");
				String hour = jsonObject.getString("hours");
				String minutes = jsonObject.getString("minutes");
				String seconds = jsonObject.getString("seconds");
				String tempString = year+"-"+month+"-"+date+" " +hour+":"+minutes+":"+seconds;
 				user.setRegiterTime(sdf.parse(tempString));
 				user.setSex(job.getString("sex"));
 				user.setSlstatus(Integer.parseInt(job.getString("slstatus")));
				context.addBusinessData("CurrentUser", user);
 				return true;
			} else {
				return false;
			}
 		} catch (Exception e) {
 			e.printStackTrace();
 			return false;
		}
 	}
}
