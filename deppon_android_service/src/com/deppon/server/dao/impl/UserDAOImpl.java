package com.deppon.server.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deppon.common.beans.User;
import com.deppon.common.core.CoreImpl;
import com.deppon.common.util.HibernateSessionFactory;
import com.deppon.server.dao.intfce.IUserDAO;

/**
 * @用户DAO实现
 * @author :赵本兵 
 * @ 创建时间：2011-8-15
 */
public class UserDAOImpl extends CoreImpl<User, String> implements IUserDAO {
 	@Override
	public boolean addUser(User user) {
		Session session = null ;
		Transaction txt = null;
 		try {
  			session = HibernateSessionFactory.getSession();
 			//开启事务
			 txt = session.beginTransaction();
 			session.save(user);
			//提交事务
			txt.commit();
 		} catch (Exception e) {
			e.printStackTrace();
			txt.rollback();
 		}finally{
 			if(session!=null){
 				HibernateSessionFactory.closeSession();
  			}
 		}
		return true;
	}

	@Override
	public List<User> findAllUsers() {
 		return null;
	}

	@Override
	public User findUser(long id) {
 		return null;
	}

	/**
	 * 根据用户名和密码查找用户
	 */
 	@SuppressWarnings("unchecked")
	@Override
	public User findUser(String email, String password) {
		System.out.println("email---------->" + email);
		System.out.println("password--------->" + password);
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery("select * from dp_3g_user where d_email=:email and d_password=:password");
		query.setParameter("email", email);
		query.setParameter("password", password);
 		List list = query.list();
		if(list.size()<1){
			HibernateSessionFactory.closeSession();
			return null;
 		}
 		Object[] obj =  (Object[]) list.get(0);
 		User user = new User();
 		user.setId(Integer.parseInt(obj[0].toString()));
 		user.setLoginName(obj[1]==null?"":obj[1].toString());
 		user.setPassword(obj[2]==null?"":obj[2].toString());
 		user.setEmail(obj[3]==null?"":obj[3].toString());
 		user.setName(obj[4]==null?"":obj[4].toString());
 		user.setSex(obj[5]==null?"":obj[5].toString());
 		user.setAddress(obj[6]==null?"":obj[6].toString());
 		user.setTelephone(obj[7]==null?"":obj[7].toString());
 		user.setMobilephone(obj[8]==null?"":obj[8].toString());
 		user.setSlstatus(obj[9]==null?0:Integer.parseInt(obj[9].toString()));
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		Date date = new Date(System.currentTimeMillis());
 		String string = sdf.format(date);
 		try {
			user.setLastUpdatetime(obj[10]==null?(sdf.parse(string)):sdf.parse(obj[10].toString()));
			user.setRegiterTime(obj[11]==null?(sdf.parse(string)):sdf.parse(obj[11].toString()));
			user.setLastActionTime(obj[15]==null?(sdf.parse(string)):sdf.parse(obj[15].toString()));
		} catch (ParseException e) {
 			e.printStackTrace();
		}
  		user.setPostCode(obj[12]==null?"":obj[12].toString());
 		user.setProvince(obj[13]==null?"":obj[13].toString());
 		user.setCity(obj[14]==null?"":obj[14].toString());
 	
  	
 		System.out.println("list_Result---------->" + user.getEmail());
 		System.out.println("list_Result---------->" + user.getTelephone());
	    if(session != null){
	    	HibernateSessionFactory.closeSession();
	    }
	    return user;
	}

	@Override
	public boolean modifyUserInfo(User user) {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		StringBuilder sql = new StringBuilder();
		sql.append("update dp_3g_user set d_name=:name ,");
		sql.append(" d_telephone=:phone,d_mobilephone=:mobile,d_province=:provinceId ");
		sql.append(",d_city=:cityId,d_lastupdatetime=:lastupdatetime,D_ADDRESS=:address ");
		sql.append(" where d_id=:userId");
		Query query = session.createSQLQuery(sql.toString());
		query.setParameter("name",user.getName());
		query.setParameter("phone", user.getTelephone());
		query.setParameter("mobile", user.getMobilephone());
		query.setParameter("provinceId", user.getProvince());
		query.setParameter("cityId", user.getCity());
		query.setParameter("lastupdatetime", new Date());
		query.setParameter("address", user.getAddress());
		query.setParameter("userId",user.getId());
		int a = query.executeUpdate();
		System.out.println("isSeccuss"+a);
 		if(a>0){
			session.getTransaction().commit();
			return true;
		}else{
			session.getTransaction().rollback();
			return false;
 		}
	}

	@Override
	public boolean removeUser(int id) {
 		return false;
	}
	/**
	 * 查看email是否存在
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean emailExisted(String email) {
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(" select d_email from dp_3g_user where d_email=:email ");
		query.setParameter("email", email);
 		List list = query.list();
 		if (list.size()>0) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			if(obj != null){
				return true;
			}
		} 
		return false;
	}
	//根据用户Id更新用户名
	@Override
	public void updateUserName(int userId, String name) {
		 Session session = HibernateSessionFactory.getSession();
		 session.beginTransaction();
		 Query query = session.createSQLQuery(" update dp_3g_user set d_name=:name where d_id=:userId ");
		 query.setParameter("userId", userId);
		 query.setParameter("name", name);
		 query.executeUpdate();
		 session.getTransaction().commit();
 	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUserNameExisted(int userId) {
 		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery("select d_name  from dp_3g_user where d_id=:userId");
		query.setParameter("userId", userId+"");
 		List list = query.list();
 		if (list.size()>0) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			if(obj != null){
				return true;
			}
		} 
		return false;
 	}
	//修改密码
	@Override
	public boolean modifyPassword(String userId, String oldPassword,
			String newPassword) {
		System.out.println("==oldpwd=="+oldPassword);
		System.out.println("==newpwd=="+newPassword);
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		String sql = "update dp_3g_user set D_PASSWORD=:newpwd where D_ID=:userId and D_PASSWORD=:oldpwd";
		Query query = session.createSQLQuery(sql);
		query.setParameter("newpwd", newPassword);
		query.setParameter("userId", userId);
		query.setParameter("oldpwd", oldPassword);
		int a = query.executeUpdate();
 		if(a>0){
			session.getTransaction().commit();
			return true;
		}else{
			session.getTransaction().rollback();
			return false;
 		}
	}
}
