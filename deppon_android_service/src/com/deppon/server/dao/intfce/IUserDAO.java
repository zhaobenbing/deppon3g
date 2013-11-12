package com.deppon.server.dao.intfce;

import java.util.List;
/**
 * 用户DAO
 * @author Administrator:赵本兵
 * @ 创建时间：2011-8-15
 */
import com.deppon.common.beans.User;
import com.deppon.common.core.ICore;

public interface IUserDAO extends ICore<User, String>{
	//添加用户
	public boolean addUser(User user) ;
	//根据id删除用户
	public boolean removeUser(int id);
	//查找用户根据Id
	public User findUser(long id);
	//查找用户根据密码和email
	public User findUser(String email ,String password);
	//查找所有用户
	public List<User> findAllUsers();
	//修改用户信息
	public boolean modifyUserInfo(User user);
	//查看注册邮箱是否已使用
	public boolean emailExisted(String email);
	//根据用户Id更新用户名
	public void updateUserName(int userId,String name);
	//查看用户名是否已存在
	public boolean checkUserNameExisted(int userId);
	//修改密码
	public  boolean modifyPassword(String userId,String oldPassword,String newPassword);
}
