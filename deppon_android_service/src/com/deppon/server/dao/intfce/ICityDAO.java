package com.deppon.server.dao.intfce;

import java.util.List;

import com.deppon.common.beans.City;
import com.deppon.common.core.ICore;
	/**
	 * @功能描述：城市DAO
	 * @author  ：赵本兵
	 * @创建时间：2011-10-5
	 */
public interface ICityDAO extends ICore<City, String> {
	//查找所有城市
	public List<City>  findAllCitys();
	//根据城市Id查找城市
	public City findCityById(String cityId);
	//根据城市名查找城市
	public City findCityByName(String CityName);
	//添加城市
	public void addCity(City city);
	//根据省份Id查找城市
	public List<City> findcityByProvinceId(String provinceId);
	
}
