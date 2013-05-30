/**
 * 
 */
package cistern.dao.hibernate.hibernate4;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import cistern.dao.ql.HQLQuery;

/**
 * Utility for using facilities of hibernate4.
 * 
 * @author panqr(panqingrong@gmail.com)
 * 
 */
public class Hibernate4Util {
	public static Query createQuery(Session session, HQLQuery hqlQuery) throws HibernateException{
		Query query = session.createQuery(hqlQuery.getClause());
		Object[] hqlArgs = hqlQuery.getHQLArgs();
		if (hqlArgs != null){
			for (int i = 0;i < hqlArgs.length;i++){
				query.setParameter(i, hqlArgs[i]);
			}
		}
		
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public static List<? extends Serializable> queryElements(Session session, final HQLQuery hqlQuery,
			final long firstResult, final long maxResults) {
		Query query = createQuery(session, hqlQuery);
		query.setFirstResult((int)firstResult);
		if (maxResults > 0){
			query.setMaxResults((int)maxResults);
		}
		return query.list();
	}
	
	public static long countElements(Session session, final HQLQuery hqlQuery){
		StringBuffer countHql = new StringBuffer("select count(*) from ");
		countHql.append(hqlQuery.getFromClause());
		String whereClause = hqlQuery.getWhereClause();
		if (whereClause != null && whereClause.equals("") == false){
			countHql.append(" where ");
			countHql.append(whereClause);
		}
		
		Query query = session.createQuery(countHql.toString());
		Object[] hqlArgs = hqlQuery.getHQLArgs();
		if (hqlArgs != null){
			for (int i = 0; i < hqlArgs.length; i++){
				query.setParameter(i, hqlArgs[i]);
			}
		}
		
		return ((Long)query.list().get(0)).intValue();
	}
}
