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
 * @description: ��ѯ�˻���Ϣ��������
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
	 * �˻����
	 */
	private String accountNo;
	
	/**
	 * �˻�����
	 */
	private String accountName;
	
	/**
	 * ���˱�־
	 */
	private Boolean generalLedgerFlag;
	
	/**
	 * �˻�����ʱ��,��ʼ
	 */
	private Date beginCreateDate;
	
	/**
	 * �˻�����ʱ��,����
	 */
	private Date endCreateDate;
	
	/**
	 * ��Ч��־
	 */
	private Boolean validFlag = true;
	
	/**
	 * ��ע
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

	@Expression(persistenceProperty="createDate", operator=">=", convertor="cistern.dao.hibernate.simplequery.RoundToStartTimeConvertor")
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(java.sql.Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	@Expression(persistenceProperty="createDate", operator="<=", convertor="cistern.dao.hibernate.simplequery.RoundToEndTimeConvertor")
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
