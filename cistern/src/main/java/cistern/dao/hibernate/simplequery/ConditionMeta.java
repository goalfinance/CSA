/*
 * File ConditionMeta.java
 * Created on 2004-7-14
 */
package cistern.dao.hibernate.simplequery;

/**
 * ���������Meta��
 * 
 * @author seabao
 * @project Cistern
 * @date 2004-7-14
 */
public class ConditionMeta {
	/**
	 * SimpleQuery�������
	 */
	private SimpleQueryDefinition simpleQueryDefinition;

	public ConditionMeta(SimpleQueryDefinition simpleQueryDefinition) {
		this.simpleQueryDefinition = simpleQueryDefinition;
	}

	public String hqlPropName(String property) {
		return getDefaultPersistenceAlias() + "." + property;
	}
	
	public String getDefaultPersistenceAlias() {
		return simpleQueryDefinition.getDefaultPersistenceAlias();
	}

	public SimpleQueryDefinition getSimpleQueryDefinition() {
		return simpleQueryDefinition;
	}
}