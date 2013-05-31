/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @project: cistern
 * @description: �����ܷ����˻���������Ϣ����������ƽ��
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
	 * �ڳ��跽���
	 */
	private BigDecimal openingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * �ڳ��������
	 */
	private BigDecimal openingCreditTotal = BigDecimal.ZERO;

	/**
	 * ���ڽ跽���
	 */
	private BigDecimal debitActiveTotal = BigDecimal.ZERO;
	
	/**
	 * ���ڴ������
	 */
	private BigDecimal creditActiveTotal = BigDecimal.ZERO;
	
	/**
	 * ��ĩ�跽���
	 */
	private BigDecimal closingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * ��ĩ�������
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
