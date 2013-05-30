/**
 * 
 */
package cistern.solutions.acct.domain;

import java.util.Date;

import cistern.dao.ql.annotation.Condition;
import cistern.dao.ql.annotation.Expression;
import cistern.dao.ql.impl.OrderableCondition;

/**
 * @project: cistern
 * @description: 查询账期信息条件对象
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
	 * 账期编号
	 */
	private String periodNo;
	
	/**
	 * 财政年度
	 */
	private String fiscalYear;
	
	/**
	 * 查询账期开始日期范围，开始
	 */
	private Date beginFromDate;
	
	/**
	 *查询 账期开始时间范围，结束
	 */
	private Date endFromDate;
	
	/**
	 * 查询账期结束日期范围，开始
	 */
	private Date beginThruDate;
	
	/**
	 * 查询账期结束日期范围，结束
	 */
	private Date endThruDate;
	
	/**
	 * 是否为当前会计账期
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

	@Expression(persistenceProperty = "fromDate", operator = ">=", convertorClass=cistern.dao.ql.impl.RoundToStartTimeConvertor.class)
	public Date getBeginFromDate() {
		return beginFromDate;
	}

	public void setBeginFromDate(Date beginFromDate) {
		this.beginFromDate = beginFromDate;
	}

	@Expression(persistenceProperty="fromDate", operator="<=", convertorClass=cistern.dao.ql.impl.RoundToEndTimeConvertor.class)
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
