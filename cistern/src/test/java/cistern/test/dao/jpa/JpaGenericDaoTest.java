/**
 * 
 */
package cistern.test.dao.jpa;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cistern.dao.QueryResult;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.QueryAccountCond;
import cistern.test.dao.AccountGenericDao;

/**
 * @author panqingrong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:cistern/test/dao/jpa/applicationContext.xml"})
public class JpaGenericDaoTest {
	
	private AccountGenericDao accountGenericDao;
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void pagingQueryTest() throws Exception{
		//Importing Data for testing.
		Account acct1  = new Account();
		acct1.setAccountName("pagingQueryTest");
		acct1.setAccountNo("0010");
		acct1.setMemo("pagingQueryTest");
		acct1.setParentIdAcct(-1L);
		accountGenericDao.add(acct1);
		
		Account acct2 = new Account();
		acct2.setAccountName("pagingQueryTest");
		acct2.setAccountNo("0011");
		acct2.setMemo("pagingQueryTest");
		acct2.setParentIdAcct(-1L);
		accountGenericDao.add(acct2);
		
		Account acct3 = new Account();
		acct3.setAccountName("pagingQueryTest");
		acct3.setAccountNo("0012");
		acct3.setMemo("pagingQueryTest");
		acct3.setParentIdAcct(-1L);
		accountGenericDao.add(acct3);
		
		Account acct4 = new Account();
		acct4.setAccountName("pagingQueryTest");
		acct4.setAccountNo("0013");
		acct4.setMemo("pagingQueryTest");
		acct4.setParentIdAcct(-1L);
		accountGenericDao.add(acct4);
		
		Account acct5 = new Account();
		acct5.setAccountName("pagingQueryTest");
		acct5.setAccountNo("0014");
		acct5.setMemo("pagingQueryTest");
		acct5.setParentIdAcct(-1L);
		accountGenericDao.add(acct5);
		Assert.assertNotNull(accountGenericDao.get(acct5.getIdAcct()));
		
		
		//Verifying the results.
		QueryAccountCond cond = new QueryAccountCond();
		cond.setAccountName("pagingQueryTest");
		List<Account> accounts = accountGenericDao.query(cond, 0, 2);
		QueryResult<Account> rs = accountGenericDao.queryForResult(cond, 0, 2);
		Assert.assertNotNull(accounts);
		Assert.assertNotNull(rs);
		Assert.assertEquals(2, accounts.size());
		Assert.assertEquals(5, rs.getCount());
		Assert.assertNotNull(rs.getElements());
		Assert.assertEquals(2, rs.getElements().size());
		
		List<Account> accounts1 = accountGenericDao.query(cond, 2, 2);
		QueryResult<Account> rs1 = accountGenericDao.queryForResult(cond, 2, 2);
		Assert.assertNotNull(accounts);
		Assert.assertNotNull(rs1);
		Assert.assertEquals(2, accounts.size());
		Assert.assertEquals(5, rs1.getCount());
		Assert.assertNotNull(rs1.getElements());
		Assert.assertEquals(2, rs1.getElements().size());
		Assert.assertNotNull(accounts1);
		Assert.assertEquals(2, accounts1.size());
		
		List<Account> accounts2 = accountGenericDao.query(cond, 4, 2);
		Assert.assertNotNull(accounts2);
		Assert.assertEquals(1, accounts2.size());
		QueryResult<Account> rs2 = accountGenericDao.queryForResult(cond, 4, 2);
		Assert.assertNotNull(rs2);
		Assert.assertEquals(5, rs2.getCount());
		Assert.assertNotNull(rs2.getElements());
		Assert.assertEquals(1, rs2.getElements().size());
	}
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED)
	public void countTest() throws Exception{
		//Importing Data for testing.
		Account acct1  = new Account();
		acct1.setAccountName("pagingQueryTest");
		acct1.setAccountNo("0010");
		acct1.setMemo("pagingQueryTest");
		acct1.setParentIdAcct(-1L);
		accountGenericDao.add(acct1);
		
		Account acct2 = new Account();
		acct2.setAccountName("pagingQueryTest");
		acct2.setAccountNo("0011");
		acct2.setMemo("pagingQueryTest");
		acct2.setParentIdAcct(-1L);
		accountGenericDao.add(acct2);
		
		Account acct3 = new Account();
		acct3.setAccountName("pagingQueryTest");
		acct3.setAccountNo("0012");
		acct3.setMemo("pagingQueryTest");
		acct3.setParentIdAcct(-1L);
		accountGenericDao.add(acct3);
		
		Account acct4 = new Account();
		acct4.setAccountName("pagingQueryTest");
		acct4.setAccountNo("0013");
		acct4.setMemo("pagingQueryTest");
		acct4.setParentIdAcct(-1L);
		accountGenericDao.add(acct4);
		
		Account acct5 = new Account();
		acct5.setAccountName("pagingQueryTest");
		acct5.setAccountNo("0014");
		acct5.setMemo("pagingQueryTest");
		acct5.setParentIdAcct(-1L);
		accountGenericDao.add(acct5);
		
		//Verifying the results.
		QueryAccountCond cond = new QueryAccountCond();
		cond.setAccountName("pagingQueryTest");
		
		Assert.assertEquals(5, accountGenericDao.count(cond));
	}

	public AccountGenericDao getAccountGernicDao() {
		return accountGenericDao;
	}

	@Resource(name="cistern.test.dao.jpa.AccountGenericDao")
	public void setAccountGernicDao(AccountGenericDao accountGernicDao) {
		this.accountGenericDao = accountGernicDao;
	}
	
	

}
