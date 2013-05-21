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
	 * ��������ڵĵ�һ������
	 * @param cond
	 * @return
	 */
	public T getFirst(Q cond);

	/**
	 * ��������ڵĵ�һ�����󣨸��¶�ȡ��
	 * @param cond
	 * @return
	 */
	public T getFirstForUpdate(Q cond);

	/**
	 * д��ȡ�����ڵĶ���
	 * @param cond
	 * @return
	 */
	public List<T> loadForUpdate(Q cond);


	/**
	 * ��������ڵĶ�������
	 * @param cond ��������
	 * @return ��������
	 */
	public long count(Q cond);
	
	/**
	 * �����ṩ�Ĳ�ѯ��������ָ�������б����
	 * @param cond ��ѯ����
	 * @param propertyNames ָ����������б�
	 * @return ��ͽ��
	 */
	public List<Object> sum(Q cond, List<String> propertyNames);
	
	/**
	 * �����ṩ�Ĳ�ѯ��������ָ�����Ե����
	 * @param cond ��ѯ����
	 * @param propertyName ָ������
	 * @return ��ͽ��
	 */
	public Object simpleSum(Q cond, String propertyName);

	/**
	 * ��������ڵĽ���Զ���
	 * @param cond
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult<T> queryForResult(Q cond, long firstResult, long maxResults);

	/**
	 * ���������ڵĶ���
	 * @param cond ��������
	 * @param values ������ֵӳ���
	 */
	public int bulkUpdate(Q cond, Map<String, Object> values);
}
