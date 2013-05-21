/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;

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
 * @description: ������Ϣ
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Entity
@Proxy(lazy=false)
@Table(name="t_acct_period")
@SequenceGenerator(name="id_acct_period", sequenceName="id_acct_period")
public class AccountPeriod implements Serializable {
	/**
	 * �޸�������Ϣ
	 */
	public static final Long NULL_PARENT_ID_ACCT_PERIOD = -1L; 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �����ڲ���ţ�����
	 */
	private Long idAcctPeriod;
	
	/**
	 * �������ڲ����
	 */
	private Long parentIdAcctPeriod = NULL_PARENT_ID_ACCT_PERIOD;
	
	/**
	 * ���ڱ��
	 */
	private String periodNo;
	
	/**
	 * �������
	 */
	private String fiscalYear;
	
	/**
	 * ��ʼ����
	 */
	private java.sql.Date fromDate;
	
	/**
	 * ��������
	 */
	private java.sql.Date ThruDate;
	
	/**
	 * �Ƿ�Ϊ��ǰ�������
	 */
	private Boolean activePeroidFlag = false;

	public AccountPeriod() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_acct_period")
	@Column(name="id_acct_period")
	public Long getIdAcctPeriod() {
		return idAcctPeriod;
	}

	public void setIdAcctPeriod(Long idAcctPeriod) {
		this.idAcctPeriod = idAcctPeriod;
	}

	@Column(name="parent_id_acct_period")
	public Long getParentIdAcctPeriod() {
		return parentIdAcctPeriod;
	}

	public void setParentIdAcctPeriod(Long parentIdAcctPeriod) {
		this.parentIdAcctPeriod = parentIdAcctPeriod;
	}

	@Column(name="period_no")
	public String getPeriodNo() {
		return periodNo;
	}

	public void setPeriodNo(String periodNo) {
		this.periodNo = periodNo;
	}

	@Column(name="fiscal_year")
	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	@Column(name="from_date")
	public java.sql.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.sql.Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name="thru_date")
	public java.sql.Date getThruDate() {
		return ThruDate;
	}

	public void setThruDate(java.sql.Date thruDate) {
		ThruDate = thruDate;
	}

	public void setActivePeroidFlag(Boolean activePeroidFlag) {
		this.activePeroidFlag = activePeroidFlag;
	}

	@Column(name="AP_FLG")
	public Boolean getActivePeroidFlag() {
		return activePeroidFlag;
	}
}
