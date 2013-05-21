/**
 * 
 */
package cistern.test.dao.hibernate.hibernate4;

import cistern.dao.GenericDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;

/**
 * @author panqr(panqingrong@gmail.com)
 *
 */
public interface AccountGenericDao extends
		GenericDao<Account, Long, QueryAccountCond> {

}
