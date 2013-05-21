package cistern.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import cistern.common.AppBizException;

/**
 * @description 遍历内容回叫处理接口定义类
 * @author seabao
 * @project Brick
 * @date 2006-11-8
 */
public interface IterateCallback {
	/**
	 * 处理遍历对象
	 * @param session
	 * @param element
	 * @return true，表示继续遍历，false表示中断遍历
	 * @throws AppBizException
	 * @throws HibernateException
	 */
	public boolean doElement( Session session, Object element ) throws AppBizException, HibernateException;
}