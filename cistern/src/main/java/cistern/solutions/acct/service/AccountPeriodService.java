/**
 * 
 */
package cistern.solutions.acct.service;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.AccountPeriod;

/**
 * @project: cistern
 * @description: 会计账期服务接口
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public interface AccountPeriodService {
	
	/**
	 * 根据内部编号获取账期信息
	 * @param idAcctPeriod
	 * @return
	 * @throws AppBizException
	 */
	public AccountPeriod getById(Long idAcctPeriod) throws AppBizException;
	
	/**
	 * 获取当前账期信息
	 * @return
	 * @throws AppBizException
	 */
	public AccountPeriod getActiveAccountPeriod() throws AppBizException;
	
	/**
	 * 判断指定的账期编号是否为当前账期
	 * @param idAccPeriod
	 * @throws AppBizException
	 */
	public boolean isActiveAccountPeriod(Long idAcctPeriod) throws AppBizException;

}
