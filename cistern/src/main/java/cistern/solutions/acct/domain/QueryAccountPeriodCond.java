/**
 * 
 */
package cistern.solutions.acct.domain;

import java.util.Date;

import cistern.dao.hibernate.simplequery.OrderableCondition;
import cistern.dao.hibernate.simplequery.annotation.Condition;
import cistern.dao.hibernate.simplequery.annotation.Expression;

/**
 * @project: cistern
 * @description: ��ѯ������Ϣ��������
 * @author: panqr
 * @create_time: 2011-4-21
 *
 */
@Condition(entity=cistern.solutions.acct.domain.AccountPeriod.class)
public class QueryAccountPeriodCond extends OrderableCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ���ڱ��
	 */
	private String periodNo;
	
	/**
	 * �������
	 */
	private String fiscalYear;
	
	/**
	 * ��ѯ���ڿ�ʼ���ڷ�Χ����ʼ
	 */
	private Date beginFromDate;
	
	/**
	 *��ѯ ���ڿ�ʼʱ�䷶Χ������
	 */
	private Date endFromDate;
	
	/**
	 * ��ѯ���ڽ������ڷ�Χ����ʼ
	 */
	private Date beginThruDate;
	
	/**
	 * ��ѯ���ڽ������ڷ�Χ������
	 */
	private Date endThruDate;
	
	/**
	 * �Ƿ�Ϊ��ǰ�������
	 */
	private Boolean activePeroidFlag;

	public QueryAccountPeriodCond() {
		super();
	}

	@Expression
	public String getPeriodNo() {
		return periodNo;
	}

	public void setPeriodNo(String periodNo) {
		this.periodNo = periodNo;
	}

	@Expression(operator = "like")
	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	@Expression(persistenceProperty = "fromDate", operator = ">=", convertor="cistern.dao.simplequery.RoundToStartTimeConvertor")
	public Date getBeginFromDate() {
		return beginFromDate;
	}

	public void setBeginFromDate(Date beginFromDate) {
		this.beginFromDate = beginFromDate;
	}

	@Expression(persistenceProperty="fromDate", operator="<=", convertor="cistern.dao.simplequery.RoundToEndTimeConvertor")
	public Date getEndFromDate() {
		return endFromDate;
	}

	public void setEndFromDate(Date endFromDate) {
		this.endFromDate = endFromDate;
	}

	@Expression(persistenceProperty="thruDate", operator=">=", convertor="cistern.dao.simplequery.RoundToStartTimeConvertor")
	public Date getBeginThruDate() {
		return beginThruDate;
	}

	public void setBeginThruDate(Date beginThruDate) {
		this.beginThruDate = beginThruDate;
	}

	@Expression(persistenceProperty="thruDate", operator="<=", convertor="cistern.dao.simplequery.RoundToEndTimeConvertor")
	public Date getEndThruDate() {
		return endThruDate;
	}

	public void setEndThruDate(Date endThruDate) {
		this.endThruDate = endThruDate;
	}

	public void setActivePeroidFlag(Boolean activePeroidFlag) {
		this.activePeroidFlag = activePeroidFlag;
	}

	@Expression
	public Boolean getActivePeroidFlag() {
		return activePeroidFlag;
	}
}
