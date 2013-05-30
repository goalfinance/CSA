/**
 * 
 */
package cistern.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cistern.dao.ql.HQLQuery;

/**
 * @author panqingrong
 * 
 */
public class JpaUtil {
	public static Query createQuery(EntityManager em, HQLQuery hqlQuery) {
		Query query = em.createQuery(hqlQuery.getClause());
		Object[] hqlArgs = hqlQuery.getHQLArgs();
		if (hqlArgs != null) {
			for (int i = 0; i < hqlArgs.length; i++) {
				// The ordinal parameters of JP-QL are 1-based.
				query.setParameter(i + 1, hqlArgs[i]);
			}
		}

		return query;
	}

	@SuppressWarnings("unchecked")
	public static List<? extends Serializable> queryElements(EntityManager em,
			final HQLQuery hqlQuery, final long firstResult,
			final long maxResults) {
		Query query = createQuery(em, hqlQuery);
		query.setFirstResult((int) firstResult);
		if (maxResults > 0) {
			query.setMaxResults((int) maxResults);
		}
		return query.getResultList();
	}

	public static long countElements(EntityManager em, final HQLQuery hqlQuery) {
		StringBuffer countHql = new StringBuffer("select count(*) from ");
		countHql.append(hqlQuery.getFromClause());
		String whereClause = hqlQuery.getWhereClause();
		if (whereClause != null && whereClause.equals("") == false) {
			countHql.append(" where ");
			countHql.append(whereClause);
		}

		Query query = em.createQuery(countHql.toString());
		Object[] hqlArgs = hqlQuery.getHQLArgs();
		if (hqlArgs != null) {
			for (int i = 0; i < hqlArgs.length; i++) {
				// The ordinal parameters of JP-QL are 1-based.
				query.setParameter(i + 1, hqlArgs[i]);
			}
		}

		return ((Long) query.getResultList().get(0)).intValue();
	}
}
