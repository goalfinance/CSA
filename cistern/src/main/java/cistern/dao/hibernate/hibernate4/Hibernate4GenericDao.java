/**
 * 
 */
package cistern.dao.hibernate.hibernate4;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import cistern.dao.GenericDao;
import cistern.dao.QueryResult;
import cistern.dao.ql.HQLQuery;
import cistern.dao.ql.impl.SimpleQueryFactory;
import cistern.dao.ql.impl.SimpleQueryFactoryImpl;

/**
 * Generic Dao implementation for hibernate 4 including some query method.
 * 
 * @author panqr(panqingrong@gmail.com)
 * 
 */
public class Hibernate4GenericDao<T, I extends Serializable, Q> extends
		Hibernate4CrudDao<T, I> implements GenericDao<T, I, Q> {

	private SimpleQueryFactory simpleQueryFactory = new SimpleQueryFactoryImpl();

	private Class<? extends Q> conditionClazz;

	public Hibernate4GenericDao(Class<? extends T> clazz,
			Class<? extends Q> condClazz) {
		super(clazz);
		this.conditionClazz = condClazz;
	}

	@SuppressWarnings("unchecked")
	public Hibernate4GenericDao() {
		super();
		this.conditionClazz = (Class<? extends Q>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	@PostConstruct
	public void init(){
		simpleQueryFactory.registerCondition(conditionClazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(Q cond, long firstResult, long maxResults) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		return (List<T>) Hibernate4Util.queryElements(getSession(), hqlQuery,
				firstResult, maxResults);
	}

	@Override
	@Deprecated
	public T getFirst(Q cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public T getFirstForUpdate(Q cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<T> loadForUpdate(Q cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Q cond) {
		HQLQuery hqlQuery = simpleQueryFactory.genHQLQuery(cond);
		return Hibernate4Util.countElements(getSession(), hqlQuery);
	}

	@Override
	@Deprecated
	public List<Object> sum(Q cond, List<String> propertyNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public Object simpleSum(Q cond, String propertyName) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	@Deprecated
	public int bulkUpdate(Q cond, Map<String, Object> values) {
		// TODO Auto-generated method stub
		return 0;
	}

	public SimpleQueryFactory getSimpleQueryFactory() {
		return simpleQueryFactory;
	}

	public void setSimpleQueryFactory(SimpleQueryFactory simpleQueryFactory) {
		this.simpleQueryFactory = simpleQueryFactory;
	}

}
