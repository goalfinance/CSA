/**
 * 
 */
package cistern.solutions.acct.service;

import java.math.BigDecimal;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.AccountBalance;

/**
 * @project: cistern
 * @description: �˻�����ӿ�
 * @author: panqr
 * @create_time: 2011-4-23
 *
 */
public interface AccountService {
	
	/**
	 * �����˻��Ż�ȡ״̬�˻���Ϣ
	 * @param accountNo
	 * @return
	 * @throws AppBizException
	 */
	public Account getByAccountNo(String accountNo) throws AppBizException;
	
	/**
	 * ��ȡָ���˻���ǰ���ڵ������Ϣ
	 * @param accountNo
	 * @return
	 * @throws AppBizException
	 */
	public AccountBalance getActiveAccountBalance(String accountNo) throws AppBizException;
	
	/**
	 * ����˻�
	 * @param AccountNo
	 * @param idAcctPeriod
	 * @param amt
	 * @throws AppBizException
	 */
	public void debitAccount(String accountNo, Long idAcctPeriod, BigDecimal amt) throws AppBizException;
	
	/**
	 * �����˻�
	 * @param accountNo
	 * @param idAcctPeriod
	 * @param amt
	 * @throws AppBizException
	 */
	public void creditAccount(String accountNo, Long idAcctPeriod, BigDecimal amt) throws AppBizException;
	
	/**
	 * ��ָ�����ڵ��˻�����������ƽ��
	 * ���㹫ʽΪ��
	 * 
	 * 1��ȫ���˻��Ľ跽�ڳ����ϼ�������ȫ���˻��Ĵ����ڳ����ϼ���
	 * 2��ȫ���˻��Ľ跽������ϼƵ���ȫ���˻��Ĵ���������ϼ�
	 * 3��ȫ���˻��Ľ跽��ĩ���ϼƵ���ȫ���˻��Ĵ�����ĩ���ϼ�
	 * 
	 * @param idAcctPeriod
	 * @throws AppBizException
	 */
	public boolean trialBalancing(Long idAcctPeriod) throws AppBizException;

}
