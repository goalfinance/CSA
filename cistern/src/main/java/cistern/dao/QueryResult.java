package cistern.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @project: cistern
 * @description: 查询结果类
 * @author: seabao
 * @create_time: 2007-4-19
 * @modify_time: 2007-4-19
 */
public class QueryResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询结果记录数量
	 */
	private long count;
	
	/**
	 * 起始记录数
	 */
	private long first;
	
	/**
	 * 单元列表
	 */
	private List<T> elements;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public long getFirst() {
		return first;
	}

	public void setFirst(long first) {
		this.first = first;
	}
	
}
