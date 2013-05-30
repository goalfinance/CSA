package cistern.dao.hibernate.hibernate3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cistern.dao.GenericDao;
import cistern.dao.QueryResult;

import cistern.dao.ql.HQLQuery;
import cistern.dao.ql.SimpleQueryFactory;
import cistern.dao.ql.impl.ConditionMeta;
import cistern.dao.ql.impl.SimpleQueryDefinition;

public class HibernateGenericDao<T, I extends Serializable, Q> extends HibernateCrudDao<T, I> implements
		GenericDao<T, I, Q> {
	/**
	 * SimpleQuery工厂
	 */
	protected SimpleQueryFactory simpleQueryFactory;

	/**
	 * 查询条件类
	 */
	protected Class<? extends Q> conditionClazz;

	public HibernateGenericDao(Class<? extends T> clazz, Class<? extends Q> qClazz) {
		super(clazz);
		conditionClazz = qClazz;
	}

	@SuppressWarnings("unchecked")
	public HibernateGenericDao() {
		clazz = (Class<? extends T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		conditionClazz = (Class<? extends Q>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[2];
	}

	@PostConstruct
	public void init() {
		simpleQueryFactory.registerCondition(conditionClazz);
	}
	
	public Object simpleSum(Q cond, String propertyName) {
		if (propertyName == null) {
			return null;
		}
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add(propertyName);
		List<Object> results = sum(cond, propertyNames);
		if (!results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> sum(Q cond, List<String> propertyNames) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		if (propertyNames == null || propertyNames.isEmpty()) {
			return null;
		}
		StringBuffer sumClause = new StringBuffer();
		SimpleQueryDefinition definition = simpleQueryFactory.getSimpleQueryDefinition(cond.getClass().getName());
		if (definition == null) {
			return null;
		}
		for (String propertyName : propertyNames) {
			sumClause.append("sum(")
					.append(definition.getDefaultPersistenceAlias())
					.append(".")
					.append(propertyName)
					.append("),");
		}
		if (sumClause.length() > 0) {
			sumClause.deleteCharAt(sumClause.length() - 1);
		}
		hqlQuery.setSelectClause(sumClause.toString());
		List<Object> results = HibernateUtil.queryElements(getHibernateTemplate(), hqlQuery, 0, -1);
		
		return results;
		
	}
	
	public long count(Q cond) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		
		return HibernateUtil.countElements(this.getHibernateTemplate(), hqlQuery);
	}

	@SuppressWarnings("unchecked")
	public List<T> query(Q cond, long firstResult, long maxResults) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		return HibernateUtil.queryElements(getHibernateTemplate(), hqlQuery, firstResult, maxResults);
	}

	public QueryResult<T> queryForResult(Q cond, long firstResult, long maxResults) {
		QueryResult<T> res = new QueryResult<T>();
		res.setElements(query(cond, firstResult, maxResults));
		res.setCount(count(cond));
		res.setFirst(firstResult);

		return res;
	}

	public T getFirst(Q cond) {
		List<T> list = query(cond, 0, 1);
		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	public T getFirstForUpdate(Q cond) {
		List<T> list = loadForUpdate(cond);
		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	public int bulkUpdate(Q cond, Map<String, Object> values) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		ConditionMeta condMeta = simpleQueryFactory.getConditionMeta(cond.getClass());
		StringBuffer clause = new StringBuffer();
		clause.append("update ");
		clause.append(condMeta.getSimpleQueryDefinition().getCondition().getFromClause());
		clause.append(" set ");

		List<Object> parameters = new ArrayList<Object>();
		for (Iterator<String> it = values.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			Object value = values.get(name);

			clause.append(condMeta.getDefaultPersistenceAlias());
			clause.append(".");
			clause.append(name);
			clause.append("=? ");

			if (it.hasNext()) {
				clause.append(", ");
			}

			parameters.add(value);
		}

		clause.append(" where ");
		clause.append(hqlQuery.getWhereClause());
		parameters.addAll(hqlQuery.getParameters());

		return getHibernateTemplate().bulkUpdate(clause.toString(), parameters.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<T> loadForUpdate(Q cond) {
		if (inTx() == false) {
			throw new RuntimeException("loadForUpdate must be called in tx.");
		}

		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		ConditionMeta condMeta = simpleQueryFactory.getConditionMeta(cond.getClass());
		return HibernateUtil.loadElementsForUpdate(getHibernateTemplate(), hqlQuery, condMeta
				.getDefaultPersistenceAlias());
	}

	public SimpleQueryFactory getSimpleQueryFactory() {
		return simpleQueryFactory;
	}

	@Autowired
	public void setSimpleQueryFactory(@Qualifier("simpleQueryFactory") SimpleQueryFactory simpleQueryFactory) {
		this.simpleQueryFactory = simpleQueryFactory;
	}

}
