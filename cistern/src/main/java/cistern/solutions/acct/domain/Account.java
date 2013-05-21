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
 * @description: �˻���Ϣ
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Entity
@Proxy(lazy = false)
@Table(name = "t_acct")
@SequenceGenerator(name="id_acct", sequenceName="id_acct")
public class Account implements Serializable {
	@Override
	public String toString() {
		return "Account [idAcct=" + idAcct + ", parentIdAcct=" + parentIdAcct
				+ ", accountNo=" + accountNo + ", accountName=" + accountName
				+ ", generalLedgerFlag=" + generalLedgerFlag + ", createDate="
				+ createDate + ", validFlag=" + validFlag + ", memo=" + memo
				+ "]";
	}

	/**
	 * �����ڸ��˻�
	 */
	public static final Long NULL_PARENT_ID_ACCT = -1L; 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * �˻��ڲ���ţ�����
	 */
	private Long idAcct;
	
	/**
	 * ���˻��ڲ����
	 */
	private Long parentIdAcct = NULL_PARENT_ID_ACCT;
	
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
	private Boolean generalLedgerFlag = false;
	
	/**
	 * �˻�����ʱ��
	 */
	private java.sql.Date createDate;
	
	/**
	 * ��Ч��־
	 */
	private Boolean validFlag = true;
	
	/**
	 * ��ע
	 */
	private String memo;

	public Account() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_acct")
	@Column(name="id_acct")
	public Long getIdAcct() {
		return idAcct;
	}

	public void setIdAcct(Long idAcct) {
		this.idAcct = idAcct;
	}

	@Column(name="parent_id_acct")
	public Long getParentIdAcct() {
		return parentIdAcct;
	}

	public void setParentIdAcct(Long parentIdAcct) {
		this.parentIdAcct = parentIdAcct;
	}

	@Column(name="acct_no")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name="acct_name")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name="create_date")
	public java.sql.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="valid_flg")
	public Boolean getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Boolean validFlag) {
		this.validFlag = validFlag;
	}

	@Column(name="memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setGeneralLedgerFlag(Boolean generalLedgerFlag) {
		this.generalLedgerFlag = generalLedgerFlag;
	}

	@Column(name="gl_flg")
	public Boolean getGeneralLedgerFlag() {
		return generalLedgerFlag;
	}
}
