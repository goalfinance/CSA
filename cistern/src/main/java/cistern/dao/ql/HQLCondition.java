/*
 * Project cistern
 * FileName: Condition.java
 * Created on 2004-1-16 by Administrator
 * Last Modified on 2004-1-16 by Administrator
 */
package cistern.dao.ql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.MappingException;


/**
 * @author Administrator
 * description: Hibernate HQLÌõ¼þÀà
 */
public class HQLCondition {
	public static final int AND = 0;

	public static final int OR = 1;

	private boolean not = false;

	private String left = null;

	private String operator = null;

	private String right = null;

	private List<Object> parameters = new ArrayList<Object>();

	private List<ConcatCondition> concatConditions = new ArrayList<ConcatCondition>();

	public static class ConcatCondition {
		private int relation;

		private HQLCondition condition;

		public HQLCondition getCondition() {
			return condition;
		}

		public int getRelation() {
			return relation;
		}

		public void setCondition(HQLCondition condition) {
			this.condition = condition;
		}

		public void setRelation(int i) {
			relation = i;
		}
	}

	public HQLCondition() {
	}

	public void setNot(boolean not) {
		this.not = not;
	}

	public boolean getNot() {
		return not;
	}

	public static HQLCondition getInstance(String left, String operator) {
		HQLCondition cond = new HQLCondition();
		cond.setCondition(left, operator);
		return cond;
	}

	public static HQLCondition getInstance(String left, Collection<Object> parameters) {
		HQLCondition cond = new HQLCondition();
		cond.setCondition(left, parameters);
		return cond;
	}

	public void setCondition(String left, Collection<Object> parameters) {
		this.left = left;
		this.parameters.addAll(parameters);
	}

	public void setCondition(String left, String operator) {
		this.left = left;
		this.operator = operator;
		this.right = null;
	}

	public static HQLCondition getInstance(String left, String operator, String right, Object para) {
		HQLCondition cond = new HQLCondition();
		cond.setCondition(left, operator, right, para);
		return cond;
	}

	@SuppressWarnings("unchecked")
	public void setCondition(String left, String operator, String right, Object para) {
		this.left = left;
		this.operator = operator;
		this.right = right;
		if (para instanceof Collection) {
			parameters.addAll((Collection<Object>) para);
		} else {
			parameters.add(para);
		}
	}

	public static HQLCondition getInstance(String left, String operator, Object right) {
		HQLCondition cond = new HQLCondition();
		cond.setCondition(left, operator, right);
		return cond;
	}

	@SuppressWarnings("unchecked")
	public void setCondition(String left, String operator, Object right) {
		this.left = left;

		if (right == null) {
			if (operator.equals("=")) {
				this.operator = "IS NULL";
			} else if (operator.equals("!=")) {
				this.operator = "IS NOT NULL";
			}
		} else {
			if (right instanceof Collection) {
				this.setCondition(left, operator, (Collection) right);
			} else {
				this.operator = operator;
				this.right = "?";
				if (right instanceof ArgCollection) {
					this.parameters.addAll(((ArgCollection) right).getArgs());
				} else {
					this.parameters.add(right);
				}
			}
		}
	}

	public static HQLCondition getInstance(String left, String operator, Collection<Object> right) {
		HQLCondition cond = new HQLCondition();
		cond.setCondition(left, operator, right);
		return cond;
	}

	public void setCondition(String left, String operator, Collection<Object> right) {
		this.left = left;
		this.operator = operator;
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < right.size(); i++) {
			sb.append("?");
			if (i < (right.size() - 1)) {
				sb.append(",");
			}
		}
		sb.append(")");
		this.right = sb.toString();
		parameters.addAll(right);
	}

	public void concat(int relation, HQLCondition cond) {
		ConcatCondition concatCondition = new ConcatCondition();
		concatCondition.setCondition(cond);
		concatCondition.setRelation(relation);
		concatConditions.add(concatCondition);
	}

	private int getConditionCount() {
		int ccount = 0;
		if (left != null) {
			ccount++;
		}

		ccount += concatConditions.size();

		return ccount;
	}

	public HQLQuery toHQLQuery() throws MappingException {
		HQLQuery hqlCmd = new HQLQuery();
		StringBuffer sb = new StringBuffer("");

		if (not == true) {
			sb.append("NOT ");
		}

		if (left != null) {
			if (operator != null) {
				sb.append(left);
				sb.append(" ");
				sb.append(operator);

				if (right != null) {
					sb.append(" ");
					sb.append(right);
				}
			} else {
				// composited clause
				sb.append("(");
				sb.append(left);
				sb.append(")");
			}

			hqlCmd.addParameters(parameters);
		}

		for (Iterator<ConcatCondition> it = concatConditions.iterator(); it.hasNext();) {
			ConcatCondition concatCondition = it.next();
			HQLQuery hqlQuery1 = concatCondition.getCondition().toHQLQuery();
			if (sb.length() > 0) {
				if (concatCondition.getRelation() == AND) {
					sb.append(" AND ");
				} else {
					sb.append(" OR ");
				}
			}

			if (concatCondition.getCondition().getConditionCount() > 1) {
				sb.append("(");
				sb.append(hqlQuery1.getWhereClause());
				sb.append(")");
			} else {
				sb.append(hqlQuery1.getWhereClause());
			}

			hqlCmd.addParameters(hqlQuery1.getParameters());
		}

		hqlCmd.setWhereClause(sb.toString());
		return hqlCmd;
	}

	public String getLeft() {
		return left;
	}

	public String getOperator() {
		return operator;
	}

	public String getRight() {
		return right;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public List<ConcatCondition> getConcatConditions() {
		return concatConditions;
	}

}