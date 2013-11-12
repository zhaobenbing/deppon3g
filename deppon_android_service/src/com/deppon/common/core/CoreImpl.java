package com.deppon.common.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class CoreImpl<T extends Serializable, PK extends Serializable> extends
		HibernateDaoSupport implements ICore<T, PK> {

	private Class<T> entityClass;

 	public CoreImpl() {
		Class c = this.getClass();
		Type t = c.getGenericSuperclass();

		if (t instanceof ParameterizedType) {
			Type p[] = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	public void addnew(T bean) {
		this.getHibernateTemplate().save(bean);
	}

	public void delete(T bean) {
		this.getHibernateTemplate().delete(bean);

	}

	public void update(T bean) {
		this.getHibernateTemplate().update(bean);

	}

	public T load(PK id) {

		return (T) this.getHibernateTemplate().load(this.entityClass, id);
	}

	public T get(PK id) {

		return (T) this.getHibernateTemplate().get(this.entityClass, id);
	}

	public List<T> find(String hsql) {
		final String findSql = hsql;
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(findSql);
				return (List<T>) query.list();
			}
		});
	}

 	public List<T> find(T bean) {
		final T finalBean = bean;
		final Class clz = this.entityClass;
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clz);
				Example e = Example.create(finalBean);
				criteria.add(e);
				return (List<T>) criteria.list();
			}
		});
	}

 	@Override
	public List<T> find(T bean, String[] excludeProperty) {
		final T finalBean = bean;
		final String[] excludePropertyProxy = excludeProperty;
		final Class clz = this.entityClass;
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clz);
				Example e = Example.create(finalBean);
				for (String exclude : excludePropertyProxy) {
					e.excludeProperty(exclude);
				}
				criteria.add(e);
				return (List<T>) criteria.list();
			}
		});
	}

}
