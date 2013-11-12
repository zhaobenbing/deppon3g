package com.deppon.common.beans;

/**
 * 城市实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 * @修改：新增3个属性 //old城市Id,	//old城市名称,//old所属省份
 */
public class City extends AbstractBean{
	private static final long serialVersionUID = 1L;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//所属省份
	private String father;
	//old城市Id
	private String cityOldId;
	//old城市名称
	private String cityOldName;
	//old所属省份
	private String oldFather;
	public City() {
 	}
	public City(String cityId, String cityName, String father,
			String cityOldId, String cityOldName, String oldFather) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.father = father;
		this.cityOldId = cityOldId;
		this.cityOldName = cityOldName;
		this.oldFather = oldFather;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
	public String getCityOldId() {
		return cityOldId;
	}
	public void setCityOldId(String cityOldId) {
		this.cityOldId = cityOldId;
	}
	public String getCityOldName() {
		return cityOldName;
	}
	public void setCityOldName(String cityOldName) {
		this.cityOldName = cityOldName;
	}
	public String getOldFather() {
		return oldFather;
	}
	public void setOldFather(String oldFather) {
		this.oldFather = oldFather;
	}
}
