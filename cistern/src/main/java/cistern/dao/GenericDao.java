package cistern.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



/**
 * @project: cistern
 * @description: ��ѯDao�ӿڶ�����
 * @author: seabao
 * @create_time: 2007-3-3
 * @modify_time: 2007-3-3
 */
public interface GenericDao<T, I extends Serializable, Q> extends CrudDao<T, I> {
	/**
	 * ��ѯ��������ڵĶ���
	 * @param cond ��������
	 * @param firstResult ��ʼ��¼��ţ���0��ʼ��
	 * @param maxResults ����¼����
	 * @return ����
	 */
	public List<T> query(Q cond, long firstResult, long maxResults);

	/**
	 * ��������ڵĶ�������
	 * @param cond ��������
	 * @return ��������
	 */
	public long count(Q cond);
	
	
	/**
	 * ��������ڵĽ���Զ���
	 * @param cond
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult<T> queryForResult(Q cond, long firstResult, long maxResults);

	
}
