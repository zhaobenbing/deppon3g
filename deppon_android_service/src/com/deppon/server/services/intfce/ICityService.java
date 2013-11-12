package com.deppon.server.services.intfce;

import java.util.List;

import com.deppon.common.beans.City;

public interface ICityService {
	//查找所有城市
	public List<City>  findAllCitys();
	//根据城市Id查找城市
	public City findCityById(String cityId);
	//根据城市名查找城市
	public City findCityByName(String CityName);
	//添加城市
	public void addCity(City city);
	//根据省份Id查找城市
	public List<City> findCityByProvinceId(String provinceId);
}
