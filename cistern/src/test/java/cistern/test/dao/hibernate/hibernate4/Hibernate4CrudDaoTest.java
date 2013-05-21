/**
 * 
 */
package cistern.test.dao.hibernate.hibernate4;

import javax.annotation.Resource;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cistern.solutions.acct.domain.Account;

/**
 * @author panqr(panqingrong@gmail.com)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:cistern/test/dao/hibernate/hibernate4/applicationContext.xml"})
public class Hibernate4CrudDaoTest {
	
	private AccountCrudDao accountDao;
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void crudTest() throws Exception{
		Account cashAccount = new Account();
		cashAccount.setAccountNo("0001");
		cashAccount.setAccountName("现金账户");
		cashAccount.setGeneralLedgerFlag(true);
		cashAccount.setMemo("现金账户总账");
		cashAccount.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		
		//Test create logic.
		accountDao.add(cashAccount);
		Account cashAcctInDb = accountDao.get(cashAccount.getIdAcct());
		Assert.assertEquals(cashAcctInDb.toString(), cashAccount.toString());
		
		//Test update logic.
		cashAcctInDb.setMemo("现金账户总账1");
		accountDao.update(cashAcctInDb);
		Account cashAcctAfterUpdate = accountDao.get(cashAcctInDb.getIdAcct());
		Assert.assertEquals(cashAcctInDb.toString(), cashAcctAfterUpdate.toString());
		
		//Test delete by domain object.
		accountDao.delete(cashAcctAfterUpdate);
		Assert.assertNotNull(cashAcctAfterUpdate.getIdAcct());
		System.out.println("idAcct='" + cashAcctAfterUpdate.getIdAcct() + "'");
		Account cashAcctAfterDelete = accountDao.get(cashAcctAfterUpdate.getIdAcct());
		Assert.assertNull(cashAcctAfterDelete);
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void getForUpdateTest() throws Exception{
		Account cashAccount = new Account();
		cashAccount.setAccountNo("0001");
		cashAccount.setAccountName("现金账户");
		cashAccount.setGeneralLedgerFlag(true);
		cashAccount.setMemo("现金账户总账");
		cashAccount.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		accountDao.add(cashAccount);
		
		Account cashAccountInDb = (Account)accountDao.getForUpdate(cashAccount.getIdAcct());
		Assert.assertNotNull(cashAccountInDb);
		Assert.assertEquals(cashAccount.toString(), cashAccountInDb.toString());
	}
	
	
	
	

	/**
	 * @return the accountDao
	 */
	public AccountCrudDao getAccountDao() {
		return accountDao;
	}

	/**
	 * @param accountDao the accountDao to set
	 */
	@Resource(name="cistern.test.dao.hibernate.hibernate4.AccountCrudDao")
	public void setAccountDao(AccountCrudDao accountDao) {
		this.accountDao = accountDao;
	}
	
	public static void main(String[] args) throws Exception{
		ApplicationContext ap = new FileSystemXmlApplicationContext("classpath:cistern/test/dao/hibernate4/applicationContext.xml");
		
		AccountService as = (AccountService)ap.getBean("cistern.test.AccountService");
		Account cashAccount = new Account();
		cashAccount.setAccountNo("0001");
		cashAccount.setAccountName("现金账户");
		cashAccount.setGeneralLedgerFlag(true);
		cashAccount.setMemo("现金账户总账");
		cashAccount.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		as.add(cashAccount);
		
		as.getForUpdate(cashAccount.getIdAcct());
		
		System.out.println("idAcct='" + cashAccount.getIdAcct() + "'");
	}
	

}
