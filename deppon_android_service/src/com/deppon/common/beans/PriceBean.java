package com.deppon.common.beans;

import net.sf.json.JSONObject;

/**
 * @功能描述：价格信息，为了封装价格信息
 * @author 赵本兵
 * @创建日期：2011-10-20
 */
public class PriceBean implements java.io.Serializable{
	private static final long serialVersionUID = -1160193107016863499L;
	 private TransPropertyEnum transProperty; //运输类型
     private double lightPrice; //轻货价
     private double weightPrice; //重货价
     private double minPrice; //最低一票
     private String workTime; //运行时效
     private int weightGoodFlag; //重货标志
     private double totalAmount; //运费
     private double morning; //空运：早班
     private double noon; //空运：中班
     private double night; //空运：晚班
     private String schedule; //空运：航班班期
     private String airPriceRemark; //空运价格信息
     //**************************************************
     private double airwayMorningPrice; //空运早班价格 
     private double airwayNoonPrice; //空运中班价格
     private double airwayNightPrice; //空运晚班价格
     //****************************************************
     private int arrDates;//到达营业部所需天数
     private String arrTimePoint;//到达营业部时间点
     private int sendDates;//派送天数
     private String sendTimePoint;//派送时间点
     private int isGetFromHome;//是否上门接货
     private double pickUpMinPrice; //最低一票
	public TransPropertyEnum getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(TransPropertyEnum transProperty) {
		this.transProperty = transProperty;
	}
	public double getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(double lightPrice) {
		this.lightPrice = lightPrice;
	}
	public double getWeightPrice() {
		return weightPrice;
	}
	public void setWeightPrice(double weightPrice) {
		this.weightPrice = weightPrice;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public int getWeightGoodFlag() {
		return weightGoodFlag;
	}
	public void setWeightGoodFlag(int weightGoodFlag) {
		this.weightGoodFlag = weightGoodFlag;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getMorning() {
		return morning;
	}
	public void setMorning(double morning) {
		this.morning = morning;
	}
	public double getNoon() {
		return noon;
	}
	public void setNoon(double noon) {
		this.noon = noon;
	}
	public double getNight() {
		return night;
	}
	public void setNight(double night) {
		this.night = night;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getAirPriceRemark() {
		return airPriceRemark;
	}
	public void setAirPriceRemark(String airPriceRemark) {
		this.airPriceRemark = airPriceRemark;
	}
	public double getAirwayMorningPrice() {
		return airwayMorningPrice;
	}
	public void setAirwayMorningPrice(double airwayMorningPrice) {
		this.airwayMorningPrice = airwayMorningPrice;
	}
	public double getAirwayNoonPrice() {
		return airwayNoonPrice;
	}
	public void setAirwayNoonPrice(double airwayNoonPrice) {
		this.airwayNoonPrice = airwayNoonPrice;
	}
	public double getAirwayNightPrice() {
		return airwayNightPrice;
	}
	public void setAirwayNightPrice(double airwayNightPrice) {
		this.airwayNightPrice = airwayNightPrice;
	}
	public int getArrDates() {
		return arrDates;
	}
	public void setArrDates(int arrDates) {
		this.arrDates = arrDates;
	}
	public String getArrTimePoint() {
		return arrTimePoint;
	}
	public void setArrTimePoint(String arrTimePoint) {
		this.arrTimePoint = arrTimePoint;
	}
	public int getSendDates() {
		return sendDates;
	}
	public void setSendDates(int sendDates) {
		this.sendDates = sendDates;
	}
	public String getSendTimePoint() {
		return sendTimePoint;
	}
	public void setSendTimePoint(String sendTimePoint) {
		this.sendTimePoint = sendTimePoint;
	}
	public int getIsGetFromHome() {
		return isGetFromHome;
	}
	public void setIsGetFromHome(int isGetFromHome) {
		this.isGetFromHome = isGetFromHome;
	}
	public double getPickUpMinPrice() {
		return pickUpMinPrice;
	}
	public void setPickUpMinPrice(double pickUpMinPrice) {
		this.pickUpMinPrice = pickUpMinPrice;
	}
     @Override
    public String toString() {
    	 JSONObject json = new JSONObject();
    	 try {
			json.put("transProperty", transProperty);//1
			 json.put("lightPrice", lightPrice);//2
			 json.put("weightPrice", weightPrice);
			 json.put("minPrice", minPrice);
			 json.put("workTime", workTime);//5
			 json.put("weightGoodFlag", weightGoodFlag);
			 json.put("totalAmount", totalAmount);
			 json.put("morning", morning);
			 json.put("noon", noon);
			 json.put("night", night);//10
			 json.put("schedule", schedule);
			 json.put("airPriceRemark", airPriceRemark);//12
			 
			 json.put("airwayMorningPrice", airwayMorningPrice);
			 json.put("airwayNoonPrice", airwayNoonPrice);
			 json.put("airwayNightPrice", airwayNightPrice);//15
			 json.put("arrDates", arrDates);
			 json.put("arrTimePoint", arrTimePoint);
			 json.put("sendDates", sendDates);
			 json.put("sendTimePoint", sendTimePoint);
			 json.put("isGetFromHome", isGetFromHome);//20
			 json.put("pickUpMinPrice", pickUpMinPrice);//21
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return json.toString();
    }
}
