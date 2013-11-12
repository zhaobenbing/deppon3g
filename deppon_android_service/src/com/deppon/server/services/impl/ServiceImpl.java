package com.deppon.server.services.impl;

import com.deppon.server.services.intfce.ICityService;
import com.deppon.server.services.intfce.IDeptService;
import com.deppon.server.services.intfce.IOrderService;
import com.deppon.server.services.intfce.IProvinceService;
import com.deppon.server.services.intfce.IService;
import com.deppon.server.services.intfce.IUserService;
/**
 * @ 功能描述：管理service实现
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public class ServiceImpl implements IService{
	private IUserService UserService ;
	private IProvinceService provinceService;
	private ICityService cityService;
	private IDeptService deptService;
	private IOrderService orderService;
	@Override
	public IUserService getUserService() {
		UserService = new UserServiceImpl();
 		return UserService;
	}
	@Override
	public IProvinceService getProvinceService() {
		provinceService = new ProvinceServiceImpl();
		return provinceService;
	}
	@Override
	public ICityService getCityService() {
		cityService = new CityServiceImpl();
		return cityService;
	}
	@Override
	public IDeptService getDeptService() {
		deptService = new DeptServiceImpl();
 		return deptService;
	}
	public IOrderService getOrderService(){
		orderService = new OrderServiceImpl();
		return orderService;
 	}
}
