/*
 * File ConditionProperties.java
 * Created on 2004-7-15
 */
package cistern.dao.hibernate.simplequery;

import java.util.HashMap;
import java.util.Map;


/**
 * 条件属性类
 * 
 * @author seabao
 * @project Cistern
 * @date 2004-7-15
 */
public class ConditionProperties {
	private SimpleQueryDefinition simpleQueryDefinition;

	private Map<String, Object> propertiesCache = new HashMap<String, Object>();

	private Object condition;

	ConditionProperties(SimpleQueryDefinition simpleQueryDefinition, Object condition) {
		this.simpleQueryDefinition = simpleQueryDefinition;
		this.condition = condition;
	}

	public Object getConditionProperty(String property) {
		if (propertiesCache.containsKey(property)) {
			return propertiesCache.get(property);
		}

		try {
			Object propertyValue = SimpleBeanAccess.getProperty(condition, property);

			ExpressionBean expression = simpleQueryDefinition.getExpression(property);
			if (expression.getConvertor() != null) {
				propertyValue = SimpleQueryUtil.convertProperty(expression.getConvertor(), propertyValue);
			}

			propertiesCache.put(property, propertyValue);

			return propertyValue;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}