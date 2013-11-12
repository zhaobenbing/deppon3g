package com.deppon.common.beans;

public class Goods implements java.io.Serializable{
	private static final long serialVersionUID = 209390121729716224L;
	private long id;
	//货物名称
	private String name;
	//货物重量
	private String weight;
	//货物件数
	private int number;
	//保价声明
	private double supportValueApp;
	//货物体积
	private double volume;
	//货物包装类型
	private int packageType;
	public Goods() {
 	}
	public Goods(String name, String weight, int number,
			double supportValueApp, double volume, int packageType) {
 		this.name = name;
		this.weight = weight;
		this.number = number;
		this.supportValueApp = supportValueApp;
		this.volume = volume;
		this.packageType = packageType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getSupportValueApp() {
		return supportValueApp;
	}
	public void setSupportValueApp(double supportValueApp) {
		this.supportValueApp = supportValueApp;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public int getPackageType() {
		return packageType;
	}
	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(obj instanceof Goods){
			Goods gs = (Goods)obj;
			return gs.name.equals(this.name)&&gs.weight.equals(this.weight)&&gs.volume==this.volume;
		}else{
			return false;
		}
 	}
	@Override
	public int hashCode() {
 		return (int) (name.hashCode()+weight.hashCode()+volume+10);
	}
}
