/**
 * 
 */
package cistern.test.dao.jpa;

import org.springframework.stereotype.Repository;

import cistern.dao.jpa.JpaCrudDao;
import cistern.solutions.acct.domain.Account;
import cistern.test.dao.AccountCrudDao;

/**
 * @author panqingrong
 *
 */
//@Repository(value="cistern.test.dao.jpa.AccountCrudDao")
public class JpaAccountCrudDao extends JpaCrudDao<Account, Long> implements AccountCrudDao{

}
