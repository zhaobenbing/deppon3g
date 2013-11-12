package com.deppon.common.beans;

public class OrderItem implements java.io.Serializable{
	private static final long serialVersionUID = 5988380236788009188L;
	private long id;
	//货物名称信息
	private Goods goods;
	//收货人(等同于联系人)信息
	private Contact contact;
	//发货人信息
	private Consignor consignor;
	//受理网点
	private Branch branch;
	//总费用
	private double totalPay;
	public OrderItem() {
 	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Consignor getConsignor() {
		return consignor;
	}
	public void setConsignor(Consignor consignor) {
		this.consignor = consignor;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	
}
