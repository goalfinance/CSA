/**
 * 
 */
package cistern.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import cistern.dao.CrudDao;

/**
 * DAO class for C.R.U.D. implemented by JPA 2.0
 * 
 * @author panqingrong
 * 
 */
public class JpaCrudDao<T, I extends Serializable> implements CrudDao<T, I> {

	private EntityManager entityManager;

	private Class<? extends T> clazz;

	public JpaCrudDao(Class<? extends T> clazz) {
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public JpaCrudDao() {
		super();
		this.clazz = (Class<? extends T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T get(I id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public T getForUpdate(I id) {
		return loadForUpdate(id);
	}

	@Override
	public T load(I id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public T loadForUpdate(I id) {
		return entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
	}

	@Override
	public T refresh(T o) {
		entityManager.refresh(o);
		return o;
	}

	@Override
	public T refreshForUpdate(T o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(T o) {
		entityManager.persist(o);

	}

	@Override
	public void update(T o) {
		entityManager.merge(o);

	}

	@Override
	public void delete(T o) {
		entityManager.remove(o);

	}

	@Override
	public void delete(I id) {
		entityManager.remove(this.get(id));

	}

	@Override
	public void flush() {
		entityManager.flush();

	}

	@Override
	public void evict(T o) {
		entityManager.detach(o);

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
