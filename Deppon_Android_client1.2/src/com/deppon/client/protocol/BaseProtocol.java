package com.deppon.client.protocol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.deppon.client.protocol.intfce.IBaseProtocol;
import com.deppon.common.util.StatusCode;
/**
 *  @手机端的协议，用户向服务器发送请求，同时服务器反馈给手机端信息的：
 *  @author Administrator：赵本兵
 *  @　创建时间：2011-8-15
 */
public abstract class BaseProtocol implements IBaseProtocol{
 	private HttpClient httpClient;
	private HttpPost httpRequest;
 	private HttpResponse response;
 	public static String RESULT = StatusCode.STATUS_UNEXIST;
 	private List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
 	private StringBuilder  sb;
	public BaseProtocol() {
		httpClient = new DefaultHttpClient();
	}

	/**
	 * 向服务器端发送请求
	 */
	public void sendRequest(String url) throws Exception {
		httpClient = new DefaultHttpClient();
		//设置超时30秒
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,15000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,15000);
		httpRequest = new HttpPost(new URI(url));
		httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePair,HTTP.UTF_8));
 		response = httpClient.execute(httpRequest);
	}

	/**
	 * 得到返回数据,不包含返回码
	 */
	public String parse() throws Exception {
		 sb = new StringBuilder();
		 sb.setLength(500);
		 System.out.println("parse StatusCode---------->"+response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()),1000);
			String str = null;
			while((str=buffer.readLine()) != null){
				sb.append(str);
			}
 			String[] temp = new String(sb).split("#");
 			if(temp != null&&temp.length>1){
 				//System.out.println("RESULT------------>"+temp[0].trim());
 	 			//System.out.println("temp[1]------------>"+temp[1].trim());
 	 			RESULT = temp[0].trim();	 			
 	  			return  temp[1].toString();
 			}
 			buffer.close();
  		}
		return null;
  	}
	//得到原始数据
	public String originalData() throws Exception {
		 sb = new StringBuilder();
		 sb.setLength(2*1024);
 		 System.out.println("originalData StatusCode---------->"+response.getStatusLine().getStatusCode());
 		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader buffer = new BufferedReader( new InputStreamReader(response.getEntity().getContent()),2*1024);
			String str = null;
			while((str=buffer.readLine()) != null){
				sb.append(str);
			}
 			buffer.close();
 			System.out.println("originalData length=========="+sb.toString().trim().length());
 			return  new String(sb);
		}
		return null;
 	}
	/**
	//得到原始数据,并存入到sd卡里的tempdepts.xml
	public String parseDepts() throws Exception {
		 sb = new StringBuilder();
		 sb.setLength(2*1024);
 		 System.out.println("parseDepts StatusCode---------->"+response.getStatusLine().getStatusCode());
 		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader buffer = new BufferedReader( new InputStreamReader(response.getEntity().getContent()),2*1024);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream("/mnt/sdcard/depts.txt"),"utf-8"));
			String str = null;
			String temp = "\"},\"",temp1 = "\"},###\"";
			
			while((str=buffer.readLine()) != null){
				sb.append(str);
				str.replace(temp, temp1);
				pw.println(str+"\n");
			}
			pw.flush();
			pw.close();
 			buffer.close();
 			System.out.println("parseDepts length=========="+sb.toString().trim().length());
 			return  new String(sb);
		}
		return null;
 	}
 	**/
	/**
	 * 向服务器发送信息
	 */
	public void addNameValuePair(String key, String value) {
		nameValuePair.add(new BasicNameValuePair(key, value));
	}
	/**
	 * 返回JSONObject对象数据模型
	 */
	public JSONObject getJSON() throws Exception {
		 return new JSONObject(parse());
	}
	public JSONArray getJsonArray() throws Exception {
		return new JSONArray(parse());
	}
	/**
	 * 执行某种协议
	 */
	public abstract boolean executeSomeProtocol(Object arg0,Object arg1,String URL) throws Exception;
}
