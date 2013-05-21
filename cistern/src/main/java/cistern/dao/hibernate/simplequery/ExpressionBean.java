package cistern.dao.hibernate.simplequery;

/**
 * @project: Cistern
 * @description: 表达式Bean类
 * @author: seabao
 * @create_time: 2007-12-12
 * @modify_time: 2007-12-12
 *
 */
public class ExpressionBean {
	private String persistenceAlias;

	private Class<ConditionPropertyConvertor> convertor;

	private Class<HQLConditionGenerator> generator;

	private String conditionProperty;

	private boolean absenceWhenNull;

	private String absenceWhenNullCtrlProp;

	private String operator;

	private String persistenceProperty;

	private String generateTemplate;

	private boolean nonePersistenceProperty;

	public String getPersistenceAlias() {
		return persistenceAlias;
	}

	public void setPersistenceAlias(String persistenceAlias) {
		this.persistenceAlias = persistenceAlias;
	}

	public Class<ConditionPropertyConvertor> getConvertor() {
		return convertor;
	}

	public void setConvertor(Class<ConditionPropertyConvertor> convertor) {
		this.convertor = convertor;
	}

	public Class<HQLConditionGenerator> getGenerator() {
		return generator;
	}

	public void setGenerator(Class<HQLConditionGenerator> generator) {
		this.generator = generator;
	}

	public String getConditionProperty() {
		return conditionProperty;
	}

	public void setConditionProperty(String conditionProperty) {
		this.conditionProperty = conditionProperty;
	}

	public boolean isAbsenceWhenNull() {
		return absenceWhenNull;
	}

	public void setAbsenceWhenNull(boolean absenceWhenNull) {
		this.absenceWhenNull = absenceWhenNull;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPersistenceProperty() {
		return persistenceProperty;
	}

	public void setPersistenceProperty(String persistenceProperty) {
		this.persistenceProperty = persistenceProperty;
	}

	public String getGenerateTemplate() {
		return generateTemplate;
	}

	public void setGenerateTemplate(String generateTemplate) {
		this.generateTemplate = generateTemplate;
	}

	public boolean isNonePersistenceProperty() {
		return nonePersistenceProperty;
	}

	public void setNonePersistenceProperty(boolean nonePersistenceProperty) {
		this.nonePersistenceProperty = nonePersistenceProperty;
	}

	public String getAbsenceWhenNullCtrlProp() {
		return absenceWhenNullCtrlProp;
	}

	public void setAbsenceWhenNullCtrlProp(String absenceWhenNullCtrlProp) {
		this.absenceWhenNullCtrlProp = absenceWhenNullCtrlProp;
	}
}
