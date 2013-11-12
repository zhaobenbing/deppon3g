package com.deppon.common.beans;

import org.json.JSONObject;

public class TrackInfo implements java.io.Serializable{
	private static final long serialVersionUID = -2849835162468201671L;
	private String status;//运单状态
	private String statusDescription;//运单状态描述
	private String arrivecity;//目的城市
	private String traType;//承运方式
	private String takeWay;//提货方式
	private String goodsName;//货物名称
	private String goodsCount;//货物件数
	private String goodsWeight;//货物重量
	private String goodsVolume;//货物体积
	
	private String takeName;//提货点名称 
	private String takePerson;//提货人
	private String takePhone;//提货点电话
 	private String takeAddress;//提货点地址;
 	
 	private String storeDate;//揽货日期
	 public TrackInfo() {
 	}
	public TrackInfo(String status, String statusDescription,
			String arrivecity, String traType, String takeWay,
			String goodsName, String goodsCount, String goodsWeight,
			String goodsVolume, String takeName, String takePerson,
			String takePhone, String takeAddress) {
 		this.status = status;
		this.statusDescription = statusDescription;
		this.arrivecity = arrivecity;
		this.traType = traType;
		this.takeWay = takeWay;
		this.goodsName = goodsName;
		this.goodsCount = goodsCount;
		this.goodsWeight = goodsWeight;
		this.goodsVolume = goodsVolume;
		this.takeName = takeName;
		this.takePerson = takePerson;
		this.takePhone = takePhone;
		this.takeAddress = takeAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getArrivecity() {
		return arrivecity;
	}
	public void setArrivecity(String arrivecity) {
		this.arrivecity = arrivecity;
	}
	public String getTraType() {
		return traType;
	}
	public void setTraType(String traType) {
		this.traType = traType;
	}
	public String getTakeWay() {
		return takeWay;
	}
	public void setTakeWay(String takeWay) {
		this.takeWay = takeWay;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	public String getGoodsVolume() {
		return goodsVolume;
	}
	public void setGoodsVolume(String goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	public String getTakeName() {
		return takeName;
	}
	public void setTakeName(String takeName) {
		this.takeName = takeName;
	}
	public String getTakePerson() {
		return takePerson;
	}
	public void setTakePerson(String takePerson) {
		this.takePerson = takePerson;
	}
	public String getTakePhone() {
		return takePhone;
	}
	public void setTakePhone(String takePhone) {
		this.takePhone = takePhone;
	}
	public String getTakeAddress() {
		return takeAddress;
	}
	public void setTakeAddress(String takeAddress) {
		this.takeAddress = takeAddress;
	}
	
	public String getStoreDate() {
		return storeDate;
	}
	public void setStoreDate(String storeDate) {
		this.storeDate = storeDate;
	}
	@Override
	public String toString() {
		 JSONObject json = new JSONObject();
		 try {
			json.put("status", status);
			json.put("statusDescription", statusDescription);
			json.put("arrivecity", arrivecity);
			json.put("traType", traType);
			json.put("takeWay", takeWay);
			json.put("goodsName", goodsName);
			json.put("goodsCount", goodsCount);
			json.put("goodsWeight", goodsWeight);
			json.put("goodsVolume", goodsVolume);
			json.put("takeName", takeName);
			json.put("takePerson", takePerson);
			json.put("takePhone", takePhone);
			json.put("takeAddress", takeAddress);
			json.put("storeDate", storeDate);
			return json.toString();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return super.toString();
	}
}
