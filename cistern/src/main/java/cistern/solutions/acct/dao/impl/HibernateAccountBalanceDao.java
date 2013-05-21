/**
 * 
 */
package cistern.solutions.acct.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import cistern.dao.hibernate.hibernate3.HibernateGenericDao;
import cistern.solutions.acct.dao.AccountBalanceDao;
import cistern.solutions.acct.domain.AccountBalance;
import cistern.solutions.acct.domain.AccountBalanceSummaryInfo;
import cistern.solutions.acct.domain.QueryAccountBalanceCond;

/**
 * @project: cistern
 * @description: 账户余额信息数据操作hibernate实现类
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
public class HibernateAccountBalanceDao extends
		HibernateGenericDao<AccountBalance, Long, QueryAccountBalanceCond> 
		implements AccountBalanceDao{
	
	/**
	 * 汇总所有总分类账的账户余额
	 * @param idAcctPeriod
	 * @return
	 */
	public AccountBalanceSummaryInfo accountBalanceGLSummary(Long idAcctPeriod){
		AccountBalanceSummaryInfo abSummaryInfo = new AccountBalanceSummaryInfo();
		
		StringBuffer hsql = new StringBuffer();
		
		hsql.append("select ");
		hsql.append("sum(acctbal.openingDebitTotal),");
		hsql.append("sum(acctbal.openingCreditTotal),");
		hsql.append("sum(acctbal.debitActTotal),");
		hsql.append("sum(acctbal.creditActTotal),");
		hsql.append("sum(acctbal.closingDebitTotal),");
		hsql.append("sum(acctbal.closingCreditTotal) ");
		hsql.append("from Account acct, AccountBalance acctbal ");
		hsql.append("where acct.idAcct = acctbal.idAcct ");
		hsql.append(" and acctbal.idAcctPeriod = ? ");
		hsql.append(" and acct.generalLedgerFlag = '1' ");
		hsql.append(" and acct.validFlag = '1'");
		
		List result = this.getHibernateTemplate().find(hsql.toString(), idAcctPeriod);
		
		if (result.isEmpty()){
			return abSummaryInfo;
		}else{
			Object[] l = (Object[]) result.get(0);
			abSummaryInfo.setOpeningDebitTotal((BigDecimal)l[0]);
			abSummaryInfo.setOpeningCreditTotal((BigDecimal)l[1]);
			abSummaryInfo.setDebitActiveTotal((BigDecimal)l[2]);
			abSummaryInfo.setCreditActiveTotal((BigDecimal)l[3]);
			abSummaryInfo.setClosingDebitTotal((BigDecimal)l[4]);
			abSummaryInfo.setClosingCreditTotal((BigDecimal)l[5]);
			return abSummaryInfo;
		}
	}
}
