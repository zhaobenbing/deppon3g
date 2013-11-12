package com.deppon.client.protocol.intfce;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 制定标准
 * @author Administrator：赵本兵
 * @ 创建时间：2011-8-13
 */
public interface IBaseProtocol {
	//向服务器发送请求
	public void sendRequest(String url) throws Exception ; 
	//服务器返回的数据
	public String parse() throws Exception;
	//向服务器发送信息
	public void addNameValuePair(String key, String value) ;
	//返回JSONObject对象数据模型
	public JSONObject getJSON() throws Exception ;
	//如果服务器端返回来的是JSONArray的字符串，则把它转换成JSONArray
	public JSONArray getJsonArray() throws Exception ;
}
