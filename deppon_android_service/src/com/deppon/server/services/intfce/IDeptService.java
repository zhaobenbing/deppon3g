package com.deppon.server.services.intfce;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Province;
/**
 * @功能描述：部门Service
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public interface IDeptService {
	//查找所有省份
	public Province[] findAllProvice();
	//查找所有城市
	public City[] findCity();
	//查找所有部门
	public Dept[] findDept();
	//根据省份Id查找城市
	public City[] findCity(String provinceId);
	//根据城市Id查找部门
	public Dept[] findDeptByCityId(String cityId);
	//网点名称模糊查找部门信息
	public Dept[] findDeptByName(String deptName);
	//根据部门Id获取部门信息
	public Dept findDept(String deptId);
	//根据网点Id获取网点信息数据
	public String findStationInfo(String deptId);
	//根据部门名称获取部门信息数据
	public String findDeptInfo(String deptName);
	//获取省份名称
	public String findProById(String proId);
	//获取城市名称
	public String findCityById(String cityId);
	//字符串Id,查找数字Id
	public String convertProId(String proId);
	public String convertCityId(String cityId);
	//数字Id,查找字符串Id
	public String convertCityCharId(String cityId);
}
