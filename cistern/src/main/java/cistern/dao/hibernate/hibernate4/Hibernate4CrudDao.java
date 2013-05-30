/**
 * 
 */
package cistern.dao.hibernate.hibernate4;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cistern.dao.CrudDao;

/**
 * DAO for C.R.U.D. implemented by Hibernate 4.
 * 
 * @author panqr(panqingrong@gmail.com)
 * 
 */
public class Hibernate4CrudDao<T, I extends Serializable> implements
		CrudDao<T, I> {

	private SessionFactory sessionFactory;

	private Class<? extends T> clazz;
	
	public Hibernate4CrudDao(Class<? extends T> clazz) {
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public Hibernate4CrudDao() {
		super();
		clazz = (Class<? extends T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(I id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getForUpdate(I id) {
		Transaction tx = getSession().getTransaction();
		if (tx == null || tx.isActive() == false) {
			throw new RuntimeException(
					"This method must be used in transaction.");
		}

		Object o = getSession().get(clazz, id, LockOptions.UPGRADE);
		return (T) o;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T load(I id) {
		return (T) getSession().load(clazz, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T loadForUpdate(I id) {
		Transaction tx = getSession().getTransaction();
		if (tx == null || tx.isActive() == false) {
			throw new RuntimeException(
					"This method must be used in transaction.");
		}

		Object o = getSession().load(clazz, id, LockOptions.UPGRADE);
		return (T) o;
	}

	@Override
	public T refresh(T o) {
		getSession().refresh(o);
		return o;
	}

	@Override
	public T refreshForUpdate(final T o) {
		Transaction tx = getSession().getTransaction();
		if (tx == null || tx.isActive() == false) {
			throw new RuntimeException(
					"This method must be used in transaction.");
		}

		getSession().refresh(o, LockOptions.UPGRADE);

		return o;
	}

	@Override
	@Deprecated
	public List<T> getAll() {
		return null;
	}

	@Override
	public void add(T o) {
		getSession().save(o);

	}

	@Override
	public void update(T o) {
		getSession().update(o);

	}

	@Override
	public void delete(T o) {
		getSession().delete(o);

	}

	@Override
	public void delete(I id) {
		delete(get(id));

	}

	@Override
	public void flush() {
		getSession().flush();

	}

	@Override
	public void evict(T o) {
		getSession().evict(o);
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	@Resource(name = "hibernate4SessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
