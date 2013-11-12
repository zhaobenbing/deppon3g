package com.deppon.server.services.intfce;

import com.deppon.common.beans.User;
/**
 * @ 功能描述：IUserService
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public interface IUserService {
	//登录
	public User login(String email,String password) throws Exception;	
	//注册
	public boolean regist(User user);
	//修改密码
	public  boolean modifyPassword(String userId,String oldPassword,String newPassword);
	//修改用户信息
	public  boolean modifyUserInfo(User user);
	//获得用户信息Id
	public User findUserById(int id);
	//更新登录时间
	public void updateLoginTime(String username);
	//用户查询
	public User findUser(String id,String pwd,String str);
	 
	//查看用户名是否已存在
	public boolean checkUserNameExisted(int userId);
	//查看注册邮箱是否已使用
	public boolean emailExisted(String email);
	//根据用户Id更新用户名
	public void updateUserName(int userId,String name);
}
