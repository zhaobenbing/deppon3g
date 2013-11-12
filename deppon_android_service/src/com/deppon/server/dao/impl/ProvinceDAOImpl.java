package com.deppon.server.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deppon.common.beans.Province;
import com.deppon.common.core.CoreImpl;
import com.deppon.common.util.HibernateSessionFactory;
import com.deppon.server.dao.intfce.IProvinceDAO;

/**
 * @功能描述：省份DAO实现
 * @author：赵本兵
 * @创建时间：2011-10-4
 */
public class ProvinceDAOImpl extends CoreImpl<Province, String> implements
		IProvinceDAO {

 	@SuppressWarnings("unchecked")
	@Override
	public List<Province> findAllProvinces() {
		List<Province> pros = new ArrayList<Province>();
		Session session = null;
		Transaction txt = null;
		session = HibernateSessionFactory.getSession();
		txt = session.beginTransaction();
		String sql = "select * from dp_3g_province order by findexid";
		Query query = session.createSQLQuery(sql);
 		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		System.out.println("province size------------>"+list.size());
		Province pro = null;
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			pro = new Province();
			pro.setId(obj[0] == null ? null : obj[0].toString());
			pro.setProvinceId(obj[1] == null ? null : obj[1].toString());
			pro.setProvinceName(obj[2] == null ? null : obj[2].toString());
			pro.setFindex(obj[3] == null ? null : obj[3].toString());
			pro.setProvinceOldId(obj[4] == null ? null : obj[4].toString());
			pro.setProvinceOldName(obj[5] == null ? null : obj[5].toString());
 			pros.add(pro);
		}
		txt.commit();
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return pros;
	}

	@Override
	public void addProvince(Province provnce) {
 		
	}

	@Override
	public Province findProvinceById(String provinceId) {
 		return null;
	}

	@Override
	public Province findProvinceByName(String provinceName) {
 		return null;
	}
 
}
