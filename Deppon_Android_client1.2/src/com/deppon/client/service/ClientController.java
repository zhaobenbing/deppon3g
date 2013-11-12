package com.deppon.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.Log;

import com.deppon.client.ui.P02_LoginAct;
import com.deppon.client.ui.P03_RegistAct;
import com.deppon.client.ui.P05_NowOrderAct;
import com.deppon.client.ui.P07_PersonDatum;
import com.deppon.client.ui.P08_ContactAct;
import com.deppon.client.ui.P09_ModifyPwdAct;
import com.deppon.client.ui.P20_OrdMagUIAct;

/**
 * 该类保存客户端缓存数据,并进行Activity之间的转换
 * @author Administrator：赵本兵
 * @ 创建时间 ：2011-7-29
 */
public class ClientController {
	// 客户端上下文，该对象用来缓存客户端业务数据及配置参数
	private ClientContext context;
	// 控制器单例对象
	private static ClientController controller = null;
	// 服务对象
	private ClientService service;
	//当前Android活动对象（页面Activity）
	private Activity currentActivity;
	
	private ClientController(Activity act) {
		this.currentActivity = act;
		createContext();
		service = new ClientServiceImplForNet(context);
	}
	
	/**
	 * 该方法用来创建ClientContext实例，该方法在ClientController类的构造方法中调用。
	 */
	private void createContext() {
		context=new ClientContext();
		context.setProperties(getProperties());
	}

	
	private Properties getProperties() {
		 Properties pro = new Properties();
		 AssetManager am = currentActivity.getAssets();
		 try {
			InputStream is = am.open("config/client_config.properties");
			pro.load(is);
		} catch (IOException e) {
 			e.printStackTrace();
		}
		 return pro;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	/**
	 * 初始化方法，完成客户端程序的初始化操作,此方法在P01_WelcomeAct页面的onCreate()方法中被调用。
	 */
	public void init(Handler handler) {
		ClientInitialization ci = new ClientInitialization(context,
				currentActivity, handler);
		//初始化服务器返回的省份，城市，部门数据
		try {
			System.out.println("==========init native begin============");
 			if(service.initData()){
				System.out.println("==========init return data success============");
				ci.init();
				System.out.println("==========init native end============");
			}else{
				System.out.println("==========init return data fail============");
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
		}
		
	}

	/**
	 * 得到单例的ClientController对象
	 * 
	 * @return
	 */
	public synchronized static ClientController getController() {
		if (controller == null) {
			controller = new ClientController(null);
		}
		return controller;
	}
	/**
	 * 获取context
	 * @return 返回context
	 */
	public ClientContext getContext(){
		return context;
	}
	/**
	 * 得到单例的ClientController对象，并设置ClientController当前关联的Activity对象。
	 * 
	 * @param act:当前activity
	 * @return
	 */
	public synchronized static ClientController getController(Activity act) {
		if (controller == null) {
			controller = new ClientController(act);
			Log.i("myDebug", "new ClientController");
		} else {
			controller.setCurrentActivity(act);
		}
		return controller;
	}
	/**
	 * 登录页面--登录按钮
	 */
	public boolean k0201() {
		try {
 			if(service.login()){
 				String modifyPwdFlag = (String) context.getBusinessData("modifyPwdFlag");
 				String contactFlag = (String) context.getBusinessData("contactFlag");
 				String personDatumFlag = (String) context.getBusinessData("personDatumFlag");
 				String ordMagFlag = (String) context.getBusinessData("ordMagFlag");
 				//跳转到个人资料界面 
 				if(personDatumFlag!=null&&personDatumFlag.equals("personDatumFlag")){
 					Intent intent = new Intent(currentActivity,P07_PersonDatum.class);
 					currentActivity.startActivity(intent);
 					currentActivity.finish();
 					return true;
 				}
 				//跳转到联系人界面 
 				if(contactFlag!=null&&contactFlag.equals("contactFlag")){
 					Intent intent = new Intent(currentActivity,P08_ContactAct.class);
 					currentActivity.startActivity(intent);
 					currentActivity.finish();
 					return true;
 				}
 				//跳转到修改密码界面
 				if(modifyPwdFlag!=null&&modifyPwdFlag.equals("modifyPwdFlag")){
 					Intent intent = new Intent(currentActivity,P09_ModifyPwdAct.class);
 					currentActivity.startActivity(intent);
 					currentActivity.finish();
 					return true;
 				}
 				//跳转到订单管理界面
 				if(ordMagFlag!=null&&ordMagFlag.equals("ordMagFlag")){
 					Intent intent = new Intent(currentActivity,P20_OrdMagUIAct.class);
 					currentActivity.startActivity(intent);
 					currentActivity.finish();
 					return true;
 				}
 				else{
 					//否则，跳转到现在下单界面
 					Intent intent = new Intent(currentActivity,P05_NowOrderAct.class);
 					currentActivity.startActivity(intent);
 					currentActivity.finish();
 					return true;
 				}
			} else {
				//停留在登录界面 
				Intent intent = new Intent(currentActivity,P02_LoginAct.class);
				currentActivity.startActivity(intent);
				currentActivity.finish();
				return false;
 			}	
 		} catch (Exception e) {
 			e.printStackTrace();
		}finally{
			//从栈里面弹出当前activity
			currentActivity.finish();
		}
		return false;
	}
	/**
	 * 登录页面--注册按钮
	 */
	public void k0202() {
		//跳转到注册界面
		Intent intent = new Intent(currentActivity,P03_RegistAct.class);
		currentActivity.startActivity(intent);
		//从栈里面弹出当前activity
		currentActivity.finish();
	}
	/**
	 *  登录页面--退出按钮
	 */
	public void k0203() {
		controller.goBack();
	}
	/**
	 * 注册页面--注册按钮
	 */
	public boolean k0301() {
		try {
			System.out.println("start regist");
			boolean isRegistSu = service.regist();
			System.out.println("end regist");
			if(isRegistSu){
				Intent intent = new Intent(currentActivity,P02_LoginAct.class);
				currentActivity.startActivity(intent);
				//从栈里面弹出当前activity
				currentActivity.finish();
				return true;
			}
 		} catch (Exception e) {
 			e.printStackTrace();
		}
 		return false;
	}
	/**
	 * 注册页面--退出按钮
	 */
	public void k0302() {
		currentActivity.finish();
	}
	
  /**
   * 主页面--下单
   */
	public void k0401() {
		
	}
  /**
   * 主页面--订单管理
   */
	public void k0402() {
		
	}
  /**
   * 主页面-用户管理
   */
	public void k0403() {
		
	}
  /**
   * 主页面--网点查询
   */
	public void k0404() {
		
	}
	
	/**
	 * 下单界面
	 * 根据城市Id查找网点请求
	 */
	public void k0501(){
		try {
			service.getDeptsByCityId();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	/**
	 * 下单界面
	 * 根据部门名称查找部门信息请求
	 */
	public void k0502(){
		try {
			service.getDeptsInfo();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	/**
	 * 下单界面
	 * 下单请求
	 */
	public boolean k0503(){
		try {
			return service.sendOrder();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 个人资料界面 ，保存个人资料
	 */
	public boolean k0701(){
		try {
			return service.saveBaseInfo();
		} catch (Exception e) {
 		}
		return false;
	}
	/**
	 * 自助服务界面
	 * 网点查询请求
	 */
	public boolean k1001(){
		try {
			return service.findBranch();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return false;
 	}
	/**
	 * 自助服务界面
	 * 货物跟踪请求
	 */
	public boolean k1002(){
		try {
			return service.findWayBill();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return false;
 	}
	/**
	 * 自助服务界面
	 * 价格时效请求
	 */
	public boolean k1401(){
		try {
			return service.priceSearch();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return false;
 	}
	/**
	 * 订单管理--全部
	 */
	public boolean k2001(){
		try {
			return service.findOrders();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * 订单管理--未受理
	 */
	public boolean k2002(){
		try {
			return service.untreatedOrders();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * 订单管理--已受理
	 */
	public boolean k2003(){
		try {
			return service.treatedOrders();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * 订单管理--最近一笔订单
	 */
	public boolean k2004(){
		try {
			return service.lastOrder();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * 订单管理--取消订单
	 */
	public boolean k2005(){
		try {
			return service.cancleOrder();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * 订单管理--查询订单
	 */
	public boolean k2006(){
		try {
			return service.findCancledOrder();
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return false;
	}
	/**
	 * "退出"菜单点击后的操作
	 */
	public void exit() {
		try {
			service.exit();
		} catch (Exception e) {
			
		}
	}
	/**
	 * 菜单-返回按钮
	 */
	@SuppressWarnings("unchecked")
	public void goBack(){	
		LinkedList<Activity> link = (LinkedList<Activity>) context.getBusinessData("Activitys");
		link.getLast().finish();
		System.out.println("++activitys before++"+link.size()+"===activity name==="+link.getLast().getLocalClassName());
		link.removeLast();
 		System.out.println("++activitys after++"+link.size()+"===activity name==="+link.getLast().getLocalClassName());
	}
	
	
	/**
	 * 返回主界面，产品业务
	 */
	@SuppressWarnings("unchecked")
	public void goToMain(){
		LinkedList<Activity> link = (LinkedList<Activity>) context.getBusinessData("Activitys");
		while(link.size()>1){
			link.getLast().finish();
			link.removeLast();
		}
	}
	/**
	 * 系统设置界面，修改密码
	 */
	public boolean k0901(){
		try {
			boolean isModify =  service.modifyPassword();
			if(isModify){
				return true;
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}
		return false;
 	}
}
