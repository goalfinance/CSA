/**
 * 
 */
package cistern.test.dao;

import cistern.solutions.acct.domain.Account;

/**
 * @author panqr(panqingrong@gmail.com)
 *
 */
public interface AccountService {
	
	public void add(Account acct);
	
	public Account getForUpdate(Long id);
}
