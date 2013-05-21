/*
 * File HibernateUtil.java
 * Created on 2004-6-8
 *
 */
package cistern.dao.hibernate.hibernate3;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cistern.common.AppBizException;
import cistern.dao.IterateCallback;
import cistern.dao.hibernate.HQLQuery;


/**
 * @description 访问Hibernate工具类
 * @author seabao
 * @project Cistern
 * @date 2006-11-8
 */
public class HibernateUtil {
	private HibernateUtil() {
	}

	public static Object getId(Object obj) {
		Class<?> clazz = obj.getClass();
		BeanInfo info;

		try {
			info = java.beans.Introspector.getBeanInfo(clazz);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method m = pd.getReadMethod();
			if (m == null)
				continue;

			if (m.isAnnotationPresent(Id.class)) {
				try {
					return m.invoke(obj);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void iterateElement(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery,
			final IterateCallback callback) throws AppBizException {
		Object e = hibernateTemp.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hqlQuery.getClause());
				query.setParameters(hqlQuery.getHQLArgs(), hqlQuery.getHQLTypes());
				Iterator<Object> it = query.iterate();

				try {
					while (it.hasNext()) {
						Object element = it.next();

						if (callback.doElement(session, element) == false) {
							return null;
						}

						if (session.isDirty()) {
							session.flush();
						}

						session.evict(element);
					}
				} catch (AppBizException e) {
					return e;
				} finally {
					Hibernate.close(it);
				}

				return null;
			}
		});

		if (e != null && e instanceof AppBizException) {
			throw (AppBizException) e;
		}
	}

	@SuppressWarnings("unchecked")
	public static long countElements(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery) {
		return ((Long) hibernateTemp.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer countSql = new StringBuffer();
				countSql.append("select count(*) ");
				countSql.append("from ");
				countSql.append(hqlQuery.getFromClause());
				if (hqlQuery.getWhereClause() != null && hqlQuery.getWhereClause().equals("") == false) {
					countSql.append(" where ");
					countSql.append(hqlQuery.getWhereClause());
				}
				Query query = session.createQuery(countSql.toString());
				Object[] hqlArgs = hqlQuery.getHQLArgs();
				if (hqlArgs != null) {
					for (int i = 0; i < hqlArgs.length; i++) {
						query.setParameter(i, hqlArgs[i]);
					}
				}

				List l = query.list();

				return (Long)l.get(0);
			}
		})).longValue();
	}

	@SuppressWarnings("unchecked")
	public static List queryElements(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery, final long firstResult,
			final long maxResults) {
		return (List) hibernateTemp.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = HibernateUtil.createQuery(session, hqlQuery);
				query.setFirstResult((int) firstResult);
				if (maxResults != -1) {
					query.setMaxResults((int) maxResults);
				}

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static List loadElementsForUpdate(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery,
			final String lockAlias) {
		return (List) hibernateTemp.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = HibernateUtil.createQuery(session, hqlQuery);
				query.setLockMode(lockAlias, LockMode.UPGRADE);

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static List queryElements1PlusN(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery,
			final int firstResult, final int maxResults) {
		return (List) hibernateTemp.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = HibernateUtil.createQuery(session, hqlQuery);
				query.setFirstResult(firstResult);
				if (maxResults != -1) {
					query.setMaxResults(maxResults);
				}

				Iterator it = query.iterate();

				try {
					List<Object> l = new ArrayList<Object>();
					while (it.hasNext()) {
						l.add(it.next());
					}

					return l;
				} finally {
					Hibernate.close(it);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static Object firstElement(HibernateTemplate hibernateTemp, final HQLQuery hqlQuery) {
		return hibernateTemp.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hqlQuery.getClause());
				query.setParameters(hqlQuery.getHQLArgs(), hqlQuery.getHQLTypes());
				Iterator it = query.iterate();

				try {
					if (it.hasNext()) {
						return it.next();
					} else {
						return null;
					}
				} finally {
					Hibernate.close(it);
				}
			}
		});
	}

	public static Query createQuery(Session session, HQLQuery hqlQuery) throws HibernateException {
		Query query = session.createQuery(hqlQuery.getClause());
		Object[] hqlArgs = hqlQuery.getHQLArgs();
		if (hqlArgs != null) {
			for (int i = 0; i < hqlArgs.length; i++) {
				query.setParameter(i, hqlArgs[i]);
			}
		}

		return query;
	}
}