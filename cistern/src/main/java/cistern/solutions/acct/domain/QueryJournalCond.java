/**
 * 
 */
package cistern.solutions.acct.domain;

import java.util.Date;

import cistern.dao.hibernate.simplequery.OrderableCondition;
import cistern.dao.hibernate.simplequery.annotation.Condition;
import cistern.dao.hibernate.simplequery.annotation.Expression;

/**
 * @project: cistern
 * @description: ��ѯ��¼��Ϣ��������
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
@Condition(entity=cistern.solutions.acct.domain.Journal.class)
public class QueryJournalCond extends OrderableCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��¼��
	 */
	private String journalNo;
	
	/**
	 * ��ѯ������ڷ�Χ����ʼ
	 */
	private Date beginAcctDate;
	
	/**
	 * ��ѯ������ڷ�Χ������
	 */
	private Date endAcctDate;
	
	/**
	 * ժҪ��Ϣ
	 */
	private String memo;
	
	/**
	 * ��ѯ����ʱ�䷶Χ����ʼ
	 */
	private Date beginEntryTime;
	
	/**
	 * ��ѯ����ʱ�䷶Χ������
	 */
	private Date endEntityTime;
	
	/**
	 * ��ѯ����ʱ�䷶Χ����ʼ
	 */
	private Date beginPostingTime;
	
	/**
	 * ��ѯ����ʱ�䷶Χ������
	 */
	private Date endPostingTime;
	
	/**
	 * �Ƿ��Ѿ�����
	 */
	private Boolean isPosting;

	public QueryJournalCond() {
		super();
	}

	@Expression
	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	@Expression(persistenceProperty="acctDate", operator=">=", convertor="cistern.dao.simplequery.RoundToStartTimeConvertor")
	public Date getBeginAcctDate() {
		return beginAcctDate;
	}

	public void setBeginAcctDate(Date beginAcctDate) {
		this.beginAcctDate = beginAcctDate;
	}

	@Expression(persistenceProperty="acctDate", operator="<=", convertor="cistern.dao.simplequery.RoundToEndTimeConvertor")
	public Date getEndAcctDate() {
		return endAcctDate;
	}

	public void setEndAcctDate(Date endAcctDate) {
		this.endAcctDate = endAcctDate;
	}

	@Expression(operator="like")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Expression(persistenceProperty="entryTime", operator=">=")
	public Date getBeginEntryTime() {
		return beginEntryTime;
	}

	public void setBeginEntryTime(Date beginEntryTime) {
		this.beginEntryTime = beginEntryTime;
	}

	@Expression(persistenceProperty="entryTime", operator="<=")
	public Date getEndEntityTime() {
		return endEntityTime;
	}

	public void setEndEntityTime(Date endEntityTime) {
		this.endEntityTime = endEntityTime;
	}

	@Expression(persistenceProperty="postingTime", operator=">=")
	public Date getBeginPostingTime() {
		return beginPostingTime;
	}

	public void setBeginPostingTime(Date beginPostingTime) {
		this.beginPostingTime = beginPostingTime;
	}

	@Expression(persistenceProperty="postingTime", operator="<=")
	public Date getEndPostingTime() {
		return endPostingTime;
	}

	public void setEndPostingTime(Date endPostingTime) {
		this.endPostingTime = endPostingTime;
	}

	@Expression
	public Boolean getIsPosting() {
		return isPosting;
	}

	public void setIsPosting(Boolean isPosting) {
		this.isPosting = isPosting;
	}
}
