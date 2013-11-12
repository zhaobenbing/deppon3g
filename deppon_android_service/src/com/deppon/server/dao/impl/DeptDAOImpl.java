package com.deppon.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.deppon.common.beans.City;
import com.deppon.common.beans.Dept;
import com.deppon.common.beans.Province;
import com.deppon.common.core.CoreImpl;
import com.deppon.common.util.HibernateSessionFactory;
import com.deppon.server.dao.intfce.IDeptDAO;

/**
 * @功能描述：部门DAO实现
 * @author ：赵本兵
 * @创建时间：2011-10-8
 */
public class DeptDAOImpl extends CoreImpl<Dept, String> implements IDeptDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Province[] findAllProvice() {
		Province[] pros;
		Session session = null;
		Transaction txt = null;
		session = HibernateSessionFactory.getSession();
		txt = session.beginTransaction();
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from dp_3g_province p ");
		sb.append(" where exists(select * from dp_3g_dept b ");
		sb.append(" inner join dp_3g_city c on b.fnewcity = c.fid where (c.ffather = p.fprovinceid) and p.fid = b.fnewprovince and b.fisstart = 1) ");
		sb.append(" order by p.findexid,nlssort(p.fprovince,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Query query = session.createSQLQuery(sb.toString());
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		pros = new Province[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			pros[i] = new Province();
			pros[i].setId(obj[0] == null ? null : obj[0].toString());
			pros[i].setProvinceId(obj[1] == null ? null : obj[1].toString());
			pros[i].setProvinceName(obj[2] == null ? null : obj[2].toString());
			pros[i].setFindex(obj[3] == null ? null : obj[3].toString());
			pros[i].setProvinceOldId(obj[4] == null ? null : obj[4].toString());
			pros[i].setProvinceOldName(obj[5] == null ? null : obj[5].toString());
		}
		txt.commit();
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return pros;
	}

	@SuppressWarnings("unchecked")
	@Override
	public City[] findCity() {
		City[] citys;
		Session session = null;
		Transaction txt = null;
		session = HibernateSessionFactory.getSession();
		txt = session.beginTransaction();
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from dp_3g_city c ");
		sb.append(" WHERE EXISTS (SELECT * FROM dp_3g_dept b ");
		sb.append(" INNER JOIN dp_3g_province p ON b.fnewprovince = p.fid  and p.foldprovinceid = b.foldprovince  ");
		sb.append(" WHERE (c.FFATHER = p.FPROVINCEID) and c.foldcityid = b.foldcity and b.fnewcity = c.fid and b.FISSTART = 1) ");
		sb.append(" order by nlssort(c.fcity,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Query query = session.createSQLQuery(sb.toString());
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		citys = new City[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			citys[i] = new City();
			citys[i].setId(obj[0] == null ? null : obj[0].toString());
			citys[i].setCityId(obj[1] == null ? null : obj[1].toString());
			citys[i].setCityName(obj[2] == null ? null : obj[2].toString());
			citys[i].setFather(obj[3] == null ? null : obj[3].toString());
			citys[i].setCityOldId(obj[4] == null ? null : obj[4].toString());
			citys[i].setCityOldName(obj[5] == null ? null : obj[5].toString());
			citys[i].setOldFather(obj[6] == null ? null : obj[6].toString());
		}
		txt.commit();
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return citys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dept[] findDept() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.fid,b.fdeptid,b.fsimplename,b.fdeptaddress,b.fdeptphone,b.FOLDPROVINCE,b.FOLDCITY  ")
		.append(" b.fnewprovince,b.fnewcity,b.fisstart from dp_3g_dept b  ")
		.append(" where exists(select * from dp_3g_province p inner join dp_3g_city c on ")
		.append(" p.fprovinceid = c.ffather where b.fnewprovince = p.fid and b.fnewcity = c.fid and ")
		.append(" b.fisstart = '1' and  b.fdeptaddress is not null and b.fdeptphone is not null and  ")
		.append(" b.fsimplename like '%营业部%') ")
		.append(" order by nlssort(b.fsimplename,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString());
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Dept[] dept = new Dept[list.size()];
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			dept[j] = new Dept();
			dept[j].setId(obj[0] == null ? null : obj[0].toString());
			dept[j].setDeptid(obj[1] == null ? null : obj[1].toString());
			dept[j].setDeptName(obj[2] == null ? null : obj[2].toString());
			dept[j].setDeptAddress(obj[3] == null ? null : obj[3].toString());
			dept[j].setDeptPhone(obj[4] == null ? null : obj[4].toString());
			
			dept[j].setOldProvinceId(obj[5] == null ? null : obj[5].toString());
			dept[j].setOldCityId(obj[6] == null ? null : obj[6].toString());
			
			dept[j].setProvinceId(obj[7] == null ? null : obj[7].toString());
			dept[j].setCityId(obj[8] == null ? null : obj[8].toString());
			
			dept[j].setIsStart(obj[9] == null ? 0 : Integer
					.parseInt(obj[9].toString()));
			if(j ==0)
			System.out.println(dept[j].toString());
		}
		
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return dept;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public City[] findCity(String provinceId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dp_3g_city c ");
		sql.append(" WHERE c.FFATHER =:provinceId ");
		sql.append(" AND EXISTS (SELECT * FROM dp_3g_dept b WHERE b.fnewcity = c.fid and b.FISSTART = 1) ");
		sql.append(" order by nlssort(c.fcity,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"provinceId", provinceId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		City[] citys = new City[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			citys[i] = new City();
			citys[i].setId(obj[0] == null ? null : obj[0].toString());
			citys[i].setCityId(obj[1] == null ? null : obj[1].toString());
			citys[i].setCityName(obj[2] == null ? null : obj[2].toString());
			citys[i].setFather(obj[3] == null ? null : obj[3].toString());
			citys[i].setCityOldId(obj[4] == null ? null : obj[4].toString());
			citys[i].setCityOldName(obj[5] == null ? null : obj[5].toString());
			citys[i].setOldFather(obj[6] == null ? null : obj[6].toString());
		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return citys;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Dept findDept(String deptId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dp_3g_dept where fdeptId =:deptId and  and FISSTART = '1' ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"deptId", deptId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Dept dept = new Dept();
		Object[] obj = (Object[]) list.get(0);	
		dept.setId(obj[0] == null ? null : obj[0].toString());
		dept.setDeptid(obj[1] == null ? null : obj[1].toString());
		
		dept.setDeptName(obj[2] == null ? null : obj[2].toString());
		dept.setCreateTime(obj[3] == null ? null : obj[3].toString());
		dept.setLastUpdateTime(obj[4] == null ? null : new Date());
		dept.setDeptAddress(obj[5] == null ? null : obj[5].toString());
		dept.setDeptPhone(obj[6] == null ? null : obj[6].toString());
		
		dept.setOldProvinceId(obj[7] == null ? null : obj[7].toString());
		dept.setOldCityId(obj[8] == null ? null : obj[8].toString());
		
		dept.setProvinceId(obj[9] == null ? null : obj[9].toString());
		dept.setCityId(obj[10] == null ? null : obj[10].toString());
		
		dept.setIsStart(obj[11] == null ? 0 : Integer
				.parseInt(obj[11].toString()));
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return dept;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dept[] findDeptByCityId(String cityId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.fid,b.fdeptid,b.fsimplename,b.fdeptaddress,b.fdeptphone,b.FOLDPROVINCE,b.FOLDCITY, ")
		.append(" b.fnewprovince,b.fnewcity,b.fisstart ")
		.append(" from dp_3g_dept b ").append(" inner join dp_3g_city c on c.fid = b.fnewcity ")
		.append(" where b.fnewcity=:cityId ").append(" and b.fisstart = 1 and b.fsimplename like '%营业部%' ")
		.append(" order by nlssort(b.fsimplename,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"cityId", cityId);
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Dept[] dept = new Dept[list.size()];
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			dept[j] = new Dept();
			dept[j].setId(obj[0] == null ? null : obj[0].toString());
			dept[j].setDeptid(obj[1] == null ? null : obj[1].toString());
			dept[j].setDeptName(obj[2] == null ? null : obj[2].toString());
			dept[j].setDeptAddress(obj[3] == null ? null : obj[3].toString());
			dept[j].setDeptPhone(obj[4] == null ? null : obj[4].toString());
			
			dept[j].setOldProvinceId(obj[5] == null ? null : obj[5].toString());
			dept[j].setOldCityId(obj[6] == null ? null : obj[6].toString());
			
			dept[j].setProvinceId(obj[7] == null ? null : obj[7].toString());
			dept[j].setCityId(obj[8] == null ? null : convertCityId(obj[8].toString()));
			
			dept[j].setIsStart(obj[9] == null ? 0 : Integer
					.parseInt(obj[9].toString()));
		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return dept;
	}
	
	//获取省份名称，根据id
	@SuppressWarnings("unchecked")
	@Override
	public String findProById(String proId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.fprovince from dp_3g_province p where p.fprovinceid =:proId ");
		Session session = HibernateSessionFactory.getSession();
		String pro = "";
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"proId", proId);
		List list = query.list();
		if (list != null) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			pro = obj != null ? obj.toString() : "";
		} else {
			return null;
		}
		return pro;
	}
	// 根据城市Id城市名称
	@SuppressWarnings("unchecked")
	@Override
	public String findCityById(String cityId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select FCITY from dp_3g_city");
		sql.append(" WHERE FcityId =:cityId  order by FCITY");
		Session session = HibernateSessionFactory.getSession();
		String city = "";
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"cityId", cityId);
		List list = query.list();
		if (list != null) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			city = obj != null ? obj.toString() : "";
		} else {
			return null;
		}
		return city;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String findStationInfo(String deptId) {
		Session session = HibernateSessionFactory.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(" select b.fdeptaddress,b.fdeptphone from dp_3g_dept b  ")
		.append(" where b.FDEPTID=:deptId ").append(" and FISSTART ='1' ");
 		try {
			// 创建查询语句
			Query query = session.createSQLQuery(sql.toString());
			// 设置参数
			query.setString("deptId", deptId);
			List list = query.list();
			// 判断list是否为空，如果为空则返回null,否则进行如下处理
			if (list != null&&list.size()>0) {
				// 获取第一条数据，转化为object对象数组
				Object obj[] = (Object[]) list.get(0);
				String addr = obj[0] != null ? obj[0].toString() : null;
				String phone = obj[1] != null ? obj[1].toString() : null;
 				if (addr == null && phone == null) {
					return null;
				}
 				// 返回拼接的网点信息字符串
 				return addr+"\n"+phone;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null) {
				HibernateSessionFactory.closeSession();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String findDeptInfo(String deptName) {
		Session session = HibernateSessionFactory.getSession();
		StringBuilder sql = new StringBuilder();
		sql.append(" select b.fdeptaddress,b.fdeptphone from dp_3g_dept b  ")
		.append(" where b.FSIMPLENAME=:deptName ")
		.append(" and FISSTART =1");
 		try {
			// 创建查询语句
			Query query = session.createSQLQuery(sql.toString());
			// 设置参数
			query.setString("deptName", deptName);
			List list = query.list();
			// 判断list是否为空，如果为空则返回null,否则进行如下处理
			if (list != null&&list.size()>0) {
				// 获取第一条数据，转化为object对象数组
				Object obj[] = (Object[]) list.get(0);
				String addr = obj[0] != null ? obj[0].toString() : null;
				String phone = obj[1] != null ? obj[1].toString() : null;
 				if (addr == null && phone == null) {
					return null;
				}
 				// 返回拼接的网点信息字符串
 				return addr+"\n"+phone;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null) {
				HibernateSessionFactory.closeSession();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String convertCityId(String cityId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select c.fcityid from dp_3g_city c where c.fid=:cityId ");
		Session session = HibernateSessionFactory.getSession();
		String city = "";
		Query query = session.createSQLQuery(sql.toString()).setParameter("cityId", cityId);
		List list = query.list();
		if (list != null) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			city = obj != null ? obj.toString() : "";
		} else {
			return null;
		}
		return city;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertProId(String proId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.fprovinceid from dp_3g_province p where p.fid=:proId ");
		Session session = HibernateSessionFactory.getSession();
		String pro = "";
		Query query = session.createSQLQuery(sql.toString()).setParameter(
				"proId", proId);
		List list = query.list();
		if (list != null) {
			// 获取第一条数据，转化为object对象数组
			Object obj = (Object) list.get(0);
			pro = obj != null ? obj.toString() : "";
		} else {
			return null;
		}
		return pro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertCityCharId(String cityId) {
		if(cityId != null&&cityId.length()>0){
			StringBuffer sql = new StringBuffer();
			sql.append(" select c.fid from dp_3g_city c where c.fcityid=:cityId ");
			Session session = HibernateSessionFactory.getSession();
			String city = "";
			Query query = session.createSQLQuery(sql.toString()).setParameter("cityId", cityId);
			List list = query.list();
			if (list != null) {
				// 获取第一条数据，转化为object对象数组
				Object obj = (Object) list.get(0);
				city = obj != null ? obj.toString() : "";
			} else {
				return null;
			}
			return city;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dept[] findDeptByName(String deptName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * ")
		.append(" from dp_3g_dept b ").append(" inner join dp_3g_city c on c.fid = b.fnewcity ")
		.append(" where b.fsimplename like '%"+deptName +"%' ").append(" and b.fisstart = 1 and b.fsimplename like '%营业部%' ")
		.append(" order by nlssort(b.fsimplename,'NLS_SORT=SCHINESE_PINYIN_M') ");
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createSQLQuery(sql.toString());
		List list = query.list();
		if (list.size() < 1) {
			HibernateSessionFactory.closeSession();
			return null;
		}
		Dept[] dept = new Dept[list.size()];
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = (Object[]) list.get(j);
			dept[j] = new Dept();
			dept[j].setId(obj[0] == null ? null : obj[0].toString());
			dept[j].setDeptid(obj[1] == null ? null : obj[1].toString());
			
			dept[j].setDeptName(obj[2] == null ? null : obj[2].toString());
			dept[j].setCreateTime(obj[3] == null ? null : obj[3].toString());
			dept[j].setLastUpdateTime(obj[4] == null ? null : new Date());
			dept[j].setDeptAddress(obj[5] == null ? null : obj[5].toString());
			dept[j].setDeptPhone(obj[6] == null ? null : obj[6].toString());
			
			dept[j].setOldProvinceId(obj[7] == null ? null : obj[7].toString());
			dept[j].setOldCityId(obj[8] == null ? null : obj[8].toString());
			
			dept[j].setProvinceId(obj[9] == null ? null : obj[9].toString());
			dept[j].setCityId(obj[10] == null ? null : obj[10].toString());
			
			dept[j].setIsStart(obj[11] == null ? 0 : Integer
					.parseInt(obj[11].toString()));
		}
		if (session != null) {
			HibernateSessionFactory.closeSession();
		}
		return dept;
	}
}
