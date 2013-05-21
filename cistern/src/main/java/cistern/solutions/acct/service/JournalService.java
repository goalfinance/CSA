/**
 * 
 */
package cistern.solutions.acct.service;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.Journal;

/**
 * @project: cistern
 * @description: 会计分录服务接口
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public interface JournalService {
	
	/**
	 * 根据分录编号获取分录信息
	 * @param idJournal
	 * @return
	 * @throws AppBizException
	 */
	public Journal getJournalById(Long idJournal) throws AppBizException;
	
	
	/**
	 * 保存会计分录草稿信息
	 * @throws AppBizException
	 */
	public void saveDraftJournal(Journal journal) throws AppBizException;
	
	/**
	 * 检查会计分录借贷是否平衡
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalCDBalance(Long idJournal) throws AppBizException;
	
	/**
	 * 价差会计分录借贷是否平衡
	 * @param journal
	 * @throws AppBizException
	 */
	public void checkJournalCDBalance(Journal journal) throws AppBizException;
	
	/**
	 * 批准会计分录
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void approveJournal(Long idJournal) throws AppBizException;
	
	/**
	 * 检查会计分录是否在当前账期
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalInActiveAcctPeriod(Long idJournal) throws AppBizException;
	
	/**
	 * 检查会计分录是否在当前账期
	 * @param journal
	 * @throws AppBizException
	 */
	public void checkJournalInActiveAcctPeriod(Journal journal) throws AppBizException;
	
	/**
	 * 检查会计分录是否被审核通过
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalApproved(Long idJournal) throws AppBizException;
	
	/**
	 * 对分录进行记账处理
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void postJournal(Long idJournal) throws AppBizException;
	
}
