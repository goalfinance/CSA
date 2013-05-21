/**
 * 
 */
package cistern.solutions.acct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cistern.common.AppBizException;
import cistern.solutions.acct.common.AppExcCodes;
import cistern.solutions.acct.dao.AccountPeriodDao;
import cistern.solutions.acct.domain.AccountPeriod;
import cistern.solutions.acct.domain.QueryAccountPeriodCond;
import cistern.solutions.acct.service.AccountPeriodService;

/**
 * @project: cistern
 * @description: 会计账期服务接口实现类
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public class AccountPeriodServiceImpl implements AccountPeriodService {
	/**
	 * 会计账期数据操作接口 
	 */
	private AccountPeriodDao accountPeriodDao;

	/* (non-Javadoc)
	 * @see cistern.solutions.acct.service.AccountPeriodService#getActiveAccountPeriod()
	 */
	public AccountPeriod getActiveAccountPeriod() throws AppBizException {
		QueryAccountPeriodCond cond = new QueryAccountPeriodCond();
		cond.setActivePeroidFlag(true);
		cond.setOrders("fromDate-");
		List<AccountPeriod> aps = accountPeriodDao.query(cond, 0, -1);
		
		if (aps == null || aps.size() <= 0){
			String errmsg = "The active account period does not exist";
			throw new AppBizException(AppExcCodes.ACCT_ACT_ACCT_PERIOD_DOSE_NOT_EXIST, errmsg);
		}
	
		return aps.get(0);
	}

	public AccountPeriod getById(Long idAcctPeriod) throws AppBizException {
		AccountPeriod accountPeriod = accountPeriodDao.get(idAcctPeriod);
		
		if (accountPeriod == null){
			String errmsg = "The account period[idAcctPeriod='" + idAcctPeriod + "'] does not exist";
			throw new AppBizException(AppExcCodes.ACCT_ACCT_PERIOD_DOSE_NOT_EXIST, errmsg);
		}
		return accountPeriod;
	}


	public boolean isActiveAccountPeriod(Long idAcctPeriod) throws AppBizException {
		AccountPeriod actAccountPeriod = getActiveAccountPeriod();
		
		return actAccountPeriod.getIdAcctPeriod().equals(idAcctPeriod);
	}
	@Autowired
	public void setAccountPeriodDao(AccountPeriodDao accountPeriodDao) {
		this.accountPeriodDao = accountPeriodDao;
	}

	public AccountPeriodDao getAccountPeriodDao() {
		return accountPeriodDao;
	}

}
