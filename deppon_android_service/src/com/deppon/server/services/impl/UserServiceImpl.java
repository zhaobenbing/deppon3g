package com.deppon.server.services.impl;

import com.deppon.common.beans.User;
import com.deppon.server.dao.impl.UserDAOImpl;
import com.deppon.server.dao.intfce.IUserDAO;
import com.deppon.server.services.intfce.IUserService;

public class UserServiceImpl implements IUserService {
	private IUserDAO userDAO ;
	public UserServiceImpl() {
		userDAO = new UserDAOImpl();
	}
	@Override
	public boolean checkUserNameExisted(int userId) {
		return userDAO.checkUserNameExisted(userId);
	}
	@Override
	public boolean emailExisted(String email) {
 		return userDAO.emailExisted(email);
	}
	@Override
	public User findUser(String id, String pwd, String str) {
 		return null;
	}
	@Override
	public User findUserById(int id) {
 		return null;
	}

	@Override
	public User login(String email,String password) throws Exception {
		User user = userDAO.findUser(email, password);
		if(user != null ){
			return user;
		}
 		return null;
	}

	@Override
	public boolean modifyPassword(String userId, String oldPassword,
			String newPassword) {
 		return userDAO.modifyPassword(userId, oldPassword, newPassword);
	}

	@Override
	public boolean modifyUserInfo(User user) {
 		return userDAO.modifyUserInfo(user);
	}
	
	@Override
	public boolean regist(User user) {
 		if(userDAO.addUser(user)){
			return true;
		}
 		return false;
		
	}
 	@Override
	public void updateLoginTime(String username) {
 		
	}
	@Override
	public void updateUserName(int userId, String name) {
		userDAO.updateUserName(userId, name);
	}
}
