package com.deppon.server.services.impl;

import java.util.List;

import com.deppon.common.beans.Province;
import com.deppon.server.dao.impl.ProvinceDAOImpl;
import com.deppon.server.dao.intfce.IProvinceDAO;
import com.deppon.server.services.intfce.IProvinceService;

public class ProvinceServiceImpl implements IProvinceService{
	private IProvinceDAO provinceDAO;
	public ProvinceServiceImpl() {
		provinceDAO = new ProvinceDAOImpl();
 	}
 	@Override
	public List<Province> findAllProvinces() {
  		return provinceDAO.findAllProvinces();
	}
	@Override
	public void addProvince(Province provnce) {
		provinceDAO.addProvince(provnce);
	}
	@Override
	public Province findProvinceById(String provinceId) {
 		return provinceDAO.findProvinceById(provinceId);
	}
	@Override
	public Province findProvinceByName(String provinceName) {
 		return provinceDAO.findProvinceByName(provinceName);
	}
 

}
