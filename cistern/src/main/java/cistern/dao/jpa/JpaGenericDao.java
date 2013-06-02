/**
 * 
 */
package cistern.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import cistern.dao.GenericDao;
import cistern.dao.QueryResult;
import cistern.dao.ql.HQLQuery;
import cistern.dao.ql.impl.SimpleQueryFactory;
import cistern.dao.ql.impl.SimpleQueryFactoryImpl;

/**
 * @author panqingrong
 * 
 */
public class JpaGenericDao<T, I extends Serializable, Q> extends
		JpaCrudDao<T, I> implements GenericDao<T, I, Q> {

	private SimpleQueryFactory simpleQueryFactory = new SimpleQueryFactoryImpl();

	private Class<? extends Q> conditionClazz;

	public JpaGenericDao(Class<? extends T> clazz,
			Class<? extends Q> conditionClazz) {
		super(clazz);
		this.conditionClazz = conditionClazz;
	}

	@SuppressWarnings("unchecked")
	public JpaGenericDao() {
		super();
		conditionClazz = (Class<? extends Q>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[2];

	}

	@PostConstruct
	public void init() {
		simpleQueryFactory.registerCondition(conditionClazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> query(Q cond, long firstResult, long maxResults) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);

		return (List<T>) JpaUtil.queryElements(getEntityManager(), hqlQuery,
				firstResult, maxResults);
	}

	@Override
	public long count(Q cond) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		return JpaUtil.countElements(getEntityManager(), hqlQuery);
	}

	@Override
	public QueryResult<T> queryForResult(Q cond, long firstResult,
			long maxResults) {
		QueryResult<T> qrs = new QueryResult<T>();
		qrs.setElements(query(cond, firstResult, maxResults));
		qrs.setCount(count(cond));
		qrs.setFirst(firstResult);

		return qrs;
	}

	public SimpleQueryFactory getSimpleQueryFactory() {
		return simpleQueryFactory;
	}

	public void setSimpleQueryFactory(SimpleQueryFactory simpleQueryFactory) {
		this.simpleQueryFactory = simpleQueryFactory;
	}

}
