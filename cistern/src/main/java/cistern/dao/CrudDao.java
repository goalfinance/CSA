package cistern.dao;

import java.io.Serializable;
import java.util.List;
 
/**
 * @project: cistern
 * @description: CrudDao接口定义类
 * @author: seabao
 * @create_time: 2007-3-2
 * @modify_time: 2007-3-2
 */
public interface CrudDao<T, I extends Serializable> {
	/**
	 * 获得主键对应的对象
	 * @param id 主键值
	 * @return 对象
	 */
	public T get(I id);
	
	/**
	 * 获得主键对应的对象（更新读取）
	 * @param id
	 * @return
	 */
	public T getForUpdate(I id);
	
	/**
	 * 获得主键对应的对象
	 * @param id 主键值
	 * @return 对象
	 */
	public T load(I id);
	
	/**
	 * 获得主键对应的对象（更新读取）
	 * @param id
	 * @return
	 */
	public T loadForUpdate(I id);
	
	/**
	 * 刷新对象
	 * @param o
	 */
	public T refresh(T o);
	
	/**
	 * 刷新对象（更新读取）
	 * @param o
	 */
	public T refreshForUpdate(T o);
	
	/**
	 * 获得所有对象
	 * @return 对象列表
	 */
	public List<T> getAll();
	
	/**
	 * 增加对象
	 * @param o 对象
	 */
	public void add(T o);
	
	/**
	 * 修改对象
	 * @param o 对象
	 */
	public void update(T o);
	
	/**
	 * 删除对象
	 * @param o 对象
	 */
	public void delete(T o);
	
	/**
	 * 删除主键对应的对象
	 * @param id 主键值
	 */
	public void delete(I id);
	
	/**
	 * 强制刷新到数据库
	 */
	public void flush();
	
	/**
	 * 驱除一级缓存的对象
	 * @param o
	 */
	public void evict(T o);
}
