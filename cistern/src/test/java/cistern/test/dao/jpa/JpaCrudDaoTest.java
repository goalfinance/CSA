package cistern.test.dao.jpa;

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
import cistern.test.dao.AccountCrudDao;
import cistern.test.dao.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:cistern/test/dao/jpa/applicationContext.xml"})
public class JpaCrudDaoTest {
	
	private AccountCrudDao accountDao;
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void testCrud() throws Exception{
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
	}

	public AccountCrudDao getAccountCrudDao() {
		return accountDao;
	}

	@Resource(name="cistern.test.dao.jpa.AccountCrudDao")
	public void setAccountCrudDao(AccountCrudDao accountCrudDao) {
		this.accountDao = accountCrudDao;
	}

	public static void main(String[] args) throws Exception{
		ApplicationContext ap = new FileSystemXmlApplicationContext("classpath:cistern/test/dao/jpa/applicationContext.xml");
		
		AccountService as = (AccountService)ap.getBean("cistern.test.jpa.AccountService");
		Account cashAccount = new Account();
		cashAccount.setAccountNo("0001");
		cashAccount.setAccountName("现金账户");
		cashAccount.setGeneralLedgerFlag(true);
		cashAccount.setMemo("现金账户总账");
		cashAccount.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		//as.add(cashAccount);
		
		
		AccountCrudDao accountDao = (AccountCrudDao)ap.getBean("cistern.test.dao.jpa.AccountCrudDao");
		accountDao.add(cashAccount);
		System.out.println("idAcct='" + cashAccount.getIdAcct() + "'");
		accountDao.loadForUpdate(1800L);		
		
		Thread.sleep(60 * 1000L * 60);
	}
}
