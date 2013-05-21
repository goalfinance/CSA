/**
 * 
 */
package cistern.solutions.acct.dao;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.QueryAccountPeriodCond;

/**
 * @project: cistern
 * @description: 账期信息数据操作接口
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public interface AccountPeriodDao extends
		GenericDao<AccountPeriod, Long, QueryAccountPeriodCond> {

}
