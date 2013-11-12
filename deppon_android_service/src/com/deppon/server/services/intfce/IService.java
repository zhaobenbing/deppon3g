package com.deppon.server.services.intfce;

/**
 * @ 功能描述：管理service
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public interface IService {
	//获取IUserService
	public IUserService getUserService();
	//获取IProvinceService
	public IProvinceService getProvinceService();
	//获取ICityService
	public ICityService getCityService();
	//获取IDeptService
	public IDeptService getDeptService();
	//获取IOrderService
	public IOrderService getOrderService();
}
