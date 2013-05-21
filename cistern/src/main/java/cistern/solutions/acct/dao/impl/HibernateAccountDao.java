/**
 * 
 */
package cistern.solutions.acct.dao.impl;

import cistern.dao.hibernate.hibernate3.HibernateGenericDao;
import cistern.solutions.acct.dao.AccountDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;

/**
 * @project: cistern
 * @description: 账户信息数据操作hibernate实现类 
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public class HibernateAccountDao extends
		HibernateGenericDao<Account, Long, QueryAccountCond> implements
		AccountDao {
}
