/**
 * 
 */
package cistern.solutions.acct.service;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.AccountPeriod;

/**
 * @project: cistern
 * @description: ������ڷ���ӿ�
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public interface AccountPeriodService {
	
	/**
	 * �����ڲ���Ż�ȡ������Ϣ
	 * @param idAcctPeriod
	 * @return
	 * @throws AppBizException
	 */
	public AccountPeriod getById(Long idAcctPeriod) throws AppBizException;
	
	/**
	 * ��ȡ��ǰ������Ϣ
	 * @return
	 * @throws AppBizException
	 */
	public AccountPeriod getActiveAccountPeriod() throws AppBizException;
	
	/**
	 * �ж�ָ�������ڱ���Ƿ�Ϊ��ǰ����
	 * @param idAccPeriod
	 * @throws AppBizException
	 */
	public boolean isActiveAccountPeriod(Long idAcctPeriod) throws AppBizException;

}
