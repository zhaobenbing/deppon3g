package com.deppon.server.services.intfce;

import java.util.List;

import com.deppon.common.beans.Province;

public interface IProvinceService {
	//查找所有省份
	public List<Province>  findAllProvinces();
	//根据省份Id查找省份
	public Province findProvinceById(String provinceId);
	//根据省份名查找省份
	public Province findProvinceByName(String provinceName);
	//添加省份
	public void addProvince(Province provnce);
}
