/**
 * 
 */
package cistern.solutions.acct.service;

import java.math.BigDecimal;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.Account;
import cistern.solutions.acct.domain.AccountBalance;

/**
 * @project: cistern
 * @description: 账户服务接口
 * @author: panqr
 * @create_time: 2011-4-23
 *
 */
public interface AccountService {
	
	/**
	 * 根据账户号获取状态账户信息
	 * @param accountNo
	 * @return
	 * @throws AppBizException
	 */
	public Account getByAccountNo(String accountNo) throws AppBizException;
	
	/**
	 * 获取指定账户当前账期的余额信息
	 * @param accountNo
	 * @return
	 * @throws AppBizException
	 */
	public AccountBalance getActiveAccountBalance(String accountNo) throws AppBizException;
	
	/**
	 * 借记账户
	 * @param AccountNo
	 * @param idAcctPeriod
	 * @param amt
	 * @throws AppBizException
	 */
	public void debitAccount(String accountNo, Long idAcctPeriod, BigDecimal amt) throws AppBizException;
	
	/**
	 * 贷记账户
	 * @param accountNo
	 * @param idAcctPeriod
	 * @param amt
	 * @throws AppBizException
	 */
	public void creditAccount(String accountNo, Long idAcctPeriod, BigDecimal amt) throws AppBizException;
	
	/**
	 * 对指定账期的账户余额进行试算平衡
	 * 计算公式为：
	 * 
	 * 1、全部账户的借方期初余额合计数等于全部账户的贷方期初余额合计数
	 * 2、全部账户的借方发生额合计等于全部账户的贷方发生额合计
	 * 3、全部账户的借方期末余额合计等于全部账户的贷方期末余额合计
	 * 
	 * @param idAcctPeriod
	 * @throws AppBizException
	 */
	public boolean trialBalancing(Long idAcctPeriod) throws AppBizException;

}
