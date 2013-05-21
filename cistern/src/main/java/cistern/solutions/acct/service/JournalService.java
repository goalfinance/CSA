/**
 * 
 */
package cistern.solutions.acct.service;

import cistern.common.AppBizException;
import cistern.solutions.acct.domain.Journal;

/**
 * @project: cistern
 * @description: ��Ʒ�¼����ӿ�
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
public interface JournalService {
	
	/**
	 * ���ݷ�¼��Ż�ȡ��¼��Ϣ
	 * @param idJournal
	 * @return
	 * @throws AppBizException
	 */
	public Journal getJournalById(Long idJournal) throws AppBizException;
	
	
	/**
	 * �����Ʒ�¼�ݸ���Ϣ
	 * @throws AppBizException
	 */
	public void saveDraftJournal(Journal journal) throws AppBizException;
	
	/**
	 * ����Ʒ�¼����Ƿ�ƽ��
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalCDBalance(Long idJournal) throws AppBizException;
	
	/**
	 * �۲��Ʒ�¼����Ƿ�ƽ��
	 * @param journal
	 * @throws AppBizException
	 */
	public void checkJournalCDBalance(Journal journal) throws AppBizException;
	
	/**
	 * ��׼��Ʒ�¼
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void approveJournal(Long idJournal) throws AppBizException;
	
	/**
	 * ����Ʒ�¼�Ƿ��ڵ�ǰ����
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalInActiveAcctPeriod(Long idJournal) throws AppBizException;
	
	/**
	 * ����Ʒ�¼�Ƿ��ڵ�ǰ����
	 * @param journal
	 * @throws AppBizException
	 */
	public void checkJournalInActiveAcctPeriod(Journal journal) throws AppBizException;
	
	/**
	 * ����Ʒ�¼�Ƿ����ͨ��
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void checkJournalApproved(Long idJournal) throws AppBizException;
	
	/**
	 * �Է�¼���м��˴���
	 * @param idJournal
	 * @throws AppBizException
	 */
	public void postJournal(Long idJournal) throws AppBizException;
	
}
