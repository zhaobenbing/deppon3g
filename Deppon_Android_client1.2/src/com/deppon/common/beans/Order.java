package com.deppon.common.beans;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * @订单信息实体类
 * @author  ：赵本兵
 * @ 创建时间：2011-10-13
 */
public class Order implements Serializable{
	private static final long serialVersionUID = -6401498907725989280L;
	//与业务无关id
    private int id;
	//订单编号
	private String orderNumber;
	//订单所属用户
	private String userId;
	//下单时间
	private Date orderDate; 
	//货物名称信息
	private String goodsName;
	//订单状态 1 受理 0 未受理
	private int state;
	//受理网点
	private String deptId;
	public Order() {
 	}
 	public Order(String orderNumber, String userId, Date orderDate,
			String goodsName, int state, String deptId) {
		super();
		this.orderNumber = orderNumber;
		this.userId = userId;
		this.orderDate = orderDate;
		this.goodsName = goodsName;
		this.state = state;
		this.deptId = deptId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(obj instanceof Order){
			Order order = (Order)obj;
			return order.getOrderNumber().equals(orderNumber);
		}else {
			return false;
		}
  	}
	@Override
	public int hashCode() {
 		return orderNumber.hashCode()+100;
	}
	@Override
	public String toString() {
		JSONObject json = new  JSONObject();
		try {
 			json.put("orderNumber", orderNumber);
			json.put("userId", userId);
			json.put("orderDate", orderDate);
			json.put("goodsName", goodsName);
			json.put("state", state);
			json.put("deptId", deptId);
			return json.toString();
		} catch (JSONException e) {
 			e.printStackTrace();
 			return null;
		}
	}
}
