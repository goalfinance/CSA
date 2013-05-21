/**
 * 
 */
package cistern.solutions.acct.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Proxy;

/**
 * @project: cistern
 * @description: ��¼��Ϣ��
 * @author: panqr
 * @create_time: 2011-4-22
 *
 */
@Entity
@Proxy(lazy=false)
@Table(name="t_journal")
@SequenceGenerator(name="id_journal", sequenceName="id_journal")
public class Journal implements Serializable {
	/**
	 * ��¼״̬����ʼ
	 */
	public static final String STATUS_INIT = "0";
	
	/**
	 * ��¼״̬�������
	 */
	public static final String STATUS_APPROVED = "1";
	
	/**
	 * ��¼״̬���ѹ���
	 */
	public static final String STATUS_POSTED = "2";
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��¼��Ϣ�ڲ���ţ� ����
	 */
	private Long idJournal;
	
	/**
	 * �����ڲ���ţ����
	 */
	private Long idAcctPeriod;
	
	/**
	 * ��¼��
	 */
	private String journalNo;
	
	/**
	 * �������
	 */
	private java.sql.Date acctDate;
	
	/**
	 * ժҪ��Ϣ
	 */
	private String memo;
	
	/**
	 * ����ʱ��
	 */
	private java.util.Date entryTime;
	
	/**
	 * ����ʱ��
	 */
	private java.util.Date postingTime;
	
	/**
	 * �Ƿ��Ѿ�����
	 */
	private String status = STATUS_INIT;
	
	/**
	 * ��¼��ϸ
	 */
	private List<JournalDetail> details = new ArrayList<JournalDetail>();

	public Journal() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_journal")
	@Column(name="id_journal")
	public Long getIdJournal() {
		return idJournal;
	}

	public void setIdJournal(Long idJournal) {
		this.idJournal = idJournal;
	}

	@Column(name="id_acct_period")
	public Long getIdAcctPeriod() {
		return idAcctPeriod;
	}

	public void setIdAcctPeriod(Long idAcctPeriod) {
		this.idAcctPeriod = idAcctPeriod;
	}
	
	@Column(name="journal_no")
	public String getJournalNo() {
		return journalNo;
	}

	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}

	@Column(name="acct_date")
	public java.sql.Date getAcctDate() {
		return acctDate;
	}

	public void setAcctDate(java.sql.Date acctDate) {
		this.acctDate = acctDate;
	}

	@Column(name="memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="create_time")
	public java.util.Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(java.util.Date entryTime) {
		this.entryTime = entryTime;
	}

	@Column(name="posting_time")
	public java.util.Date getPostingTime() {
		return postingTime;
	}

	public void setPostingTime(java.util.Date postingTime) {
		this.postingTime = postingTime;
	}

	@Column(name="posting_status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="t_journal_detail",
			joinColumns=@JoinColumn(name="id_journal"))
	@IndexColumn(name="line_num")
	public List<JournalDetail> getDetails() {
		return details;
	}

	public void setDetails(List<JournalDetail> details) {
		this.details = details;
	}
}
