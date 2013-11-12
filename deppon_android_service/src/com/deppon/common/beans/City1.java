package com.deppon.common.beans;

import java.io.Serializable;
/**
 * 城市实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 */
public class City1 implements Serializable{
	private static final long serialVersionUID = 8976204528322538018L;
   private int city_id ;
	//城市名称
	private String city_name ;
	//所属省份
	private int provinceId;
	//城市拼音
	private String city_spell_name ;
	public City1() {
	}
	public City1(String city_name, int provinceId, String city_spell_name) {
		this.city_name = city_name;
		this.provinceId = provinceId;
		this.city_spell_name = city_spell_name;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public int getProvince() {
		return provinceId;
	}
	public void setProvince(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getCity_spell_name() {
		return city_spell_name;
	}
	public void setCity_spell_name(String city_spell_name) {
		this.city_spell_name = city_spell_name;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((city_name == null) ? 0 : city_name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final City1 other = (City1) obj;
		if (city_name == null) {
			if (other.city_name != null)
				return false;
		} else if (!city_name.equals(other.city_name))
			return false;
		return true;
	}
}
