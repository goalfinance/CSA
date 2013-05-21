/**
 * 
 */
package cistern.solutions.acct.domain;

import cistern.dao.hibernate.simplequery.OrderableCondition;
import cistern.dao.hibernate.simplequery.annotation.Condition;
import cistern.dao.hibernate.simplequery.annotation.Expression;

/**
 * @project: cistern
 * @description: ��ѯ�˻������Ϣ��������
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Condition(entity=cistern.solutions.acct.domain.AccountBalance.class)
public class QueryAccountBalanceCond extends OrderableCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �˻��ڲ���ţ����reference to Account
	 */
	private Long idAcct;
	
	/**
	 * �����ڲ���ţ����reference to AccountPeriod
	 */
	private Long idAcctPeriod;
	
	/**
	 * ��ת��־
	 */
	private Boolean CFFlg;
	
	/**
	 * ��ע
	 */
	private String memo;

	public QueryAccountBalanceCond() {
		super();
	}

	@Expression
	public Long getIdAcct() {
		return idAcct;
	}

	public void setIdAcct(Long idAcct) {
		this.idAcct = idAcct;
	}

	@Expression
	public Long getIdAcctPeriod() {
		return idAcctPeriod;
	}

	public void setIdAcctPeriod(Long idAcctPeriod) {
		this.idAcctPeriod = idAcctPeriod;
	}

	@Expression
	public Boolean getCFFlg() {
		return CFFlg;
	}

	public void setCFFlg(Boolean cFFlg) {
		CFFlg = cFFlg;
	}

	@Expression(operator = "like")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
