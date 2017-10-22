package com.guanhuan.common.db;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author ChenJia
 *
 */
@Component
public class HibernateAccessor {

	@Autowired
	private SessionFactory sessionFactory;
	private Session session = null;
	
	private Session getSession() {
		try {
		    session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		return session;
	}
	
	public void save(Object o) {
		if(session == null)
			getSession();
		session.save(o);
	}

	public void saveOrUpdate(Object o) {
		if(session == null)
			getSession();
		session.saveOrUpdate(o);
	}
	
	public void update(Object o) {
		if(session == null)
			getSession();
		session.update(o);
	}
	
	public void delete(Object o) {
		if(session == null)
			getSession();
		session.delete(o);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Serializable id) {
		if(session == null)
			getSession();
		return (T)session.get(clazz, id);
	}
	
	public List<?> find(String sql, Object ... args) {
		if(session == null)
			getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(args != null) {
			int i = 0;
			for (Object object : args) {
				query.setParameter(i, object);
				i++;
			}
		}
		return query.list();
	}
	
	/**
	 * ， 1
	 * find(User, select * from user where id = ？, 1)
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(Class<T> clazz, String sql, Object ... args) {
		if(session == null)
			getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(args != null) {
			int i = 0;
			for (Object object : args) {
				query.setParameter(i, object);
				i++;
			}
		}
		query.addEntity(clazz);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findUnique(Class<T> clazz, String sql, Object ... args) {
		if(session == null)
			getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(args != null) {
			int i = 0;
			for (Object object : args) {
				query.setParameter(i, object);
				i++;
			}
		}
		query.addEntity(clazz);
		return (T) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findUnique(String sql, Object ... args) {
		if(session == null)
			getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(args != null) {
			int i = 0;
			for (Object object : args) {
				query.setParameter(i, object);
				i++;
			}
		}
		return (T) query.uniqueResult();
	}
	
	public void save(String sql, Object ... args) {
		if(session == null)
			getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(args != null) {
			int i = 0;
			for (Object object : args) {
				query.setParameter(i, object);
				i++;
			}
		}
		query.executeUpdate();
	}
	
	
	public void save(String sql, List<Object[]> argList) {
		if(session == null)
			getSession();
		if(argList != null && !argList.isEmpty()) {
			SQLQuery query = session.createSQLQuery(sql);
			for(Object[] args : argList) {
				int i = 0;
				for (Object object : args) {
					query.setParameter(i, object);
					i++;
				}
				query.executeUpdate();
			}
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
