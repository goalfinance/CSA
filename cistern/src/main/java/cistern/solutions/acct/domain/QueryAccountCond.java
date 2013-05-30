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
 * @description: 查询账户信息条件对象
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Condition(entity=cistern.solutions.acct.domain.Account.class)
public class QueryAccountCond extends OrderableCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户编号
	 */
	private String accountNo;
	
	/**
	 * 账户名称
	 */
	private String accountName;
	
	/**
	 * 总账标志
	 */
	private Boolean generalLedgerFlag;
	
	/**
	 * 账户创建时间,开始
	 */
	private Date beginCreateDate;
	
	/**
	 * 账户创建时间,结束
	 */
	private Date endCreateDate;
	
	/**
	 * 有效标志
	 */
	private Boolean validFlag = true;
	
	/**
	 * 备注
	 */
	private String memo;

	public QueryAccountCond() {
		super();
	}

	@Expression
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Expression(operator = "like")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Expression(persistenceProperty="createDate", operator=">=", convertorClass=cistern.dao.ql.impl.RoundToStartTimeConvertor.class)
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(java.sql.Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	@Expression(persistenceProperty="createDate", operator="<=", convertorClass=cistern.dao.ql.impl.RoundToEndTimeConvertor.class)
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(java.sql.Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	@Expression
	public Boolean getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Boolean validFlag) {
		this.validFlag = validFlag;
	}

	@Expression(operator="like")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setGeneralLedgerFlag(Boolean generalLedgerFlag) {
		this.generalLedgerFlag = generalLedgerFlag;
	}

	@Expression
	public Boolean getGeneralLedgerFlag() {
		return generalLedgerFlag;
	}
}
