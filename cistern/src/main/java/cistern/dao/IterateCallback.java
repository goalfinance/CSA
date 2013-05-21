package cistern.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import cistern.common.AppBizException;

/**
 * @description �������ݻؽд���ӿڶ�����
 * @author seabao
 * @project Brick
 * @date 2006-11-8
 */
public interface IterateCallback {
	/**
	 * �����������
	 * @param session
	 * @param element
	 * @return true����ʾ����������false��ʾ�жϱ���
	 * @throws AppBizException
	 * @throws HibernateException
	 */
	public boolean doElement( Session session, Object element ) throws AppBizException, HibernateException;
}