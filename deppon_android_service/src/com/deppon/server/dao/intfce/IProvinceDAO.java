package com.deppon.server.dao.intfce;

import java.util.List;

import com.deppon.common.beans.Province;
import com.deppon.common.core.ICore;
/**
 * @功能描述：省份DAO
 * @author  ：赵本兵
 * @创建时间：2011-10-4
 */
public interface IProvinceDAO extends ICore<Province, String> {
	//查找所有省份
	public List<Province>  findAllProvinces();
	//根据省份Id查找省份
	public Province findProvinceById(String provinceId);
	//根据省份名查找省份
	public Province findProvinceByName(String provinceName);
	//添加省份
	public void addProvince(Province provnce);
}
