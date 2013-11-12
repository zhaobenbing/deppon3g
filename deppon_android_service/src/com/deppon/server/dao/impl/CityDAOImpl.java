package com.deppon.server.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deppon.common.beans.City;
import com.deppon.common.core.CoreImpl;
import com.deppon.common.util.HibernateSessionFactory;
import com.deppon.server.dao.intfce.ICityDAO;
/**
 * @功能描述：城市DAO实现
 * @author ：赵本兵
 * @创建时间：2011-10-5
 */
public class CityDAOImpl extends CoreImpl<City, String> implements ICityDAO {
 	@SuppressWarnings("unchecked")
	@Override
	public List<City> findAllCitys() {
		List<City> citys = new ArrayList<City>();
		Session session = null;
		Transaction txt = null;
		session = HibernateSessionFactory.getSession();
		txt = session.beginTransaction();
		String sql = "select * from dp_3g_city";
		Query query = session.createSQLQuery(sql);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		System.out.println("City size------------>" + list.size());
		City city = null;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			city = new City();
			city.setId(obj[0] == null ? null : obj[0].toString());
			city.setCityId(obj[1] == null ? null : obj[1].toString());
			city.setCityName(obj[2] == null ? null : obj[2].toString());
			city.setFather(obj[3] == null ? null : obj[3].toString());
			city.setCityOldId(obj[4] == null ? null : obj[4].toString());
			city.setCityOldName(obj[5] == null ? null : obj[5].toString());
			citys.add(city);
		}
		txt.commit();
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return citys;
	}

	 
 	@SuppressWarnings("unchecked")
	@Override
	public List<City> findcityByProvinceId(String province_id) {
		List<City> citys = new ArrayList<City>();
		Session session = null;
		Transaction txt = null;
		session = HibernateSessionFactory.getSession();
		txt = session.beginTransaction();
		String sql = "  select * from dp_3g_city where FOLDPROVINCEID=:FFATHER";
		Query query = session.createSQLQuery(sql).setParameter("FFATHER", province_id);
 		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		System.out.println("City size------------>" + list.size());
		City city = null;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			city = new City();
			city.setId(obj[0] == null ? null : obj[0].toString());
			city.setCityId(obj[1] == null ? null : obj[1].toString());
			city.setCityName(obj[2] == null ? null : obj[2].toString());
			city.setFather(obj[3] == null ? null : obj[3].toString());
			city.setCityOldId(obj[4] == null ? null : obj[4].toString());
			city.setCityOldName(obj[5] == null ? null : obj[5].toString());
			citys.add(city);
		}
		txt.commit();
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return citys;
	}

 	@Override
	public void addCity(City city) {
 		
	}
 
	@Override
	public City findCityById(String cityId) {
 		return null;
	}

 	@Override
	public City findCityByName(String CityName) {
 		return null;
	}
}
