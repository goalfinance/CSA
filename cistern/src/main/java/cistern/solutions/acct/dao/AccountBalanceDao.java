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
 * @description: 账户余额信息数据操作接口
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public interface AccountBalanceDao extends
		GenericDao<AccountBalance, Long, QueryAccountBalanceCond> {
	/**
	 * 汇总所有总分类账的账户余额
	 * @param idAcctPeriod
	 * @return
	 */
	public AccountBalanceSummaryInfo accountBalanceGLSummary(Long idAcctPeriod);
}
