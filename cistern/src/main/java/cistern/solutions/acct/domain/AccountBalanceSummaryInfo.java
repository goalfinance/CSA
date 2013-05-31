/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @project: cistern
 * @description: 所有总分类账户余额汇总信息，用于试算平衡
 * @author: panqr
 * @create_time: 2011-4-25
 *
 */
public class AccountBalanceSummaryInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 期初借方余额
	 */
	private BigDecimal openingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * 期初贷方余额
	 */
	private BigDecimal openingCreditTotal = BigDecimal.ZERO;

	/**
	 * 本期借方余额
	 */
	private BigDecimal debitActiveTotal = BigDecimal.ZERO;
	
	/**
	 * 本期贷方余额
	 */
	private BigDecimal creditActiveTotal = BigDecimal.ZERO;
	
	/**
	 * 期末借方余额
	 */
	private BigDecimal closingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * 期末贷方余额
	 */
	private BigDecimal closingCreditTotal = BigDecimal.ZERO;
	
	public AccountBalanceSummaryInfo() {
		super();
	}

	public BigDecimal getOpeningDebitTotal() {
		return openingDebitTotal;
	}

	public void setOpeningDebitTotal(BigDecimal openingDebitTotal) {
		this.openingDebitTotal = openingDebitTotal;
	}

	public BigDecimal getOpeningCreditTotal() {
		return openingCreditTotal;
	}

	public void setOpeningCreditTotal(BigDecimal openingCreditTotal) {
		this.openingCreditTotal = openingCreditTotal;
	}

	public BigDecimal getDebitActiveTotal() {
		return debitActiveTotal;
	}

	public void setDebitActiveTotal(BigDecimal debitActiveTotal) {
		debitActiveTotal = debitActiveTotal;
	}

	public BigDecimal getCreditActiveTotal() {
		return creditActiveTotal;
	}

	public void setCreditActiveTotal(BigDecimal creditActiveTotal) {
		creditActiveTotal = creditActiveTotal;
	}

	public BigDecimal getClosingDebitTotal() {
		return closingDebitTotal;
	}

	public void setClosingDebitTotal(BigDecimal closingDebitTotal) {
		closingDebitTotal = closingDebitTotal;
	}

	public BigDecimal getClosingCreditTotal() {
		return closingCreditTotal;
	}

	public void setClosingCreditTotal(BigDecimal closingCreditTotal) {
		closingCreditTotal = closingCreditTotal;
	}
}
