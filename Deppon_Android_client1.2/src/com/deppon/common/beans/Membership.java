package com.deppon.common.beans;

import java.util.Date;

/**
 * 会员类
 * @author Administrator
 *
 */
public class Membership implements java.io.Serializable{
	private static final long serialVersionUID = 5426951072318341657L;
	/**
	 * 会员编号
	 */
	private String id;	
	/**
	 * 会员姓名
	 */
	private String name;
	
	/**
	 * 会员密码
	 */
	private String password;
	/**
	 * 会员邮箱
	 */
	private String email;	
	/**
	 * 会员手机
	 */
	private String mobilePhone;	
	/**
	 * 会员电话
	 */
	private String telPhone;
	
	/**
	 * 会员省份id
	 */
	private String proId;
	/**
	 * 会员城市id
	 */
	private String cityId;
	/**
	 * 会员地址
	 */
	private String adress;
	/**
	 * 会员网上用户名
	 */
	private String username;
	
	/**
	 * 会员状态
	 */
	private Integer statues;
	
	
	/**
	 * 会员注册时间
	 */
	private Date memRigeterTime;
	
	/**
	 * 会员最后一次登录时间
	 */
	private Date memLastTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStatues() {
		return statues;
	}

	public void setStatues(Integer statues) {
		this.statues = statues;
	}

	public Date getMemRigeterTime() {
		return memRigeterTime;
	}

	public void setMemRigeterTime(Date memRigeterTime) {
		this.memRigeterTime = memRigeterTime;
	}

	public Date getMemLastTime() {
		return memLastTime;
	}

	public void setMemLastTime(Date memLastTime) {
		this.memLastTime = memLastTime;
	}
	
}
