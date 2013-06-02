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
	 * 获得条件内的对象数量
	 * @param cond 条件对象
	 * @return 对象数量
	 */
	public long count(Q cond);
	
	
	/**
	 * 获得条件内的结果对对象
	 * @param cond
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult<T> queryForResult(Q cond, long firstResult, long maxResults);

	
}
