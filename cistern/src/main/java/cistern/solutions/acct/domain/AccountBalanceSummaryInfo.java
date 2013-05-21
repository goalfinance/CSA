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
	private BigDecimal DebitActiveTotal = BigDecimal.ZERO;
	
	/**
	 * ���ڴ������
	 */
	private BigDecimal CreditActiveTotal = BigDecimal.ZERO;
	
	/**
	 * ��ĩ�跽���
	 */
	private BigDecimal ClosingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * ��ĩ�������
	 */
	private BigDecimal ClosingCreditTotal = BigDecimal.ZERO;
	
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
		return DebitActiveTotal;
	}

	public void setDebitActiveTotal(BigDecimal debitActiveTotal) {
		DebitActiveTotal = debitActiveTotal;
	}

	public BigDecimal getCreditActiveTotal() {
		return CreditActiveTotal;
	}

	public void setCreditActiveTotal(BigDecimal creditActiveTotal) {
		CreditActiveTotal = creditActiveTotal;
	}

	public BigDecimal getClosingDebitTotal() {
		return ClosingDebitTotal;
	}

	public void setClosingDebitTotal(BigDecimal closingDebitTotal) {
		ClosingDebitTotal = closingDebitTotal;
	}

	public BigDecimal getClosingCreditTotal() {
		return ClosingCreditTotal;
	}

	public void setClosingCreditTotal(BigDecimal closingCreditTotal) {
		ClosingCreditTotal = closingCreditTotal;
	}
}
