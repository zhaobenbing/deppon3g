package com.deppon.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Order;
import com.deppon.common.beans.PriceBean;
import com.deppon.common.beans.Province;
import com.deppon.common.beans.TrackInfo;
import com.deppon.common.beans.TransPropertyEnum;

/**
 * @功能描述：解析服务器返回的省份，城市，部门缓存数据，
 * @author 赵本兵
 * @创建日期：2011-10-8
 */
public class ParserData {
	private static int  LENGTH = 10;
	// 解析省份
	@SuppressWarnings("unchecked")
	public static Map<String, Province> parseProvinces(String jsonString) {
		Map<String, Province> map = new HashMap<String, Province>();
		JSONObject json;
		Province pro;
		try {
 			json = new JSONObject(jsonString);
			Iterator it = json.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONObject p = new JSONObject(json.get(key).toString());
				pro = new Province();
				pro.setId(p.getString("id"));
				pro.setProvinceId(p.getString("provinceId"));
				pro.setProvinceName(p.getString("provinceName"));
				pro.setFindex(p.getString("findex"));
				pro.setProvinceOldId(p.getString("provinceOldId"));
				pro.setProvinceOldName(p.getString("provinceOldName"));
				map.put(pro.getProvinceId(), pro);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	//解析城市
	@SuppressWarnings("unchecked")
	public static Map<String, City> parseCitys(String jsonString) {
		Map<String, City> map = new HashMap<String, City>();
		JSONObject json;
		City city;
		try {
 			json = new JSONObject(jsonString);
			Iterator it = json.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONObject p = new JSONObject(json.get(key).toString());
				city = new City();
				city.setId(p.getString("id"));
				city.setCityId(p.getString("cityId"));
				city.setCityName(p.getString("cityName"));
				city.setFather(p.getString("father"));
				city.setCityOldId(p.getString("cityOldId"));
				city.setCityOldName(p.getString("cityOldName"));
				city.setOldFather(p.getString("oldFather"));
				map.put(city.getCityId(), city);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	//解析营业网点
	@SuppressWarnings("unchecked")
	public static Map<String, Dept> parseDepts(String jsonString) {
		Map<String, Dept> map = new HashMap<String, Dept>();
		JSONObject obj;
		Dept dept;
		try {
			System.out.println("=========parseDepts======="+jsonString.length());
			obj = new JSONObject(jsonString);
			Iterator it = obj.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONObject json = new JSONObject(obj.get(key).toString());
				dept = new Dept();
				dept.setDeptid(json.getString("deptid"));
				dept.setDeptName(json.getString("deptName"));
				dept.setDeptAddress(json.getString("deptAddress"));
				dept.setDeptPhone(json.getString("deptPhone"));
				dept.setProvinceId(json.getString("provinceId"));
				dept.setCityId(json.getString("cityId"));
				dept.setOldCityId(json.getString("oldCityId"));
				dept.setOldProvinceId(json.getString("oldProvinceId"));
				dept.setIsStart(Integer.parseInt(json.getString("isStart")));
 				map.put(dept.getDeptid(), dept);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
 	}
 	/**
	 * 解析json对象为Map集合，服务器返回的是一个Map
	 */
	@SuppressWarnings("unchecked")
	public static Map parserToMap(String s){
		Map map=new HashMap();
		JSONObject json = null;
		try {
			json = new JSONObject(s);
			Iterator keys=json.keys();
			while(keys.hasNext()){
				String key=(String) keys.next();
				String value=json.get(key).toString();
				if(value.startsWith("{")&&value.endsWith("}")){
					map.put(key, parserToMap(value));
				}else{
					map.put(key, value);
				}

			}
		} catch (JSONException e) {
 			e.printStackTrace();
		}
		return map;
	}
	public static Dept[] parseDeptsName(String deptsName) {
 		String[] depts = deptsName.toString().split("$");
 		
		if(depts != null){
			Dept[] array = new Dept[depts.length];
			for (int i = 0; i < depts.length; i++) {
 			}
			return array;
		}
  		return null;
	}
	
	//关键字解析
	public static String[] parseKeyWords(){
 		int count=0;
 		StringBuilder sb = new StringBuilder();
 		String[] data = new String[LENGTH];
		FileReader fr = null;
 		BufferedReader br = null ;
		try {
			fr = new FileReader("/mnt/sdcard/deptNametext.txt");
 			br = new BufferedReader(fr,1000);
			String str = "";
			w :while((str = br.readLine())!=null){
				if(count==LENGTH){
					LENGTH  = LENGTH*2;
 				}
				if(str==null||"".equals(str)){
					continue w ;
				}
				count++;
				sb.append(str.trim()).append("#");
 			}
			sb.deleteCharAt(sb.length()-1);
 			data = sb.toString().trim().split("#");
			System.out.println("========keywords length===="+data.length);
			return data;
		} catch (Exception e) {
 			e.printStackTrace();
		}finally{
		
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 		return null;
	}
	//解析货物跟踪基本信息
	public static TrackInfo parseTrackInfo(String js){
		TrackInfo track = new TrackInfo();
		try {
			JSONObject json = new JSONObject(js);
			track.setStatus(json.getString("status"));
			track.setStatusDescription(json.getString("statusDescription"));
			track.setArrivecity(json.getString("arrivecity"));
			track.setTraType(json.getString("traType"));
			track.setTakeWay(json.getString("takeWay"));
			track.setGoodsName(json.getString("goodsName"));
			track.setGoodsCount(json.getString("goodsCount"));
			track.setGoodsWeight(json.getString("goodsWeight"));
			track.setGoodsVolume(json.getString("goodsVolume"));
			//到达信息
			track.setTakeName(json.getString("takeName"));
			track.setTakePerson(json.getString("takePerson"));
			track.setTakePhone(json.getString("takePhone"));
			track.setTakeAddress(json.getString("takeAddress"));
			return track;
		} catch (JSONException e) {
 			e.printStackTrace();
		}
		return null;
	}
	//解析货物跟踪详细信息
	public static List<Map<String,String>> parseTrackDetail(String str){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
 		String[] data = str.split("@");
		if(data.length>0){
			for (int i = 0; i < data.length; i++) {
				if(data[i] != null){
					map = new HashMap<String,String>();
					String[] temp = data[i].split("\\s+");
					map.put("trackDate", temp[0]);
					map.put("trackType", temp[1]);
					map.put("trackInfo", temp[2]);
					list.add(map);
				}				
			}
			return list;
		}
		return null;
 	}
	//解析价格时效查询
	public static PriceBean parsePriceSearch(String str){
		PriceBean pb = new PriceBean();
		try {
			JSONObject json = new JSONObject(str);
			TransPropertyEnum tran = new TransPropertyEnum();
			String tpe = json.getString("transProperty");
			if(tpe!=null){
				JSONObject js = new JSONObject(tpe);
				tran.setEnumName(js.getString("enumName"));
				tran.setEnumValue(Integer.parseInt(js.getString("enumValue")));
			}
			pb.setTransProperty(tran);
			pb.setLightPrice(Double.parseDouble(json.getString("lightPrice")));
			pb.setWeightPrice(Double.parseDouble(json.getString("weightPrice")));
			pb.setMinPrice(Double.parseDouble(json.getString("minPrice")));
			pb.setWorkTime(json.getString("workTime")==null?null:json.getString("workTime"));//5
			
			pb.setWeightGoodFlag(Integer.parseInt(json.getString("weightGoodFlag")));
			pb.setTotalAmount(Double.parseDouble(json.getString("totalAmount")));
			pb.setMorning(Double.parseDouble(json.getString("morning")));
			pb.setNoon(Double.parseDouble(json.getString("noon")));
			pb.setNight(Double.parseDouble(json.getString("night")));//10
			if("".equals(json.getString("schedule"))||json.getString("schedule")==null){
				pb.setSchedule("");
			}else{
				pb.setSchedule(json.getString("schedule"));
			}
			if("".equals(json.getString("airPriceRemark"))||json.getString("airPriceRemark")==null){
				pb.setAirPriceRemark("");
			}else{
				pb.setAirPriceRemark(json.getString("airPriceRemark"));//12				
			}
			pb.setAirwayMorningPrice(Double.parseDouble(json.getString("airwayMorningPrice")));
			pb.setAirwayNoonPrice(Double.parseDouble(json.getString("airwayNoonPrice")));
			pb.setAirwayNightPrice(Double.parseDouble(json.getString("airwayNightPrice")));//15
			
			pb.setArrDates(Integer.parseInt(json.getString("arrDates")));
			pb.setArrTimePoint(json.getString("arrTimePoint")=="0"?"":json.getString("arrTimePoint"));
			pb.setSendDates(Integer.parseInt(json.getString("sendDates")));
			pb.setSendTimePoint(json.getString("sendTimePoint")=="0"?"":json.getString("sendTimePoint"));
			String gfh = json.getString("isGetFromHome")=="0"?0+"":json.getString("isGetFromHome");
			pb.setIsGetFromHome(Integer.parseInt(gfh));//20
			String pmp = json.getString("pickUpMinPrice")=="0"?0+"":json.getString("pickUpMinPrice");
			pb.setPickUpMinPrice(Double.parseDouble(pmp));
			return pb;
		} catch (JSONException e) {
 			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static Map<String, Order> parseOrder(String result) {
		Map<String, Order> map = new HashMap<String, Order>();
		JSONObject json;
		Order order;
		try {
 			json = new JSONObject(result);
			Iterator it = json.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONObject p = new JSONObject(json.get(key).toString());
				order = new Order();
				order.setOrderNumber(p.getString("orderNumber"));
				order.setDeptId(p.getString("deptId"));
				order.setGoodsName(p.getString("goodsName"));
				order.setState(Integer.parseInt(p.getString("state")));
				order.setUserId(p.getString("userId"));
				 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				JSONObject jsonObject = new JSONObject(p.getString("orderDate"));
				String year = (1900+Integer.parseInt(jsonObject.getString("year")))+"";
				String month = (1+Integer.parseInt(jsonObject.getString("month")))+"";
				String date = jsonObject.getString("date");
				String hour = jsonObject.getString("hours");
				String minutes = jsonObject.getString("minutes");
				String seconds = jsonObject.getString("seconds");
				String tempString = year+"-"+month+"-"+date+" " +hour+":"+minutes+":"+seconds;
				try {
					order.setOrderDate(sdf.parse(tempString));
 				} catch (ParseException e) {
 					e.printStackTrace();
				}
				map.put(key,order);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
}
