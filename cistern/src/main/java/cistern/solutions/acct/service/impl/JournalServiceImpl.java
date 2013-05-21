/**
 * 
 */
package cistern.solutions.acct.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cistern.common.AppBizException;
import cistern.solutions.acct.common.AppExcCodes;
import cistern.solutions.acct.dao.JournalDao;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.Journal;
import cistern.solutions.acct.domain.JournalDetail;
import cistern.solutions.acct.service.AccountPeriodService;
import cistern.solutions.acct.service.AccountService;
import cistern.solutions.acct.service.JournalService;

/**
 * @project: cistern
 * @description: 会计分录服务接口实现类
 * @author: panqr
 * @create_time: 2011-4-22
 * 
 */
public class JournalServiceImpl implements JournalService {

	/**
	 * 会计分录信息数据操作接口
	 */
	private JournalDao journalDao;

	/**
	 * 会计账期服务接口
	 */
	private AccountPeriodService accountPeriodService;

	/**
	 * 账户信息服务接口
	 */
	private AccountService accountService;

	public Journal getJournalById(Long idJournal) throws AppBizException {
		Assert.notNull(idJournal, "The id of journal checked must not be null");

		Journal journal = null;
		try {
			journal = journalDao.get(idJournal);
		} catch (DataAccessException e) {
			String errmsg = "Error when getting a journal from database, errmsg:"
					+ e.getMessage();
			throw new AppBizException(AppExcCodes.ACCT_JOURNAL_NOT_FOUND,
					errmsg, e);
		}

		if (journal == null) {
			String errmsg = "The journal[id='" + idJournal
					+ "'] going to be loaded from database dose not exist";
			throw new AppBizException(AppExcCodes.ACCT_JOURNAL_NOT_FOUND,
					errmsg);
		}
		return journal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.JournalService#saveDraftJournal(cistern
	 * .solutions.acct.domain.Journal)
	 */
	public void saveDraftJournal(Journal journal) throws AppBizException {
		try {
			journal.setEntryTime(new java.util.Date());
			journal.setStatus(Journal.STATUS_INIT);
			journalDao.add(journal);
		} catch (DataAccessException e) {
			String errmsg = "Error when adding a journal to database, errmsg:"
					+ e.getMessage();
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_SAVED_ERROR,
					errmsg, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.JournalService#checkJournalCDBalance(java
	 * .lang.Long)
	 */
	public void checkJournalCDBalance(Long idJournal) throws AppBizException {
		Assert.notNull(idJournal,
				"The id of journal going to be checked must not be null");

		Journal journal = getJournalById(idJournal);

		checkJournalCDBalance(journal);

	}

	public void checkJournalCDBalance(Journal journal) throws AppBizException {
		Assert.notNull(journal,
				"The journal object going to be checked must not be null");
		Assert.notNull(journal.getDetails(),
				"The detail of journal going to be checked must not be null");
		Assert.notNull(journal.getDetails(),
				"The detail of journal going to be checked must not be empty");

		/**
		 * 汇总会计分录明细中的借贷余额
		 */
		BigDecimal debitBalance = BigDecimal.ZERO;
		BigDecimal creditBalance = BigDecimal.ZERO;
		for (JournalDetail jd : journal.getDetails()) {
			debitBalance = debitBalance.add(jd.getDebitAmount());
			creditBalance = creditBalance.add(jd.getCreditAmount());
		}

		/**
		 * 如果借贷余额不平
		 */
		if (debitBalance.compareTo(creditBalance) != 0) {
			String errmsg = "The CD balance of the journal[id = '"
					+ journal.getIdJournal() + "'] is not equal";
			throw new AppBizException(AppExcCodes.ACCT_JOURNAL_CD_UNBALANCE,
					errmsg);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.JournalService#approveJournal(java.lang
	 * .Long)
	 */
	public void approveJournal(Long idJournal) throws AppBizException {
		Journal journal = getJournalById(idJournal);
		checkJournalCDBalance(journal);
		checkJournalInActiveAcctPeriod(journal);
		try {
			Journal journal4Update = journalDao.get(idJournal);
			journal4Update.setStatus(Journal.STATUS_APPROVED);
			journalDao.update(journal4Update);
		} catch (DataAccessException e) {
			String errmsg = "Error when saving a journal to database, errmsg:"
					+ e.getMessage();
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_SAVED_ERROR,
					errmsg, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.JournalService#checkJournalInActiveAcctPeriod
	 * (java.lang.Long)
	 */
	public void checkJournalInActiveAcctPeriod(Long idJournal)
			throws AppBizException {
		Journal journal = getJournalById(idJournal);
		checkJournalInActiveAcctPeriod(journal);
	}

	public void checkJournalInActiveAcctPeriod(Journal journal)
			throws AppBizException {
		Assert.notNull(journal,
				"The journal object going to be checked must not be null");
		AccountPeriod activeAccountPeriod = accountPeriodService
				.getActiveAccountPeriod();
		if (journal.getIdAcctPeriod() == null
				|| journal.getIdAcctPeriod().equals(
						activeAccountPeriod.getIdAcctPeriod()) == false) {
			String errmsg = "The Accounting object[journal id = '"
					+ journal.getIdJournal()
					+ "']is not in the active account period";
			throw new AppBizException(
					AppExcCodes.ACCT_ACCTOBJ_OPERATED_NOT_IN_ACT_ACCTPERIOD,
					errmsg);
		}
	}

	public void checkJournalApproved(Long idJournal) throws AppBizException {
		Journal journal = getJournalById(idJournal);

		if (journal.getStatus().equals(Journal.STATUS_APPROVED) == false) {
			String errmsg = "The journal[id='" + journal.getIdJournal()
					+ "'] has not approved, it can not be posted!";
			throw new AppBizException(
					AppExcCodes.ACCT_JOURNAL_UNSUPPORTED_OPERATION, errmsg);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor={AppBizException.class})
	public void postJournal(Long idJournal) throws AppBizException {
		checkJournalApproved(idJournal);
		Journal journal = getJournalById(idJournal);
		Long idAcctPeriod = journal.getIdAcctPeriod();
		for (JournalDetail jd : journal.getDetails()) {
			String accountNo = jd.getAccountNo();
			if (jd.getCreditAmount().equals(BigDecimal.ZERO) == false) {
				accountService.creditAccount(accountNo, idAcctPeriod,
						jd.getCreditAmount());
			} else if (jd.getDebitAmount().equals(BigDecimal.ZERO) == false) {
				accountService.debitAccount(accountNo, idAcctPeriod,
						jd.getDebitAmount());
			}
		}
		Journal journal4Update = journalDao.get(idJournal);
		journal4Update.setStatus(Journal.STATUS_POSTED);
		journal4Update.setPostingTime(new Date());
		journalDao.update(journal4Update);
	}

	@Autowired
	public void setJournalDao(JournalDao journalDao) {
		this.journalDao = journalDao;
	}

	public JournalDao getJournalDao() {
		return journalDao;
	}

	@Autowired
	public void setAccountPeriodService(
			AccountPeriodService accountPeriodService) {
		this.accountPeriodService = accountPeriodService;
	}

	public AccountPeriodService getAccountPeriodService() {
		return accountPeriodService;
	}

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountService getAccountService() {
		return accountService;
	}
}
