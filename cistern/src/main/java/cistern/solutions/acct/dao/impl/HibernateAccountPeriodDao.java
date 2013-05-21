/**
 * 
 */
package cistern.solutions.acct.dao.impl;

import cistern.dao.hibernate.hibernate3.HibernateGenericDao;
import cistern.solutions.acct.dao.AccountPeriodDao;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.QueryAccountPeriodCond;

/**
 * @project: cistern
 * @description: 
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public class HibernateAccountPeriodDao extends
		HibernateGenericDao<AccountPeriod, Long, QueryAccountPeriodCond>
		implements AccountPeriodDao {

}
