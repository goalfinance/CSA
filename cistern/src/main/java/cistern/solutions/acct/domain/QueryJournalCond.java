/**
 * 
 */
package cistern.solutions.acct.domain;

import java.util.Date;

import cistern.dao.ql.annotation.Condition;
import cistern.dao.ql.annotation.Expression;
import cistern.dao.ql.impl.OrderableCondition;

/**
 * @project: cistern
 * @description: 查询分录信息条件对象
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
	 * 分录号
	 */
	private String journalNo;
	
	/**
	 * 查询会计日期范围，开始
	 */
	private Date beginAcctDate;
	
	/**
	 * 查询会计日期范围，结束
	 */
	private Date endAcctDate;
	
	/**
	 * 摘要信息
	 */
	private String memo;
	
	/**
	 * 查询创建时间范围，开始
	 */
	private Date beginEntryTime;
	
	/**
	 * 查询创建时间范围，结束
	 */
	private Date endEntityTime;
	
	/**
	 * 查询记账时间范围，开始
	 */
	private Date beginPostingTime;
	
	/**
	 * 查询记账时间范围，结束
	 */
	private Date endPostingTime;
	
	/**
	 * 是否已经记账
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
