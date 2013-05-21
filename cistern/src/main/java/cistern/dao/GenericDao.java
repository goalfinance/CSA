package cistern.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



/**
 * @project: cistern
 * @description: 查询Dao接口定义类
 * @author: seabao
 * @create_time: 2007-3-3
 * @modify_time: 2007-3-3
 */
public interface GenericDao<T, I extends Serializable, Q> extends CrudDao<T, I> {
	/**
	 * 查询获得条件内的对象集
	 * @param cond 条件对象
	 * @param firstResult 起始记录编号（从0开始）
	 * @param maxResults 最大记录数量
	 * @return 对象集
	 */
	public List<T> query(Q cond, long firstResult, long maxResults);

	/**
	 * 获得条件内的第一个对象
	 * @param cond
	 * @return
	 */
	public T getFirst(Q cond);

	/**
	 * 获得条件内的第一个对象（更新读取）
	 * @param cond
	 * @return
	 */
	public T getFirstForUpdate(Q cond);

	/**
	 * 写读取条件内的对象集
	 * @param cond
	 * @return
	 */
	public List<T> loadForUpdate(Q cond);


	/**
	 * 获得条件内的对象数量
	 * @param cond 条件对象
	 * @return 对象数量
	 */
	public long count(Q cond);
	
	/**
	 * 根据提供的查询条件，对指定属性列表求和
	 * @param cond 查询条件
	 * @param propertyNames 指定求和属性列表
	 * @return 求和结果
	 */
	public List<Object> sum(Q cond, List<String> propertyNames);
	
	/**
	 * 按照提供的查询条件，对指定属性的求和
	 * @param cond 查询条件
	 * @param propertyName 指定属性
	 * @return 求和结果
	 */
	public Object simpleSum(Q cond, String propertyName);

	/**
	 * 获得条件内的结果对对象
	 * @param cond
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult<T> queryForResult(Q cond, long firstResult, long maxResults);

	/**
	 * 更新条件内的对象集
	 * @param cond 条件对象
	 * @param values 更新数值映射表
	 */
	public int bulkUpdate(Q cond, Map<String, Object> values);
}
