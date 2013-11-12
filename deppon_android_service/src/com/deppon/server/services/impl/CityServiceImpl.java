package com.deppon.server.services.impl;

import java.util.List;

import com.deppon.common.beans.City;
import com.deppon.server.dao.impl.CityDAOImpl;
import com.deppon.server.dao.intfce.ICityDAO;
import com.deppon.server.services.intfce.ICityService;
/**
 * @功能描述：城市实现Service
 * @author  ：赵本兵
 * @创建时间：2011-10-5
 */
public class CityServiceImpl implements ICityService{
	private ICityDAO cityDAO ;
	public CityServiceImpl() {
		cityDAO = new CityDAOImpl();
	}
	@Override
	public void addCity(City city) {
		cityDAO.addCity(city);
	}
	@Override
	public List<City> findAllCitys() {
 		return cityDAO.findAllCitys();
	}
	@Override
	public City findCityById(String cityId) {
 		return cityDAO.findCityById(cityId);
	}
	@Override
	public City findCityByName(String CityName) {
 		return cityDAO.findCityByName(CityName);
	}
	@Override
	public List<City> findCityByProvinceId(String provinceId) {
 		return cityDAO.findcityByProvinceId(provinceId);
	}	 
}
