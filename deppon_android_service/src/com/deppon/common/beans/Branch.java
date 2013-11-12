package com.deppon.common.beans;
/**
 * 营业网点信息
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 */

public class Branch implements java.io.Serializable{
	private static final long serialVersionUID = -4392631238397486240L;
	//与业务无关的id
	private int id;
	//营业网点编号
	private String branchNumber;
	// 营业网点名称
	private String branchName;
	// 营业网点地址
	private String address;
	//营业网点电话
	private String telephone;
	//营业网点传真
	private String fax;
	//网点省份
	private Province1 province;
	//网点城市
	private City1 city;
	public Branch() {
 	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBranchNumber() {
		return branchNumber;
	}
	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Province1 getProvince() {
		return province;
	}
	public void setProvince(Province1 province) {
		this.province = province;
	}
	public City1 getCity() {
		return city;
	}
	public void setCity(City1 city) {
		this.city = city;
	}
}
