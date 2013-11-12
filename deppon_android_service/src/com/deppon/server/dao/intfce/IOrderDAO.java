package com.deppon.server.dao.intfce;

import com.deppon.common.beans.Order;
import com.deppon.common.beans.PriceBean;

/**
 * @功能描述：定单DAO
 * @author 赵本兵
 * @创建时间：2011-10-13
 */
public interface IOrderDAO {
	//添加定单
	public boolean addOrder(Order order);
	//修改定单
	public void modifyOrder(Order order);
	//根据定单号查询定单
	public Order findOrderById(String orderId);
	//查询某个用户的所有定单
	public Order[] findSomeUserOrders(String userId);
	//查询某个用户的未受理或受理或取消定单 0:表示未受理 1:表示已受理 -1:表示该订单已经取消
	public Order[] findSomeUntrearedOrders(String userId,int status);
	//查询某个用户的最近一笔订单，可查询多条
	public Order[] findLastOrder(String userId,int start,int end);
	//取消订单
	public boolean cancleOrder(String orderNumber);
	//查询已取消的订单
	public Order[] findCancledOrder(String userId,int status);
	//运单查询
	public String findWayBill(String wayBillNumber);
	//价格时效查询
	public PriceBean priceSearch(String startCityId,String arriveCityId,int paras_TransProperty, double paras_GoodsWeight, double paras_GoodsVolume);
}
