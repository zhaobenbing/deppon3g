package com.deppon.common.beans;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
/**
 * 用户信息实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-7-28
 */
public class User implements Serializable{
	private static final long serialVersionUID = 4483791203075004747L;
	//用户id
    private int id;
	//登录名
	private String loginName;
	//密码
	private String password;
	//电子邮件
	private String email;
	//姓名
	private String name;
	//性别
	private String  sex;
	//地址
	private String address;
	//手机
	private String telephone;
	//固定电话
	private String mobilephone;
	//审核状态
	private int slstatus;
	//最后登录时间
	private Date lastUpdatetime;
	//注册时间
	private Date regiterTime;
	//邮编
    private String postCode;
    //省份
    private String province;
    //城市
	private String city;
	//最后一次提交请求时间
	private Date lastActionTime;
	 
	public User() {
 	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public int getSlstatus() {
		return slstatus;
	}
	public void setSlstatus(int slstatus) {
		this.slstatus = slstatus;
	}
	public Date getLastUpdatetime() {
		return lastUpdatetime;
	}
	public void setLastUpdatetime(Date lastUpdatetime) {
		this.lastUpdatetime = lastUpdatetime;
	}
	public Date getRegiterTime() {
		return regiterTime;
	}
	public void setRegiterTime(Date regiterTime) {
		this.regiterTime = regiterTime;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	public Date getLastActionTime() {
		return lastActionTime;
	}
	public void setLastActionTime(Date lastActionTime) {
		this.lastActionTime = lastActionTime;
	}
 
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(obj instanceof User){
			User user = (User)obj;
			return user.name.equals(this.name)&&user.email.equals(this.email)&&user.password.equals(this.password);
		}else {
			return false;
		}
  	}
	@Override
	public int hashCode() {
 		return name.hashCode()+password.hashCode()+email.hashCode()+10;
	}
	@Override
	public String toString() {
		JSONObject json = new  JSONObject();
		try {
			json.put("id", id);
			json.put("loginName", loginName);
			json.put("password", password);
			json.put("email", email);
			json.put("name", name);
			json.put("sex", sex);
			json.put("address", address);
			json.put("telephone", telephone);
			json.put("mobilephone", mobilephone);
			json.put("slstatus", slstatus);
			json.put("lastUpdatetime", lastUpdatetime);
			json.put("regiterTime", regiterTime);
			json.put("postCode", postCode);
			json.put("province", province);
			json.put("city", city);
			json.put("lastActionTime", lastActionTime);
			return json.toString();
		} catch (JSONException e) {
 			e.printStackTrace();
 			return null;
		}
		/**
 		return "[{"+"\"id:\""+id+","+"\"loginName:\""+loginName+","+
 		"\"password:\""+password+","+
 		"\"email:\""+email+","+
 		"\"name:\""+name+","+
 		"\"sex:\""+sex+","+
 		"\"address:\""+address+","+
 		"\"telephone:\""+telephone+","+
 		"\"mobilephone:\""+mobilephone+","+
 		"\"slstatus:\""+slstatus+","+
 		"\"lastUpdatetime:\""+lastUpdatetime+","+
 		"\"regiterTime:\""+regiterTime+","+
 		"\"postCode:\""+postCode+","+
 		"\"province:\""+province+","+
 		"\"city:\""+city+","+
 		"\"lastActionTime:\""+lastActionTime+","
 		+"}]";
 		**/
	}
}
