package cistern.test.soluntions.acct;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cistern.common.AppBizException;
import cistern.solutions.acct.dao.AccountBalanceDao;
import cistern.solutions.acct.dao.AccountDao;
import cistern.solutions.acct.dao.AccountPeriodDao;
import cistern.solutions.acct.dao.JournalDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.AccountBalance;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.Journal;
import cistern.solutions.acct.domain.JournalDetail;
import cistern.solutions.acct.service.AccountPeriodService;
import cistern.solutions.acct.service.AccountService;
import cistern.solutions.acct.service.JournalService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cistern/test/solutions/acct/applicationContext.xml"})
public class AccountServiceTest {
	
	private AccountDao accountDao;
	
	private AccountPeriodDao accountPeriodDao;
	
	private AccountBalanceDao accountBalanceDao;
	
	private JournalDao journalDao;
	
	private AccountService accountService;
	
	private AccountPeriodService  accountPeriodService;
	
	private JournalService journalService;
	
	//private AccountServiceTest accountServiceTest;
	
	@Test
	public void addTestAccountInfo() throws Exception{
		Account cashAccount = new Account();
		cashAccount.setAccountNo("0001");
		cashAccount.setAccountName("现金账户");
		cashAccount.setGeneralLedgerFlag(true);
		cashAccount.setMemo("现金账户总账");
		cashAccount.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		accountDao.add(cashAccount);
		
		Account debitCardAccount = new Account();
		debitCardAccount.setAccountNo("000101");
		debitCardAccount.setAccountName("银行借记卡");
		debitCardAccount.setGeneralLedgerFlag(false);
		debitCardAccount.setMemo("银行存款");
		debitCardAccount.setParentIdAcct(cashAccount.getIdAcct());
		accountDao.add(debitCardAccount);
		
		
		Account walletAccount = new Account();
		walletAccount.setAccountNo("000102");
		walletAccount.setAccountName("钱包");
		walletAccount.setGeneralLedgerFlag(false);
		walletAccount.setMemo("钱包账户");
		walletAccount.setParentIdAcct(cashAccount.getIdAcct());
		accountDao.add(walletAccount);
		
		Account wallet1Account = new Account();
		wallet1Account.setAccountNo("00010201");
		wallet1Account.setAccountName("子钱包1");
		wallet1Account.setGeneralLedgerFlag(false);
		wallet1Account.setMemo("钱包账户");
		wallet1Account.setParentIdAcct(walletAccount.getIdAcct());
		accountDao.add(wallet1Account);
		
		
		AccountPeriod accountPeriod = new AccountPeriod();
		accountPeriod.setPeriodNo("0001");
		accountPeriod.setFiscalYear("2011");
		accountPeriod.setActivePeroidFlag(true);
		accountPeriodDao.add(accountPeriod);
		
		AccountBalance cashBalance = new AccountBalance();
		cashBalance.setIdAcct(cashAccount.getIdAcct());
		cashBalance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(cashBalance);
		
		AccountBalance debitCardBalance = new AccountBalance();
		debitCardBalance.setIdAcct(debitCardAccount.getIdAcct());
		debitCardBalance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(debitCardBalance);
		
		AccountBalance walletBalance = new AccountBalance();
		walletBalance.setIdAcct(walletAccount.getIdAcct());
		walletBalance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(walletBalance);
		
		AccountBalance wallet1Balance = new AccountBalance();
		wallet1Balance.setIdAcct(wallet1Account.getIdAcct());
		wallet1Balance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(wallet1Balance);
		
		Account ar = new Account();
		ar.setAccountNo("0002");
		ar.setAccountName("应收账款");
		ar.setGeneralLedgerFlag(true);
		ar.setMemo("应收账款");
		ar.setParentIdAcct(Account.NULL_PARENT_ID_ACCT);
		accountDao.add(ar);
		
		Account salaryAccount = new Account();
		salaryAccount.setAccountNo("000201");
		salaryAccount.setAccountName("应收账款--别人欠债");
		salaryAccount.setGeneralLedgerFlag(false);
		salaryAccount.setMemo("应收账款--别人欠债");
		salaryAccount.setParentIdAcct(ar.getIdAcct());
		accountDao.add(salaryAccount);
		
		Account salary1Account = new Account();
		salary1Account.setAccountNo("00020101");
		salary1Account.setAccountName("应收账款--别人欠债--男人欠债");
		salary1Account.setGeneralLedgerFlag(false);
		salary1Account.setMemo("应收账款--别人欠债--男人欠债");
		salary1Account.setParentIdAcct(salaryAccount.getIdAcct());
		accountDao.add(salary1Account);
		
		
		AccountBalance arBalance = new AccountBalance();
		arBalance.setCFFlg(false);
		arBalance.setIdAcct(ar.getIdAcct());
		arBalance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(arBalance);
		
		AccountBalance salaryBalance = new AccountBalance();
		salaryBalance.setCFFlg(false);
		salaryBalance.setIdAcct(salaryAccount.getIdAcct());
		salaryBalance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(salaryBalance);
		
		AccountBalance salary1Balance = new AccountBalance();
		salary1Balance.setCFFlg(false);
		salary1Balance.setIdAcct(salary1Account.getIdAcct());
		salary1Balance.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		accountBalanceDao.add(salary1Balance);
	}

	
//	@Test
//	//@Transactional(propagation=Propagation.REQUIRED)
//	public void testAccountService() throws Exception{
//		
//		AccountPeriod accountPeriod = accountPeriodService.getActiveAccountPeriod();
//		
//		Journal journal = new Journal();
//		journal.setJournalNo("01");
//		journal.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
//		journal.setEntryTime(new Date());
//		journal.setStatus(Journal.STATUS_INIT);
//		
//		JournalDetail debit = new JournalDetail();
//		debit.setAccountNo("00010201");
//		debit.setDebitAmount(new BigDecimal("2040.34"));
//		journal.getDetails().add(debit);
//		
//		JournalDetail credit = new JournalDetail();
//		credit.setAccountNo("00020101");
//		credit.setCreditAmount(new BigDecimal("2040.34"));
//		journal.getDetails().add(credit);
//		journalDao.add(journal);
//		
//		journalService.approveJournal(journal.getIdJournal());
//		journalService.postJournal(journal.getIdJournal());
//		
//		boolean isBalance = accountService.trialBalancing(accountPeriod.getIdAcctPeriod());
//		
//		Assert.assertTrue(isBalance);
//		
//		
//	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountPeriodDao getAccountPeriodDao() {
		return accountPeriodDao;
	}

	@Autowired
	public void setAccountPeriodDao(AccountPeriodDao accountPeriodDao) {
		this.accountPeriodDao = accountPeriodDao;
	}

	public AccountBalanceDao getAccountBalanceDao() {
		return accountBalanceDao;
	}

	@Autowired
	public void setAccountBalanceDao(AccountBalanceDao accountBalanceDao) {
		this.accountBalanceDao = accountBalanceDao;
	}

	public JournalDao getJournalDao() {
		return journalDao;
	}

	@Autowired
	public void setJournalDao(JournalDao journalDao) {
		this.journalDao = journalDao;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountPeriodService getAccountPeriodService() {
		return accountPeriodService;
	}

	@Autowired
	public void setAccountPeriodService(AccountPeriodService accountPeriodService) {
		this.accountPeriodService = accountPeriodService;
	}

	@Autowired
	public void setJournalService(JournalService journalService) {
		this.journalService = journalService;
	}

	public JournalService getJournalService() {
		return journalService;
	}

//	@Autowired
//	public void setAccountServiceTest(@Qualifier("test")AccountServiceTest accountServiceTest) {
//		this.accountServiceTest = accountServiceTest;
//	}
//
//	public AccountServiceTest getAccountServiceTest() {
//		return accountServiceTest;
//	}

}
