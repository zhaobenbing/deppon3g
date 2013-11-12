package com.deppon.server.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Order;
import com.deppon.common.beans.Province;
import com.deppon.common.beans.User;
import com.deppon.server.services.impl.ServiceImpl;
import com.deppon.server.services.intfce.IService;
 
/**
 * @ 功能描述：server上下文，缓存省份，城市，营业部门等
 * @author ：赵本兵
 * @创建日期：2011-10-9
 */
public class ServerContext {
	//获取Service
 	//在线用户统计
	public static Map<UUID,User> onlineUsers;
	//缓存省份
	public static Map<String,Province> provinceCache;
	//缓存城市
	public static Map<String,City> cityCache;
	//缓存营业网点
	public static Map<String,Dept> deptCache;
	//缓存定单
	public static Map<Order,Date> orders;
	
	public static String DEPT_DATA_VERSION="1.0";
  	
	public static String PROGRAM_VERSION="1.0";
	public ServerContext() {
  	}
	public static void init() {
		IService service = new ServiceImpl();
		provinceCache = new HashMap<String, Province>();
		System.out.println("开始加载省份...\t["+new Date()+"]");
		Province[] pros = service.getDeptService().findAllProvice();
 		if(pros != null){
			System.out.println("省份"+pros.length+"个");
			for (int i = 0; i < pros.length; i++) {
				provinceCache.put(pros[i].getProvinceId(), pros[i]);
			}
		}
		System.out.println("加载省份结束!\t["+new Date()+"]");
		cityCache = new HashMap<String, City>();
		System.out.println("开始加载城市...\t["+new Date()+"]");
		City[] citys = service.getDeptService().findCity();
		if(citys != null){
			System.out.println("城市"+citys.length+"个");
			for (int i = 0; i < citys.length; i++) {
				cityCache.put(citys[i].getCityOldId(), citys[i]);
			}
		}
		/**
		System.out.println("加载城市结束!\t["+new Date()+"]");
		deptCache = new HashMap<String, Dept>();
		System.out.println("开始加载营业网点...\t["+new Date()+"]");
		Dept[] depts = service.getDeptService().findDept();
		if(depts != null){
			System.out.println("营业网点"+depts.length+"个");
 			for (int i = 0; i < depts.length; i++) {
				deptCache.put(""+i, depts[i]);
			}
		}
 		System.out.println("加载营业网点结束!\t["+new Date()+"]");
 		**/
	}
	
	//添加一个在线用户
	public synchronized static UUID addOnlineUser(User user){
		UUID uuid=null;
		boolean flag;
		do{
			uuid=UUID.randomUUID();
			flag=false;
			for(UUID tmpId:onlineUsers.keySet()){
				if(tmpId.equals(uuid)){
					flag=true;
					break;
				}
			}
		}while(flag);
		user.setLastActionTime(new Date());
		onlineUsers.put(uuid,user);
		return uuid;
	}
	public static Map<String, Dept> findDeptByCityId(String cityId) {
		if(cityId!=null&&cityId.length()>0){
		  IService service = new ServiceImpl(); 
		  cityId = service.getDeptService().convertCityCharId(cityId);
		  System.out.println("====CharcityId===="+cityId);
		  Dept[] depts = service.getDeptService().findDeptByCityId(cityId);
		  if(depts != null){
			  Map<String, Dept> map = new HashMap<String, Dept>();
			  for (int i = 0; i < depts.length; i++) {
				  Dept de = depts[i];
				  Dept d = new Dept();
				  d.setDeptid(de.getDeptid());
				  d.setDeptName(de.getDeptName());
				  d.setDeptPhone(de.getDeptPhone());
				  d.setDeptAddress(de.getDeptAddress());
				  d.setCityId(de.getCityId());
				map.put(depts[i].getDeptid(),d);
			  }
			  return map;
		  }
		}
 		return null;
 	}
	
	public static Map<String, Dept> findDeptByName(String deptName) {
		  IService service = new ServiceImpl(); 
		  System.out.println("=====request deptName===="+deptName);
 		  Dept[] depts = service.getDeptService().findDeptByName(deptName);
		  Map<String, Dept> map = new HashMap<String, Dept>();
		  if(depts != null){
			  for (int i = 0; i < depts.length; i++) {
					map.put(depts[i].getDeptid(),depts[i]);
			  }
		  }
 		return map;
	}
	//some User of Orders
	public static Map<String, Order> findAllOrders(String userId) {
		Map<String, Order> map = new HashMap<String, Order>();
		if (userId != null) {
			IService service = new ServiceImpl();
			Order[] orders = service.getOrderService().findSomeUserOrders(
					userId);
			if (orders != null) {
				for (int i = 0; i < orders.length; i++) {
					map.put(orders[i].getOrderNumber(), orders[i]);
				}
			}
		}
		return map;
	}
	//some User of status Orders
	public static Map<String, Order> findStatusOrders(String userId,int status) {
		Map<String, Order> map = new HashMap<String, Order>();
		if (userId != null) {
			IService service = new ServiceImpl();
			Order[] orders = service.getOrderService().findSomeUntrearedOrders(userId, status);
			if (orders != null) {
				for (int i = 0; i < orders.length; i++) {
					map.put(orders[i].getOrderNumber(), orders[i]);
				}
			}
		}
		return map;
	}
	public static Map<String, Order> findLastOrder(String userId, int start, int end) {
		Map<String, Order> map = new HashMap<String, Order>();
		if (userId != null) {
			IService service = new ServiceImpl();
			Order[] orders = service.getOrderService().findLastOrder(userId, start,end);
			if (orders != null) {
				for (int i = 0; i < orders.length; i++) {
					map.put(orders[i].getOrderNumber(), orders[i]);
				}
			}
		}
		return map;
	}
	public static Map<String, Order> findCancleOrders(String userId,int status) {
		Map<String, Order> map = new HashMap<String, Order>();
		if (userId != null) {
			IService service = new ServiceImpl();
			Order[] orders = service.getOrderService().findCancledOrder(userId, status);
			if (orders != null) {
				for (int i = 0; i < orders.length; i++) {
					map.put(orders[i].getOrderNumber(), orders[i]);
				}
			}
		}
		return map;
	}
}
