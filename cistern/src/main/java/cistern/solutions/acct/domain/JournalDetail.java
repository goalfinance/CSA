/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @project: cistern
 * @description: 分录信息明细表
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
@Embeddable
public class JournalDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户号
	 */
	private String accountNo;
	
	/**
	 * 借方金额
	 */
	private BigDecimal debitAmount = BigDecimal.ZERO;
	
	/**
	 * 贷方金额
	 */
	private BigDecimal creditAmount = BigDecimal.ZERO;

	public JournalDetail() {
		super();
	}

	@Column(name="acct_no")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name="dr_amt")
	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	@Column(name="cr_amt")
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
}
