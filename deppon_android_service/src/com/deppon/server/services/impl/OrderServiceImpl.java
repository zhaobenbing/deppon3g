package com.deppon.server.services.impl;

import com.deppon.common.beans.Order;
import com.deppon.common.beans.PriceBean;
import com.deppon.server.dao.impl.OrderDAOImpl;
import com.deppon.server.dao.intfce.IOrderDAO;
import com.deppon.server.services.intfce.IOrderService;
/**
 * @功能描述：定单Service实现
 * @author 赵本兵
 * @创建时间：2011-10-13
 */
public class OrderServiceImpl implements IOrderService{
	private IOrderDAO iOrderDAO ;
	public OrderServiceImpl() {
		iOrderDAO = new OrderDAOImpl();
	}
	@Override
	public boolean addOrder(Order order) {
 		return iOrderDAO.addOrder(order);
	}

	@Override
	public Order findOrderById(String orderId) {
 		return iOrderDAO.findOrderById(orderId);
	}

	@Override
	public Order[] findSomeUserOrders(String userId) {
 		return iOrderDAO.findSomeUserOrders(userId);
	}

	@Override
	public void modifyOrder(Order order) {
		iOrderDAO.modifyOrder(order);
	}
	@Override
	public String findWayBill(String wayBillNumber) {
 		return iOrderDAO.findWayBill(wayBillNumber);
	}
	@Override
	public PriceBean priceSearch(String startCityId, String arriveCityId,
			int paras_TransProperty, double paras_GoodsWeight,
			double paras_GoodsVolume) {
 		return iOrderDAO.priceSearch(startCityId, arriveCityId, paras_TransProperty, paras_GoodsWeight, paras_GoodsVolume);
	}
	@Override
	public Order[] findSomeUntrearedOrders(String userId, int status) {
 		return iOrderDAO.findSomeUntrearedOrders(userId, status);
	}
	@Override
	public Order[] findLastOrder(String userId, int start, int end) {
 		return iOrderDAO.findLastOrder(userId, start, end);
	}
	@Override
	public boolean cancleOrder(String orderNumber) {
		return iOrderDAO.cancleOrder(orderNumber);		
	}
	@Override
	public Order[] findCancledOrder(String userId, int status) {
 		return iOrderDAO.findCancledOrder(userId, status);
	}
}
