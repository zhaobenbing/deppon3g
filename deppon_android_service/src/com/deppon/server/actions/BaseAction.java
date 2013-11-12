package com.deppon.server.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 基本的Action，所有的Action都要继承此Action
 * @author :赵本兵
 * @ 创建时间：2011-8-15
 */
public  class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	private static final long serialVersionUID = -6736093693492488823L;
	private HttpServletResponse response;
	private HttpServletRequest request;
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
 		this.response = arg0;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
