package cistern.dao.hibernate.hibernate3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cistern.dao.CrudDao;



/**
 * @project: Cistern
 * @description: 基于Hibernate实现的CrudDao类
 * @author: seabao
 * @create_time: 2007-3-2
 * @modify_time: 2007-3-2
 */
public class HibernateCrudDao<T, I extends Serializable> extends HibernateDaoSupport implements CrudDao<T, I> {
	protected Class<? extends T> clazz;

	public HibernateCrudDao(Class<? extends T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public HibernateCrudDao() {
		clazz = (Class<? extends T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void evict(T o) {
		getHibernateTemplate().evict(o);
	}

	public void add(T o) {
		getHibernateTemplate().save(o);
	}

	public void delete(I id) {
		delete(get(id));
	}

	public void delete(T o) {
		getHibernateTemplate().delete(o);
	}

	@SuppressWarnings("unchecked")
	public T get(I id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public T load(I id) {
		return (T) getHibernateTemplate().load(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return (List<T>) getHibernateTemplate().loadAll(clazz);
	}

	public void update(T o) {
		getHibernateTemplate().update(o);
	}

	@SuppressWarnings("unchecked")
	protected boolean inTx() {
		return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) {
				Transaction txn = sess.getTransaction();
				if (txn == null || txn.isActive() == false) {
					return false;
				}

				return true;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public T getForUpdate(final I id) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) {
				Transaction txn = sess.getTransaction();
				if (txn == null || txn.isActive() == false) {
					throw new RuntimeException("GetForUpdate must be called in tx.");
				}

				sess.flush();
				Object o = sess.get(clazz, id, LockMode.UPGRADE);
				if (o != null) {
					sess.refresh(o, LockMode.UPGRADE);
				}

				return o;
			}
		});
	}

	public T refresh(T o) {
		getHibernateTemplate().refresh(o);
		return o;
	}

	public T refreshForUpdate(final T o) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) {
				Transaction txn = sess.getTransaction();
				if (txn == null || txn.isActive() == false) {
					throw new RuntimeException("refreshForUpdate must be called in tx.");
				}

				getHibernateTemplate().refresh(o, LockMode.UPGRADE);
				return null;
			}
		});

		return o;
	}

	@SuppressWarnings("unchecked")
	public T loadForUpdate(final I id) {
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session sess) {
				Transaction txn = sess.getTransaction();
				if (txn == null || txn.isActive() == false) {
					throw new RuntimeException("loadForUpdate must be called in tx.");
				}

				sess.flush();
				Object o = sess.load(clazz, id, LockMode.UPGRADE);
				if (o != null) {
					sess.refresh(o, LockMode.UPGRADE);
				}

				return o;
			}
		});
	}

	@Autowired(required = false)
	public void setSessionFactory2(@Qualifier("hibernateSessionFactory")
	SessionFactory sf) {
		super.setSessionFactory(sf);
	}
}
