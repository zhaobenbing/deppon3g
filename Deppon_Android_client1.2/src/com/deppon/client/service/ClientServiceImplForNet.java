package com.deppon.client.service;

import java.util.LinkedList;

import android.app.Activity;

import com.deppon.client.protocol.BaseProtocol;
import com.deppon.client.protocol.CityFindDeptsProtocol;
import com.deppon.client.protocol.DeptSearchProtocol;
import com.deppon.client.protocol.GetDeptInfoProtocol;
import com.deppon.client.protocol.GoodsTrackProtocol;
import com.deppon.client.protocol.InitDataToClientProtocol;
import com.deppon.client.protocol.LoginProtocol;
import com.deppon.client.protocol.ModifyPasswordProtocol;
import com.deppon.client.protocol.OrderManageProtocol;
import com.deppon.client.protocol.PriceSearchProtocol;
import com.deppon.client.protocol.RegistProtocol;
import com.deppon.client.protocol.SavePersonInfoProtocol;
import com.deppon.client.protocol.SendOrderProtocol;
import com.deppon.common.beans.User;

/**
 * 客户端与服务器端连接现实方法
 * 
 * @author Administrator：赵本兵 @ 创建时间：2011-7-29
 */
public class ClientServiceImplForNet implements ClientService {
	// 基本URL
	public static String BASEURL = "";
	private ClientContext context;
	//private DatabaseHelper dh;
	private BaseProtocol bp;

	public ClientServiceImplForNet(ClientContext context) {
		this.context = context;
		BASEURL = context.getSystemProperty("BASE_URL");
	}

	public ClientContext getContext() {
		return context;
	}

	public void setContext(ClientContext context) {
		this.context = context;
	}

	public boolean initData() throws Exception {
		bp = new InitDataToClientProtocol(context);
		InitDataToClientProtocol init = (InitDataToClientProtocol) bp;
		boolean bool = init.executeSomeProtocol(null, null, BASEURL);
		if (bool) {
			System.out.println("send initData success");
			return true;
		} else {
			System.out.println("send initData fail");
			return false;
		}

	}

	/**
	 * 作者：赵本兵 功能：登录
	 */

	@Override
	public boolean login() throws Exception {

		System.out.println("BaseUrl------------->" + BASEURL);
		bp = new LoginProtocol(context);
		LoginProtocol lp = (LoginProtocol) bp;
		String email = (String) context.getBusinessData("email");
		System.out.println("email:" + email);
		String password = (String) context.getBusinessData("Pwd");
		System.out.println("Pwd:" + password);
		boolean bool = lp.executeSomeProtocol(email, password, BASEURL);
		System.out.println("bool:----->" + bool);
		if (bool) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 作者：赵本兵 功能：注册
	 */
	@Override
	public boolean regist() throws Exception {
		bp = new RegistProtocol(context);
		RegistProtocol rp = (RegistProtocol) bp;
		User user = (User) context.getBusinessData("RegistUser");
		System.out.println("RegistUser------------>" + user.toString());
		rp.addNameValuePair("RegistInfo", user.toString());
		boolean bool = rp.executeSomeProtocol(null, null, BASEURL);
		if (bool) {
			return true;
		} else {
			return false;
		}
	}

	//下单请求
	@Override
	public boolean sendOrder() throws Exception {
		 bp = new SendOrderProtocol(context);
		 SendOrderProtocol sop = (SendOrderProtocol)bp;
		 return sop.executeSomeProtocol(null, null, BASEURL);
	}
	@Override
	public void addContact() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteContact() throws Exception {
		// TODO Auto-generated method stub

	}
	//网点查询
	@Override
	public boolean findBranch() throws Exception {
		bp = new DeptSearchProtocol(context);
		DeptSearchProtocol dsp = (DeptSearchProtocol)bp;
		return dsp.executeSomeProtocol(null, null, BASEURL);
 	}
	//查找所有订单
	@Override
	public boolean findOrders() throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
 		return omp.executeSomeProtocol(null, null, BASEURL);
 
	}
	//查找未受理订单
	public boolean untreatedOrders()throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
 		return omp.executeSomeProtocol("untreated", null, BASEURL);
 	}
	//查询已受理订单
	@Override
	public boolean treatedOrders() throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
 		return omp.executeSomeProtocol("treated", null, BASEURL);
	}
	//查询最近一笔订单
	@Override
	public boolean lastOrder() throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
 		return omp.executeSomeProtocol("last", null, BASEURL);
	}
	//取消订单
	@Override
	public boolean cancleOrder() throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
		String updateOrderNum = (String) context.getBusinessData("updateOrderNum");
		omp.addNameValuePair("updateOrderNum", updateOrderNum);
 		return omp.executeSomeProtocol("cancle", null, BASEURL);
	}
	//查询已取消的订单
	@Override
	public boolean findCancledOrder() throws Exception {
		bp = new OrderManageProtocol(context);
		OrderManageProtocol omp = (OrderManageProtocol)bp;
   		return omp.executeSomeProtocol("findCancle", null, BASEURL);
	}
	@Override
	public void modifyContact() throws Exception {
		// TODO Auto-generated method stub

	}
	//修改密码
	@Override
	public boolean modifyPassword() throws Exception {
		bp = new ModifyPasswordProtocol(context);
		ModifyPasswordProtocol mpp = (ModifyPasswordProtocol)bp;
   		return mpp.executeSomeProtocol(null, null, BASEURL);
 	}

	@Override
	public void modifyUserInformation() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void online() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 退出
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void exit() throws Exception {
		LinkedList<Activity> list = (LinkedList<Activity>) context.getBusinessData("Activitys");
		while(list.size()>0){
			list.getLast().finish();
			//System.out.println("++activitys before++"+list.size()+"===activity name==="+list.getLast().getLocalClassName());
			list.removeLast();
	 		//System.out.println("++activitys after++"+list.size()+"===activity name==="+list.getLast().getLocalClassName());
		}
		/**
		context.addBusinessData("CurrentUser", null);
		context.addBusinessData("TimeOut", null);
		context.addBusinessData("cacheCitys", null);
		context.addBusinessData("cacheProvinces", null);
		context.addBusinessData("cacheDepts", null);
		context.addBusinessData("email", null);
		context.addBusinessData("Pwd",null);		
		context.addBusinessData("CurrentUser", "");
		context.addBusinessData("TimeOut", "");
		context.addBusinessData("cacheCitys", "");
		context.addBusinessData("cacheProvinces","");
		context.addBusinessData("cacheDepts","");
		context.addBusinessData("email", "");
		context.addBusinessData("Pwd", "");		
		context = null;
		**/
		try {
			android.os.Process.killProcess(android.os.Process.myPid());
			finalize();
			System.gc();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void searchContact() throws Exception {
 
	}

	/**
	 * 根据城市Id查找网点请求
	 */
	@Override
	public void getDeptsByCityId() throws Exception {
		bp = new CityFindDeptsProtocol(context);
		CityFindDeptsProtocol cfdp = (CityFindDeptsProtocol) bp;
		cfdp.executeSomeProtocol(null, null, BASEURL);
 	}
	//网点查询
	@Override
	public void getDeptsInfo() throws Exception {
		bp = new GetDeptInfoProtocol(context);
		GetDeptInfoProtocol gdip = (GetDeptInfoProtocol)bp;
		gdip.executeSomeProtocol(null, null, BASEURL);
	}
	//货物跟踪
	@Override
	public boolean findWayBill() throws Exception {
		bp = new GoodsTrackProtocol(context);
		GoodsTrackProtocol gtp = (GoodsTrackProtocol)bp;
  		return gtp.executeSomeProtocol(null, null, BASEURL);
	}
	//价格时效查询
	@Override
	public boolean priceSearch() throws Exception {
		bp = new PriceSearchProtocol(context);
		PriceSearchProtocol psp = (PriceSearchProtocol)bp;
  		return psp.executeSomeProtocol(null, null, BASEURL);
	}
	//填写基本信息
	@Override
	public boolean saveBaseInfo() throws Exception {
		bp = new SavePersonInfoProtocol(context);
		SavePersonInfoProtocol spp = (SavePersonInfoProtocol)bp;
  		return spp.executeSomeProtocol(null, null, BASEURL);
	}
}
