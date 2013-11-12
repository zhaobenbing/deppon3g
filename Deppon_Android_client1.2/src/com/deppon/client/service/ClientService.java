package com.deppon.client.service;

public interface ClientService {
	/**
	 * 从服务器获取省份，城市，部门数据，加载到客户端
	 */
	public boolean initData() throws Exception;
	/**
	 * 登录
	 * @throws Exception
	 */
	public boolean login() throws Exception ;

	/**
	 * 注册
	 * @throws Exception
	 */
	public boolean regist() throws Exception;
	
	/**
	 * 修改用户信息
	 * @throws Exception
	 */
	public void modifyUserInformation() throws Exception;
	
	/**
	 * 修改账户密码
	 * @throws Exception
	 */
	public boolean modifyPassword() throws Exception;
	/**
	 * 保存个人资料
	 * @throws Exception
	 */
	public boolean saveBaseInfo() throws Exception;
	/**
	 * 查询所有订单
	 * @throws Exception
	 */
	public boolean findOrders()throws Exception ;
	/**
	 * 查询未受理的订单
	 * @throws Exception
	 */
	public boolean untreatedOrders()throws Exception ;
	/**
	 * 查询已受理的订单
	 * @throws Exception
	 */
	public boolean treatedOrders()throws Exception ;
	/**
	 * 查询已受理的订单
	 * @throws Exception
	 */
	public boolean lastOrder()throws Exception ;
	/**
	 * 取消订单
	 * @throws Exception
	 */
	public boolean cancleOrder()throws Exception ;
	/**
	 * 查询取消订单
	 * @throws Exception
	 */
	public boolean findCancledOrder()throws Exception ;
	
 	/**
	 * 根据城市Id查找网点
	 */
	public void getDeptsByCityId()throws Exception ;
	/**
	 * 根据部门名称查找部门信息请求
	 */
	public void getDeptsInfo()throws Exception ;
	/**
	 * 查询网点
	 * @throws Exception
	 */
	public boolean findBranch()throws Exception ;
	/**
	 * 货物跟踪
	 * @throws Exception
	 */
	public boolean findWayBill()throws Exception ;
	/**
	 * 价格时效查询
 	 * @throws Exception
	 */
	public boolean priceSearch()throws Exception;
	/**
	 * 下订单
	 * @throws Exception
	 */
	public boolean sendOrder()throws Exception ;
	
	/**
	 * 添加联系人
	 * @throws Exception
	 */
	public void addContact()throws Exception;
	
	/**
	 * 修改联系人
	 * @throws Exception
	 */
	public void modifyContact() throws Exception;
	
	/**
	 * 查询联系人
	 * @throws Exception
	 */
	public void searchContact()throws Exception;
	
	/**
	 * 删除联系人
	 * @throws Exception
	 */
	public void deleteContact() throws Exception;
	
	/**
	 * 在线验证
	 */
	public void online() throws Exception;
	
	/**
	 * 退出系统
	 * @throws Exception
	 */
	public void exit() throws Exception;
}
