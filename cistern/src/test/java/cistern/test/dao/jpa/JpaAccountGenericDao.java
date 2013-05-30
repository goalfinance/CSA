/**
 * 
 */
package cistern.test.dao.jpa;

import org.springframework.stereotype.Repository;

import cistern.dao.jpa.JpaGenericDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;
import cistern.test.dao.AccountGenericDao;

/**
 * @author panqingrong
 *
 */
@Repository(value="cistern.test.dao.jpa.AccountGenericDao")
public class JpaAccountGenericDao extends JpaGenericDao<Account, Long, QueryAccountCond> implements
		AccountGenericDao {

}
