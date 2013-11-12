package com.deppon.common.beans;


/**
 * 省份实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-10-8
 * @修改：新增3个属性,old省份id,old省份名称,,索引index
 * @修改日期：2011-10-21
 */
public class Province extends AbstractBean {
	private static final long serialVersionUID = -5022999090419038590L;
	//省份id
	private String provinceId;
	//省份名称
	private String ProvinceName;
	//old省份id
	private String provinceOldId;
	//old省份名称
	private String provinceOldName;
	//索引index
	private String findex;
	public Province() {
 	}
 	public Province(String provinceId, String provinceName,
			String provinceOldId, String provinceOldName, String findex) {
		super();
		this.provinceId = provinceId;
		ProvinceName = provinceName;
		this.provinceOldId = provinceOldId;
		this.provinceOldName = provinceOldName;
		this.findex = findex;
	}

	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return ProvinceName;
	}
	public void setProvinceName(String provinceName) {
		ProvinceName = provinceName;
	}
	public String getProvinceOldId() {
		return provinceOldId;
	}
	public void setProvinceOldId(String provinceOldId) {
		this.provinceOldId = provinceOldId;
	}
	public String getProvinceOldName() {
		return provinceOldName;
	}
	public void setProvinceOldName(String provinceOldName) {
		this.provinceOldName = provinceOldName;
	}
	public String getFindex() {
		return findex;
	}
	public void setFindex(String findex) {
		this.findex = findex;
	}
}
