package cistern.dao.hibernate.simplequery;

import java.util.HashMap;
import java.util.Map;

/**
 * @project: Cistern
 * @description: SimpleQuery∂®“Â¿‡
 * @author: seabao
 * @create_time: 2007-12-12
 * @modify_time: 2007-12-12
 *
 */
public class SimpleQueryDefinition {
	private ConditionBean condition;
	
	private String defaultPersistenceAlias;
	
	private Map<String, ExpressionBean> expressions;

	public ConditionBean getCondition() {
		return condition;
	}

	public void setCondition(ConditionBean condition) {
		this.condition = condition;
	}
	
	public void addExpression(ExpressionBean expBean) {
		if ( expressions == null ) {
			expressions = new HashMap<String, ExpressionBean>();
		}
		
		expressions.put(expBean.getConditionProperty(), expBean);
	}

	public ExpressionBean getExpression(String conditionProperty) {
		ExpressionBean expression = expressions.get(conditionProperty);
		if ( expression == null ) {
			throw new RuntimeException("Not found the expression for [" + conditionProperty + "].");
		}
		
		return expression;
	}
	
	public Map<String, ExpressionBean> getExpressions() {
		return expressions;
	}

	public void setExpressions(Map<String, ExpressionBean> expressions) {
		this.expressions = expressions;
	}

	public String getDefaultPersistenceAlias() {
		return defaultPersistenceAlias;
	}

	public void setDefaultPersistenceAlias(String defaultPersistenceAlias) {
		this.defaultPersistenceAlias = defaultPersistenceAlias;
	}

}
