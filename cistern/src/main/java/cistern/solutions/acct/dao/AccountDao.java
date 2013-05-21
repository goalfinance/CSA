/**
 * 
 */
package cistern.solutions.acct.dao;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;

/**
 * @project: cistern
 * @description: 账户信息数据操作接口
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public interface AccountDao extends GenericDao<Account, Long, QueryAccountCond> {

}
