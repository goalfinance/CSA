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
 * @description: �˻���Ϣ���ݲ���hibernateʵ���� 
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public class HibernateAccountDao extends
		HibernateGenericDao<Account, Long, QueryAccountCond> implements
		AccountDao {
}
