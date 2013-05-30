/**
 * 
 */
package cistern.test.dao.hibernate.hibernate4;

import org.springframework.stereotype.Repository;

import cistern.dao.hibernate.hibernate4.Hibernate4CrudDao;
import cistern.solutions.acct.domain.Account;
import cistern.test.dao.AccountCrudDao;

/**
 * @author panqr(panqingrong@gmail.com)
 * 
 */
@Repository(value="cistern.test.dao.hibernate.hibernate4.AccountCrudDao")
public class Hibernate4AccountCrudDao extends Hibernate4CrudDao<Account, Long>
		implements AccountCrudDao {

}
