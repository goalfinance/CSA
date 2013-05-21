/**
 * 
 */
package cistern.solutions.acct.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cistern.common.AppBizException;
import cistern.solutions.acct.common.AppExcCodes;
import cistern.solutions.acct.dao.AccountBalanceDao;
import cistern.solutions.acct.dao.AccountDao;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.AccountBalance;
import cistern.solutions.acct.domain.AccountBalanceSummaryInfo;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.QueryAccountBalanceCond;
import cistern.solutions.acct.domain.QueryAccountCond;
import cistern.solutions.acct.service.AccountPeriodService;
import cistern.solutions.acct.service.AccountService;

/**
 * @project: cistern
 * @description: 账户服务接口实现类
 * @author: panqr
 * @create_time: 2011-4-23
 * 
 */
public class AccountServiceImpl implements AccountService {

	/**
	 * 账户信息数据操作接口
	 */
	private AccountDao accountDao;

	/**
	 * 账户余额信息数据操作接口
	 */
	private AccountBalanceDao accountBalanceDao;

	/**
	 * 账期信息服务接口
	 */
	private AccountPeriodService accountPeriodService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.AccountService#getByAccountNo(java.lang
	 * .String)
	 */
	public Account getByAccountNo(String accountNo) throws AppBizException {
		QueryAccountCond cond = new QueryAccountCond();
		cond.setAccountNo(accountNo);
		cond.setValidFlag(Boolean.TRUE);
		List<Account> accts = accountDao.query(cond, 0, -1);

		if (accts.size() <= 0) {
			String errmsg = "There is no account[accountNo='" + accountNo
					+ "'] in the system";

			throw new AppBizException(AppExcCodes.ACCT_ACCOUNT_DOES_NOT_EXIST,
					errmsg);
		}

		if (accts.size() > 1) {
			String errmsg = "There is duplicated account[accountNo='"
					+ accountNo + "'] in the system";
			throw new AppBizException(AppExcCodes.ACCT_DUPLICATED_ACCOUNT,
					errmsg);
		}

		return accts.get(0);
	}

	public AccountBalance getActiveAccountBalance(String accountNo)
			throws AppBizException {
		Account account = getByAccountNo(accountNo);

		if (account.getValidFlag() == false) {
			String errmsg = "The status of account[accountNo='" + accountNo
					+ "'] is invalid";
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_STATUS_INVALID,
					errmsg);
		}
		AccountPeriod accountPeriod = accountPeriodService
				.getActiveAccountPeriod();

		QueryAccountBalanceCond cond = new QueryAccountBalanceCond();
		cond.setIdAcct(account.getIdAcct());
		cond.setIdAcctPeriod(accountPeriod.getIdAcctPeriod());
		cond.setOrders("idAcctBalance-");
		List<AccountBalance> acctBals = accountBalanceDao.query(cond, 0, -1);

		if (acctBals.size() <= 0) {
			String errmsg = "The account[accountNo='" + accountNo
					+ "'] has no balance in active account period[periodNo='"
					+ accountPeriod.getPeriodNo() + "']";
			throw new AppBizException(
					AppExcCodes.ACCT_ACCOUNT_HAS_NO_ACTIVE_BALANCE, errmsg);
		}

		return acctBals.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.AccountService#debitAccount(java.lang.
	 * String, java.lang.Long, java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor={AppBizException.class, DataAccessException.class})
	public void debitAccount(String accountNo, Long idAcctPeriod, BigDecimal amt)
			throws AppBizException {
		if (accountPeriodService.isActiveAccountPeriod(idAcctPeriod) == false) {
			String errmsg = "The account period[idAcctperiod='" + idAcctPeriod
					+ "'] specified to book is not active";

			throw new AppBizException(
					AppExcCodes.ACCT_ACCTOBJ_OPERATED_NOT_IN_ACT_ACCTPERIOD,
					errmsg);
		}

		Account account = getByAccountNo(accountNo);
		AccountBalance accountBalance = getActiveAccountBalance(account
				.getAccountNo());
		if (accountBalance.getCFFlg() == true) {
			String errmsg = "The active balance info[idAcctBalance='"
					+ accountBalance.getIdAcctBalance()
					+ "'] of account[accountNo='" + accountNo
					+ "'] has been carried forward";
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_STATUS_INVALID,
					errmsg);
		}

		AccountBalance ab4Updating = accountBalanceDao.get(accountBalance
				.getIdAcctBalance());
		ab4Updating.setDebitActCount(accountBalance.getDebitActCount() + 1);
		ab4Updating
				.setDebitActTotal(accountBalance.getDebitActTotal().add(amt));
		try {
			accountBalanceDao.update(ab4Updating);
		} catch (DataAccessException e) {
			String errmsg = "Error when updating account balance info. errmsg:"
					+ e.getMessage();
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_SAVED_ERROR,
					errmsg);
		}

		/**
		 * 如果被簿记账户不是总账，则递归父账户进行相应余额记录
		 */
		if (account.getGeneralLedgerFlag() == false) {
			/**
			 * 递归记录上级账户相应余额
			 */
			if (account.getParentIdAcct().equals(Account.NULL_PARENT_ID_ACCT) == false) {
				Account parentAccount = accountDao.get(account
						.getParentIdAcct());
				if (parentAccount != null) {
					debitAccount(parentAccount.getAccountNo(), idAcctPeriod,
							amt);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.solutions.acct.service.AccountService#creditAccount(java.lang
	 * .String, java.lang.Long, java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor={AppBizException.class, DataAccessException.class})
	public void creditAccount(String accountNo, Long idAcctPeriod,
			BigDecimal amt) throws AppBizException {
		if (accountPeriodService.isActiveAccountPeriod(idAcctPeriod) == false) {
			String errmsg = "The account period[idAcctperiod='" + idAcctPeriod
					+ "'] specified to book is not active";

			throw new AppBizException(
					AppExcCodes.ACCT_ACCTOBJ_OPERATED_NOT_IN_ACT_ACCTPERIOD,
					errmsg);
		}

		Account account = getByAccountNo(accountNo);
		AccountBalance accountBalance = getActiveAccountBalance(account
				.getAccountNo());
		if (accountBalance.getCFFlg() == true) {
			String errmsg = "The active balance info[idAcctBalance='"
					+ accountBalance.getIdAcctBalance()
					+ "'] of account[accountNo='" + accountNo
					+ "'] has been carried forward";
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_STATUS_INVALID,
					errmsg);
		}

		AccountBalance ab4Updating = accountBalanceDao.get(accountBalance
				.getIdAcctBalance());
		ab4Updating.setCreditActCount(accountBalance.getCreditActCount() + 1);
		ab4Updating.setCreditActTotal(accountBalance.getCreditActTotal().add(
				amt));
		try {
			accountBalanceDao.update(ab4Updating);
		} catch (DataAccessException e) {
			String errmsg = "Error when updating account balance info. errmsg:"
					+ e.getMessage();
			throw new AppBizException(AppExcCodes.ACCT_ACCTOBJ_SAVED_ERROR,
					errmsg);
		}

		/**
		 * 如果被簿记账户不是总账，则递归父账户进行相应余额记录
		 */
		if (account.getGeneralLedgerFlag() == false) {
			/**
			 * 递归记录上级账户相应余额
			 */
			if (account.getParentIdAcct().equals(Account.NULL_PARENT_ID_ACCT) == false) {
				Account parentAccount = accountDao.get(account
						.getParentIdAcct());
				if (parentAccount != null) {
					creditAccount(parentAccount.getAccountNo(), idAcctPeriod,
							amt);
				}
			}
		}

	}

	public boolean trialBalancing(Long idAcctPeriod) throws AppBizException {
		/**
		 * 全部账户的借方期初余额合计数等于全部账户的贷方期初余额合计数
		 */

		/**
		 * 全部账户的借方发生额合计等于全部账户的贷方发生额合计
		 */

		/**
		 * 全部账户的借方期末余额合计等于全部账户的贷方期末余额合计
		 */
		Assert.notNull(idAcctPeriod,
				"The id of account period must not be null");

		AccountBalanceSummaryInfo absi = accountBalanceDao
				.accountBalanceGLSummary(idAcctPeriod);

		boolean isOpeningBalance = (absi.getOpeningDebitTotal().compareTo(
				absi.getOpeningCreditTotal()) == 0) ? true : false;
		boolean isActiveBalance = (absi.getDebitActiveTotal().compareTo(
				absi.getCreditActiveTotal()) == 0) ? true : false;
		boolean isClosingBalance = (absi.getClosingDebitTotal().compareTo(
				absi.getClosingCreditTotal()) == 0) ? true : false;

		if (isOpeningBalance && isActiveBalance && isClosingBalance) {
			return true;
		} else {
			return false;
		}
	}

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	@Autowired
	public void setAccountBalanceDao(AccountBalanceDao accountBalanceDao) {
		this.accountBalanceDao = accountBalanceDao;
	}

	public AccountBalanceDao getAccountBalanceDao() {
		return accountBalanceDao;
	}

	@Autowired
	public void setAccountPeriodService(
			AccountPeriodService accountPeriodService) {
		this.accountPeriodService = accountPeriodService;
	}

	public AccountPeriodService getAccountPeriodService() {
		return accountPeriodService;
	}
}
