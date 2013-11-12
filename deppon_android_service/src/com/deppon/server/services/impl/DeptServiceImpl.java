package com.deppon.server.services.impl;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Province;
import com.deppon.server.dao.impl.DeptDAOImpl;
import com.deppon.server.dao.intfce.IDeptDAO;
import com.deppon.server.services.intfce.IDeptService;
/**
 * @功能描述：部门Service实现
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public class DeptServiceImpl implements IDeptService{
	private IDeptDAO deptDAO;
	public DeptServiceImpl() {
		deptDAO = new DeptDAOImpl();
 	}
 	@Override
	public Province[] findAllProvice() {
 		return deptDAO.findAllProvice();
	}

	@Override
	public City[] findCity() {
 		return deptDAO.findCity();
	}

	@Override
	public City[] findCity(String provinceId) {
 		return deptDAO.findCity(provinceId);
	}

	@Override
	public String findCityById(String cityId) {
 		return deptDAO.findCityById(cityId);
	}

	@Override
	public String findProById(String proId) {
 		return deptDAO.findProById(proId);
	}

	@Override
	public String findStationInfo(String deptId) {
 		return deptDAO.findStationInfo(deptId);
	}
	@Override
	public Dept findDept(String deptId) {
 		return deptDAO.findDept(deptId);
	}
	@Override
	public Dept[] findDeptByCityId(String cityId) {
 		return deptDAO.findDeptByCityId(cityId);
	}
	@Override
	public Dept[] findDept() {
 		return deptDAO.findDept();
	}
	@Override
	public String convertCityId(String cityId) {
 		return deptDAO.convertCityId(cityId);
	}
	@Override
	public String convertProId(String proId) {
 		return deptDAO.convertProId(proId);
	}
	@Override
	public String convertCityCharId(String cityId) {
		return deptDAO.convertCityCharId(cityId);
	}
	@Override
	public String findDeptInfo(String deptName) {
 		return deptDAO.findDeptInfo(deptName);
	}
	@Override
	public Dept[] findDeptByName(String deptName) {
 		return deptDAO.findDeptByName(deptName);
	}
}
