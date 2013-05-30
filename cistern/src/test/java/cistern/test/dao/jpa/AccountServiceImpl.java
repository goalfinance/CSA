/**
 * 
 */
package cistern.test.dao.jpa;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cistern.solutions.acct.domain.Account;
import cistern.test.dao.AccountCrudDao;
import cistern.test.dao.AccountService;

/**
 * @author panqr(panqingrong@gmail.com)
 *
 */
@Service(value="cistern.test.jpa.AccountService")
public class AccountServiceImpl implements AccountService {

	private AccountCrudDao accountCrudDao;
	
	
	/* (non-Javadoc)
	 * @see cistern.test.dao.hibernate4.AccountService#add(cistern.solutions.acct.domain.Account)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void add(Account acct) {
		accountCrudDao.add(acct);

	}
	
	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Account getForUpdate(Long id) {
		Account account = accountCrudDao.loadForUpdate(id);
		try {
			Thread.sleep(10 * 60 * 1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}



	public AccountCrudDao getAccountDao() {
		return accountCrudDao;
	}
	@Resource(name="cistern.test.dao.jpa.AccountCrudDao")
	public void setAccountDao(AccountCrudDao accountDao) {
		this.accountCrudDao = accountDao;
	}

}
