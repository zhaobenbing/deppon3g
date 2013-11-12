package com.deppon.common.beans;
/**
 * 联系人信息实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 */
public class Contact implements java.io.Serializable{
	private static final long serialVersionUID = 5677734262787292685L;
	//与业务无关的id
	private int id;
	//联系人姓名
	private String name;
	//联系人电话号码
	private String telephone;
	//联系人固定电话
	private String mobilephone;
	//联系人地址
	private String address;
	//联系人省份
    private String province;
    //联系人城市
	private String city;
	public Contact() {
 	}
	public Contact(String name, String telephone, String mobilephone,
			String address, String province, String city) {
		this.name = name;
		this.telephone = telephone;
		this.mobilephone = mobilephone;
		this.address = address;
		this.province = province;
		this.city = city;
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
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj == null){
			return false;
		}
		if(obj instanceof Contact){
			Contact con = (Contact)obj;
			return con.name.equals(this.name)&&con.telephone.equals(this.telephone);
		}else {
			return false;
		}
  	}
	@Override
	public int hashCode() {
 		return name.hashCode()+telephone.hashCode()+10;
	}
}
