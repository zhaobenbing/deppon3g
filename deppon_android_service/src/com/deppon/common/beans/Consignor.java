package com.deppon.common.beans;
/**
 * 发货人信息：在填写订单时需要填写
 * @author  ：赵本兵
 * @创建时间：2011-7-28
 */
public class Consignor implements java.io.Serializable{
	private static final long serialVersionUID = -1451979534028831543L;
	private int id;
	//发货人姓名
	private String name;
    //发货人电话
    private String telephone;
    //发货人固定电话
    private String mobilephone;
	 //发货人地址
    private String address;
    //发货人省份
    private String province;
    //发货人城市
	private String city;
    public Consignor() {
 	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}