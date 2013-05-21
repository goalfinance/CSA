/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @project: cistern
 * @description: 账户余额信息
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Entity
@Proxy(lazy = false)
@Table(name = "t_acct_balance")
@SequenceGenerator(name = "id_acct_bal", sequenceName = "id_acct_bal")
public class AccountBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账户余额内部编号，主键
	 */
	private Long idAcctBalance;
	
	/**
	 * 账户内部编号，外键reference to Account
	 */
	private Long idAcct;
	
	/**
	 * 账期内部编号，外键reference to AccountPeriod
	 */
	private Long idAcctPeriod;
	
	/**
	 * 期初余额，单位：元
	 */
	private BigDecimal openingBalance = BigDecimal.ZERO;
	
	/**
	 * 期初借方发生笔数
	 */
	private Long openingDebitCount = 0L;
	
	/**
	 * 期初借方发生额
	 */
	private BigDecimal openingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * 期初贷方发生笔数
	 */
	private Long openingCreditCount = 0L;
	
	/**
	 * 期初贷方发生额
	 */
	private BigDecimal openingCreditTotal = BigDecimal.ZERO;
	
	/**
	 * 本期借方发生笔数
	 */
	private Long debitActCount = 0L;
	
	/**
	 * 本期借方发生金额，单位：元
	 */
	private BigDecimal debitActTotal = BigDecimal.ZERO;
	
	/**
	 * 本期贷方发生笔数
	 */
	private Long creditActCount = 0L;
	
	/**
	 * 本期贷方发生金额， 单位：元
	 */
	private BigDecimal creditActTotal = BigDecimal.ZERO;
	
	/**
	 * 期末余额， 单位：元
	 */
	private BigDecimal closingBalance = BigDecimal.ZERO;
	
	/**
	 * 期末借方发生笔数
	 */
	private Long closingDebitCount = 0L;
	
	/**
	 * 期末借方发生额
	 */
	private BigDecimal closingDebitTotal = BigDecimal.ZERO;
	
	/**
	 * 期末贷方发生笔数
	 */
	private Long ClosingCreditCount = 0L;
	
	/**
	 * 期末贷方发生额
	 */
	private BigDecimal ClosingCreditTotal = BigDecimal.ZERO;
	/**
	 * 结转标志
	 */
	private Boolean CFFlg = false;
	
	/**
	 * 备注
	 */
	private String memo;

	public AccountBalance() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_acct_bal")
	@Column(name="id_acct_bal")
	public Long getIdAcctBalance() {
		return idAcctBalance;
	}

	public void setIdAcctBalance(Long idAcctBalance) {
		this.idAcctBalance = idAcctBalance;
	}

	@Column(name="id_acct")
	public Long getIdAcct() {
		return idAcct;
	}

	public void setIdAcct(Long idAcct) {
		this.idAcct = idAcct;
	}

	@Column(name="id_acct_period")
	public Long getIdAcctPeriod() {
		return idAcctPeriod;
	}

	public void setIdAcctPeriod(Long idAcctPeriod) {
		this.idAcctPeriod = idAcctPeriod;
	}

	@Column(name="opening_balance")
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	@Column(name="dr_act_cnt")
	public Long getDebitActCount() {
		return debitActCount;
	}

	public void setDebitActCount(Long debitActCount) {
		this.debitActCount = debitActCount;
	}

	@Column(name="dr_act_total")
	public BigDecimal getDebitActTotal() {
		return debitActTotal;
	}

	public void setDebitActTotal(BigDecimal debitActTotal) {
		this.debitActTotal = debitActTotal;
	}

	@Column(name="cr_act_cnt")
	public Long getCreditActCount() {
		return creditActCount;
	}

	public void setCreditActCount(Long creditActCount) {
		this.creditActCount = creditActCount;
	}

	@Column(name="cr_act_total")
	public BigDecimal getCreditActTotal() {
		return creditActTotal;
	}

	public void setCreditActTotal(BigDecimal creditActTotal) {
		this.creditActTotal = creditActTotal;
	}

	@Column(name="closing_balance")
	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	@Column(name="cf_flg")
	public Boolean getCFFlg() {
		return CFFlg;
	}

	public void setCFFlg(Boolean cFFlg) {
		CFFlg = cFFlg;
	}

	@Column(name="memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	public void setOpeningDebitCount(Long openingDebitCount) {
		this.openingDebitCount = openingDebitCount;
	}

	@Column(name="opening_dr_cnt")
	public Long getOpeningDebitCount() {
		return openingDebitCount;
	}

	public void setOpeningDebitTotal(BigDecimal openingDebitTotal) {
		this.openingDebitTotal = openingDebitTotal;
	}

	@Column(name="opening_dr_total")
	public BigDecimal getOpeningDebitTotal() {
		return openingDebitTotal;
	}

	
	public void setOpeningCreditCount(Long openingCreditCount) {
		this.openingCreditCount = openingCreditCount;
	}

	@Column(name="opening_cr_cnt")
	public Long getOpeningCreditCount() {
		return openingCreditCount;
	}

	public void setOpeningCreditTotal(BigDecimal openingCreditTotal) {
		this.openingCreditTotal = openingCreditTotal;
	}

	@Column(name="opening_cr_total")
	public BigDecimal getOpeningCreditTotal() {
		return openingCreditTotal;
	}

	public void setClosingDebitCount(Long closingDebitCount) {
		this.closingDebitCount = closingDebitCount;
	}

	@Column(name="closing_dr_cnt")
	public Long getClosingDebitCount() {
		return closingDebitCount;
	}

	public void setClosingDebitTotal(BigDecimal closingDebitTotal) {
		this.closingDebitTotal = closingDebitTotal;
	}

	@Column(name="closing_dr_total")
	public BigDecimal getClosingDebitTotal() {
		return closingDebitTotal;
	}

	public void setClosingCreditCount(Long closingCreditCount) {
		ClosingCreditCount = closingCreditCount;
	}

	@Column(name="closing_cr_cnt")
	public Long getClosingCreditCount() {
		return ClosingCreditCount;
	}

	public void setClosingCreditTotal(BigDecimal closingCreditTotal) {
		ClosingCreditTotal = closingCreditTotal;
	}

	@Column(name="closing_cr_total")
	public BigDecimal getClosingCreditTotal() {
		return ClosingCreditTotal;
	}
}
