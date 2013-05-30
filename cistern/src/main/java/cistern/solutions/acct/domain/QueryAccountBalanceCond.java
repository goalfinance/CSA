/**
 * 
 */
package cistern.solutions.acct.domain;

import cistern.dao.ql.annotation.Condition;
import cistern.dao.ql.annotation.Expression;
import cistern.dao.ql.impl.OrderableCondition;

/**
 * @project: cistern
 * @description: 查询账户余额信息条件对象
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
	 * 账户内部编号，外键reference to Account
	 */
	private Long idAcct;
	
	/**
	 * 账期内部编号，外键reference to AccountPeriod
	 */
	private Long idAcctPeriod;
	
	/**
	 * 结转标志
	 */
	private Boolean CFFlg;
	
	/**
	 * 备注
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
