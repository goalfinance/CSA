package cistern.dao.ql.impl;


/**
 * @project: Cistern
 * @description: ConditionBean∂‘œÛ¿‡
 * @author: seabao
 * @create_time: 2007-12-12
 * @modify_time: 2007-12-12
 *
 */
public class ConditionBean {
	private String selectClause;
	
	private String fromClause;
	
	private String whereClause;

	public String getSelectClause() {
		return selectClause;
	}

	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}

	public String getFromClause() {
		return fromClause;
	}

	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

}
