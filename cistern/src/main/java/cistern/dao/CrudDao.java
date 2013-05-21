package cistern.dao;

import java.io.Serializable;
import java.util.List;
 
/**
 * @project: cistern
 * @description: CrudDao�ӿڶ�����
 * @author: seabao
 * @create_time: 2007-3-2
 * @modify_time: 2007-3-2
 */
public interface CrudDao<T, I extends Serializable> {
	/**
	 * ���������Ӧ�Ķ���
	 * @param id ����ֵ
	 * @return ����
	 */
	public T get(I id);
	
	/**
	 * ���������Ӧ�Ķ��󣨸��¶�ȡ��
	 * @param id
	 * @return
	 */
	public T getForUpdate(I id);
	
	/**
	 * ���������Ӧ�Ķ���
	 * @param id ����ֵ
	 * @return ����
	 */
	public T load(I id);
	
	/**
	 * ���������Ӧ�Ķ��󣨸��¶�ȡ��
	 * @param id
	 * @return
	 */
	public T loadForUpdate(I id);
	
	/**
	 * ˢ�¶���
	 * @param o
	 */
	public T refresh(T o);
	
	/**
	 * ˢ�¶��󣨸��¶�ȡ��
	 * @param o
	 */
	public T refreshForUpdate(T o);
	
	/**
	 * ������ж���
	 * @return �����б�
	 */
	public List<T> getAll();
	
	/**
	 * ���Ӷ���
	 * @param o ����
	 */
	public void add(T o);
	
	/**
	 * �޸Ķ���
	 * @param o ����
	 */
	public void update(T o);
	
	/**
	 * ɾ������
	 * @param o ����
	 */
	public void delete(T o);
	
	/**
	 * ɾ��������Ӧ�Ķ���
	 * @param id ����ֵ
	 */
	public void delete(I id);
	
	/**
	 * ǿ��ˢ�µ����ݿ�
	 */
	public void flush();
	
	/**
	 * ����һ������Ķ���
	 * @param o
	 */
	public void evict(T o);
}
