/**
 * 
 */
package cistern.test.dao.hibernate.hibernate4;

import org.springframework.stereotype.Repository;

import cistern.dao.hibernate.hibernate4.Hibernate4GenericDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;

/**
 * @author panqr(panqingrong@gmail.com)
 *
 */
@Repository(value="cistern.test.dao.hibernate.hibernate4.AccountGenericDao")
public class Hibernate4AccountGenericDao extends
		Hibernate4GenericDao<Account, Long, QueryAccountCond> implements
		AccountGenericDao {

//	public Hibernate4AccountGenericDao() {
//		super(Account.class, QueryAccountCond.class);
//	}
	

}
