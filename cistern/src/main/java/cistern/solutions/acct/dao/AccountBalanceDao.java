/**
 * 
 */
package cistern.solutions.acct.dao;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.AccountBalance;
import cistern.solutions.acct.domain.AccountBalanceSummaryInfo;
import cistern.solutions.acct.domain.QueryAccountBalanceCond;

/**
 * @project: cistern
 * @description: �˻������Ϣ���ݲ����ӿ�
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public interface AccountBalanceDao extends
		GenericDao<AccountBalance, Long, QueryAccountBalanceCond> {
	/**
	 * ���������ܷ����˵��˻����
	 * @param idAcctPeriod
	 * @return
	 */
	public AccountBalanceSummaryInfo accountBalanceGLSummary(Long idAcctPeriod);
}
