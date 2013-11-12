package com.deppon.common.beans;

import java.util.ArrayList;

/**
 * @功能描述：各种运输类型
 * @author 赵本兵
 * @创建日期：2011-10-20
 */
public class TransPropertyEnum implements java.io.Serializable{
	private static final long serialVersionUID = 8951439654982723947L;
	private static String airExpressName = "精准空运";
	private static int airExpressValue = 1;

	private static String autoLongTransName = "精准汽运";
	private static int autoLongTransValue = 2;

	private static String vehicleTransName = "精准卡航";
	private static int vehicleTransValue = 3;

	private static String cityTransName = "精准城运";
	private static int cityTransValue = 4;

	private static String agentTransName = "汽运偏线";
	private static int agentTransValue = 5;

	private static String autoShortTransName = "精准汽运(短)";
	private static int autoShortTransValue = 6;

	private String enumName;

	public String EnumName;
	private int enumValue;

	public int EnumValue;

	public TransPropertyEnum() {
	}

	public TransPropertyEnum(String enumName, int enumValue) {
		super();
		this.enumName = enumName;
		this.enumValue = enumValue;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public int getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(int enumValue) {
		this.enumValue = enumValue;
	}

	public static TransPropertyEnum airExpress = new TransPropertyEnum(
			airExpressName, airExpressValue);
	public static TransPropertyEnum autoLongTrans = new TransPropertyEnum(
			autoLongTransName, autoLongTransValue);
	public static TransPropertyEnum vehicleTrans = new TransPropertyEnum(
			vehicleTransName, vehicleTransValue);
	public static TransPropertyEnum cityTrans = new TransPropertyEnum(
			cityTransName, cityTransValue);
	public static TransPropertyEnum agentTrans = new TransPropertyEnum(
			agentTransName, agentTransValue);
	public static TransPropertyEnum autoShortTrans = new TransPropertyEnum(
			autoShortTransName, autoShortTransValue);

	/**
	 * 根据值获取对象
	 * 
	 * @param value
	 * @return TransPropertyEnum
	 */
	public static TransPropertyEnum getEnum(int value) {
		switch (value) {
		case 0:
		case 1:
			return airExpress;

		case 2:
			return autoLongTrans;

		case 3:
			return vehicleTrans;
		case 4:
			return cityTrans;

		case 5:
			return agentTrans;

		case 6:
			return autoShortTrans;
		default:
			return null;
		}
	}

	public static TransPropertyEnum getEnum(String value) {
		return getEnum(Integer.parseInt(value));
	}

	/**
	 * 获取所有枚举
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getEnum() {
		ArrayList enumList = new ArrayList();
		enumList.add(airExpress);
		enumList.add(autoLongTrans);
		enumList.add(vehicleTrans);
		enumList.add(cityTrans);
		enumList.add(agentTrans);
		enumList.add(autoShortTrans);
		return enumList;
	}
}
