package com.deppon.common.beans;

import java.util.Date;

/**
 * ��Ա��
 * @author Administrator
 *
 */
public class Membership implements java.io.Serializable{
	private static final long serialVersionUID = 5426951072318341657L;
	/**
	 * ��Ա���
	 */
	private String id;	
	/**
	 * ��Ա����
	 */
	private String name;
	
	/**
	 * ��Ա����
	 */
	private String password;
	/**
	 * ��Ա����
	 */
	private String email;	
	/**
	 * ��Ա�ֻ�
	 */
	private String mobilePhone;	
	/**
	 * ��Ա�绰
	 */
	private String telPhone;
	
	/**
	 * ��Աʡ��id
	 */
	private String proId;
	/**
	 * ��Ա����id
	 */
	private String cityId;
	/**
	 * ��Ա��ַ
	 */
	private String adress;
	/**
	 * ��Ա�����û���
	 */
	private String username;
	
	/**
	 * ��Ա״̬
	 */
	private Integer statues;
	
	
	/**
	 * ��Աע��ʱ��
	 */
	private Date memRigeterTime;
	
	/**
	 * ��Ա���һ�ε�¼ʱ��
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
