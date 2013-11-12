package com.deppon.common.beans;
/**
 * 省份实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 */
public class Province1 implements java.io.Serializable{
	private static final long serialVersionUID = 6640453723665990547L;
	//省份编号
	private int province_id ;
	//省份名称
	private String province_name ;
	//省份简称
	private String province_simple_name ;
	//省份拼音
	private String province_spell_name  ;
	public Province1() {
 	}
	public Province1(String province_name, String province_simple_name,
			String province_spell_name) {
		this.province_name = province_name;
		this.province_simple_name = province_simple_name;
		this.province_spell_name = province_spell_name;
	}
	public int getProvince_id() {
		return province_id;
	}
	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getProvince_simple_name() {
		return province_simple_name;
	}
	public void setProvince_simple_name(String province_simple_name) {
		this.province_simple_name = province_simple_name;
	}
	public String getProvince_spell_name() {
		return province_spell_name;
	}
	public void setProvince_spell_name(String province_spell_name) {
		this.province_spell_name = province_spell_name;
	}
}
