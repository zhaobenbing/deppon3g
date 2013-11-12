package com.deppon.common.beans;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 部门实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-10-8
 * @修改：新增2个属性 所属old省份  所属old城市
 * @修改日期：2011-10-21
 */
public class Dept extends AbstractBean {
	private static final long serialVersionUID = 1L;
	// 部门Id
	private String deptid;
	// 部门名称
	private String deptName;
	// 部门创建时间
	private String createTime;
	// 最后更新日期
	private Date lastUpdateTime;
	// 部门地址
	private String deptAddress;
	// 部门电话
	private String deptPhone;
	// 所属省份
	private String provinceId;
	// 所属城市
	private String oldCityId;
	// 所属old省份
	private String oldProvinceId;
	// 所属old城市
	private String cityId;
	// 是否开业
	private int isStart;

	public Dept() {
	}
	public Dept(String deptid, String deptName, String createTime,
			Date lastUpdateTime, String deptAddress, String deptPhone,
			String provinceId, String oldCityId, String oldProvinceId,
			String cityId, int isStart) {
		super();
		this.deptid = deptid;
		this.deptName = deptName;
		this.createTime = createTime;
		this.lastUpdateTime = lastUpdateTime;
		this.deptAddress = deptAddress;
		this.deptPhone = deptPhone;
		this.provinceId = provinceId;
		this.oldCityId = oldCityId;
		this.oldProvinceId = oldProvinceId;
		this.cityId = cityId;
		this.isStart = isStart;
	}
	public String getOldCityId() {
		return oldCityId;
	}
	public void setOldCityId(String oldCityId) {
		this.oldCityId = oldCityId;
	}
	public String getOldProvinceId() {
		return oldProvinceId;
	}
	public void setOldProvinceId(String oldProvinceId) {
		this.oldProvinceId = oldProvinceId;
	}
	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getIsStart() {
		return isStart;
	}

	public void setIsStart(int isStart) {
		this.isStart = isStart;
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
			Dept dept = (Dept)obj;
			return dept.deptid.equals(this.deptid)&&dept.cityId.equals(this.cityId)&&dept.provinceId.equals(this.provinceId);
		}else {
			return false;
		}
  	}
	@Override
	public int hashCode() {
 		return deptid.hashCode()+10;
	}
	@Override
	public String toString() {
		JSONObject json = new  JSONObject();
		try {
			json.put("id", getId());
			json.put("deptid", deptid);
			json.put("deptName", deptName);
			json.put("createTime", createTime);
			json.put("lastUpdateTime", lastUpdateTime);
			json.put("deptAddress", deptAddress);
			json.put("deptPhone", deptPhone);
			json.put("deptAddress", deptAddress);
			json.put("deptPhone", deptPhone);
			json.put("provinceId", provinceId);
			json.put("cityId", cityId);
			json.put("oldProvinceId", oldProvinceId);
			json.put("oldCityId", oldCityId);		
			json.put("isStart", isStart);
			return json.toString();
		} catch (JSONException e) {
 			e.printStackTrace();
 			return null;
		}
	}
}
