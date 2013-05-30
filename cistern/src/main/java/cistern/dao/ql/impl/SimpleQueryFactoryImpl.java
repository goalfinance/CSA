/*
 * File SimpleQueryFactoryImpl.java
 * Created on 2004-7-13
 */
package cistern.dao.ql.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import cistern.dao.ql.HQLCondition;
import cistern.dao.ql.HQLQuery;
import cistern.dao.ql.SimpleQueryFactory;
import cistern.dao.ql.annotation.Condition;
import cistern.dao.ql.annotation.Expression;
import cistern.dao.ql.annotation.Ignore;
import cistern.dao.ql.annotation.UnknownEntity;
import cistern.utils.StringUtil;

/**
 * SimpleQuery工厂实现类
 *
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public class SimpleQueryFactoryImpl implements SimpleQueryFactory {
	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * SimpleQuery定义集
	 */
	private Map<String, SimpleQueryDefinition> simpleQueryDefinitions = new HashMap<String, SimpleQueryDefinition>();

	/**
	 * 条件类集
	 */
	private Set<Class<Object>> conditions;

	/**
	 * 排序字段必须选择标志
	 */
	private boolean orderColumnMustBeSelect = false;

	private static Set<String> ignoreProperties = new HashSet<String>();

	static {
		ignoreProperties.add("class");
	}

	@SuppressWarnings("unchecked")
	private ExpressionBean convertToBean(Expression exp, String propertyName) {
		try {
			ExpressionBean expBean = new ExpressionBean();
			expBean.setAbsenceWhenNull(exp.absenceWhenNull());
			expBean.setAbsenceWhenNullCtrlProp(StringUtil.emptyAsNull(exp.absenceWhenNullCtrlProp()));
			expBean.setConditionProperty(StringUtil.emptyAsNull(exp.conditionProperty()));
			String convertor = StringUtil.emptyAsNull(exp.convertor());
			if (convertor != null) {
				expBean.setConvertor((Class<ConditionPropertyConvertor>) Class.forName(convertor));
			} else {
				Class<?> convertorClass = exp.convertorClass();
				if (convertorClass.equals(UnknownEntity.class) == false) {
					expBean.setConvertor((Class<ConditionPropertyConvertor>) convertorClass);
				}
			}

			expBean.setGenerateTemplate(StringUtil.emptyAsNull(exp.generateTemplate()));

			String generator = StringUtil.emptyAsNull(exp.generator());
			if (generator != null) {
				expBean.setGenerator((Class<HQLConditionGenerator>) Class.forName(generator));
			} else {
				Class<?> generatorClass = exp.generatorClass();
				if (generatorClass.equals(UnknownEntity.class) == false) {
					expBean.setGenerator((Class<HQLConditionGenerator>) generatorClass);
				}
			}

			expBean.setNonePersistenceProperty(exp.nonePersistenceProperty());
			expBean.setOperator(StringUtil.emptyAsNull(exp.operator()));
			expBean.setPersistenceAlias(StringUtil.emptyAsNull(exp.persistenceAlias()));
			expBean.setPersistenceProperty(StringUtil.emptyAsNull(exp.persistenceProperty()));

			if (expBean.getConditionProperty() == null) {
				expBean.setConditionProperty(propertyName);
			}

			return expBean;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void registerConditionImpl(Class<Object> clazz) {
		SimpleQueryDefinition def = new SimpleQueryDefinition();
		String condBeanName = clazz.getName();

		ConditionBean condBean = new ConditionBean();
		Condition condition = clazz.getAnnotation(Condition.class);
		if (condition == null) {
			throw new RuntimeException("The class=[" + clazz.getName() + "] isn't a SimpleQueryCondition bean.");
		}

		condBean.setFromClause(StringUtil.emptyAsNull(condition.fromClause()));
		condBean.setSelectClause(StringUtil.emptyAsNull(condition.selectClause()));
		condBean.setWhereClause(StringUtil.emptyAsNull(condition.whereClause()));
		if (condBean.getFromClause() == null) {
			if (condition.entity().equals(UnknownEntity.class)) {
				throw new RuntimeException("Invalid QueryCondition=[" + clazz.getName() + "].");
			}

			condBean.setFromClause(condition.entity().getName() + " entity");
			def.setDefaultPersistenceAlias("entity");
		} else {
			def.setDefaultPersistenceAlias(SimpleQueryUtil.getDefaultPersistenceAlias(condBean.getFromClause()));
		}

		def.setCondition(condBean);

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Expression.class)) {
				Expression exp = field.getAnnotation(Expression.class);
				ExpressionBean expBean = convertToBean(exp, field.getName());
				def.addExpression(expBean);
			}
		}

		try {
			Set<String> ignoredPropNames = new HashSet<String>();
			BeanInfo info = java.beans.Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				Method m = pd.getReadMethod();
				if (m == null)
					continue;
				if (m.isAnnotationPresent(Expression.class)) {
					Expression exp = m.getAnnotation(Expression.class);
					ExpressionBean expBean = convertToBean(exp, pd.getName());
					def.addExpression(expBean);
					if (expBean.getAbsenceWhenNullCtrlProp() != null) {
						ignoredPropNames.add(expBean.getAbsenceWhenNullCtrlProp());
					}
				} else if (m.isAnnotationPresent(Ignore.class)) {
					continue;
				} else if (ignoreProperties.contains(pd.getName()) == false
						&& ignoredPropNames.contains(pd.getName()) == false) {
					ExpressionBean expBean = new ExpressionBean();
					expBean.setAbsenceWhenNull(true);
					expBean.setConditionProperty(pd.getName());
					def.addExpression(expBean);
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		simpleQueryDefinitions.put(condBeanName, def);

		logger.info("Loaded simplequery condition bean, [" + condBeanName + "]");
	}

	@SuppressWarnings("unchecked")
	public void registerCondition(Class clazz) {
		try {
			registerConditionImpl(clazz);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@PostConstruct
	public void init() {
		if (conditions == null) {
			return;
		}

		for (Class<Object> clazz : conditions) {
			if (clazz.isAnnotationPresent(Condition.class)) {
				registerConditionImpl(clazz);
			}
		}
	}

	private HQLCondition genHQLConditionImpl(SimpleQueryDefinition sqDef, Object condition) throws Exception {
		HQLCondition hqlCond = new HQLCondition();
		String defaultPersistenceAlias = sqDef.getDefaultPersistenceAlias();
		ConditionMeta condMeta = new ConditionMeta(sqDef);
		ConditionProperties conditionProperties = new ConditionProperties(sqDef, condition);
		for (ExpressionBean expression : sqDef.getExpressions().values()) {
			if (expression.isNonePersistenceProperty()) {
				continue;
			}

			Object condProp = SimpleBeanAccess.getProperty(condition, expression.getConditionProperty());

			if (condProp == null) {
				boolean absenceWhenNull;
				if (expression.getAbsenceWhenNullCtrlProp() != null) {
					Boolean ctrlProp = (Boolean) SimpleBeanAccess.getProperty(condition, expression
							.getAbsenceWhenNullCtrlProp());
					if (ctrlProp == null) {
						absenceWhenNull = true;
					} else {
						absenceWhenNull = ctrlProp;
					}
				} else {
					absenceWhenNull = expression.isAbsenceWhenNull();
				}

				if (absenceWhenNull) {
					continue;
				}
			}

			if (expression.getGenerator() != null) {
				HQLConditionGenerator generator = expression.getGenerator().newInstance();
				HQLCondition hqlCond1 = generator.genHQLCondition(condMeta, conditionProperties, expression
						.getGenerateTemplate(), expression);
				if (hqlCond1 == null) {
					continue;
				}

				hqlCond.concat(HQLCondition.AND, hqlCond1);
			} else {
				if (expression.getConvertor() != null) {
					condProp = SimpleQueryUtil.convertProperty(expression.getConvertor(), condProp);

					if (condProp == null && expression.isAbsenceWhenNull()) {
						continue;
					}
				}

				String persistencePropName = expression.getPersistenceProperty();
				if (persistencePropName == null) {
					persistencePropName = expression.getConditionProperty();
				}

				String persistenceAlias = expression.getPersistenceAlias();
				if (persistenceAlias == null) {
					persistenceAlias = defaultPersistenceAlias;
				}

				String operator = SimpleQueryUtil.convertOperator(expression.getOperator());
				HQLCondition hqlCond1 = HQLCondition.getInstance(persistenceAlias + "." + persistencePropName,
						operator, condProp);
				hqlCond.concat(HQLCondition.AND, hqlCond1);
			}
		}

		return hqlCond;
	}

	private HQLQuery genHQLQueryImpl(SimpleQueryDefinition sqDef, Object condition) throws Exception {
		HQLCondition hqlCond = genHQLConditionImpl(sqDef, condition);

		String defaultPersistenceAlias = sqDef.getDefaultPersistenceAlias();

		HQLQuery hqlQuery = hqlCond.toHQLQuery();
		hqlQuery.setSelectClause(sqDef.getCondition().getSelectClause());
		hqlQuery.setFromClause(sqDef.getCondition().getFromClause());
		if (sqDef.getCondition().getWhereClause() != null) {
			MacroVariableContainer mvc = new MapMacroVariableContainer();
			String whereClause = hqlQuery.getWhereClause();
			mvc.setMacroVariable("condition-clause", whereClause);
			whereClause = MacroVariableInterpreter.interpret(sqDef.getCondition().getWhereClause(), "${", "}", mvc);
			hqlQuery.setWhereClause(whereClause);
		}

		if (condition instanceof OrderableCondition) {
			OrderableCondition orderCond = (OrderableCondition) condition;
			if (orderCond.getOrders() != null) {
				ParsedOrderableCondition poc = new ParsedOrderableCondition(orderCond.getOrders());

				if (hqlQuery.getSelectClause() == null || hqlQuery.getSelectClause().trim().equals("")) {
					hqlQuery.setSelectClause(defaultPersistenceAlias);
				}

				if (orderColumnMustBeSelect) {
					hqlQuery.setSelectClause(hqlQuery.getSelectClause() + ","
							+ poc.genSelectClause(defaultPersistenceAlias));
				}

				hqlQuery.setOrderClause(poc.genOrderClause(defaultPersistenceAlias));
			}
		}
		
		System.out.println("clause:" + hqlQuery.getClause());

		return hqlQuery;
	}

	public SimpleQueryDefinition getSimpleQueryDefinition(String clazzName) {
		SimpleQueryDefinition sqDef = simpleQueryDefinitions.get(clazzName);

		if (sqDef == null) {
			throw new RuntimeException("Not found the simpleQueryDefinition for " + clazzName);
		}

		return sqDef;
	}

	@SuppressWarnings("unchecked")
	public HQLQuery genHQLQuery(Class clazz, Object condition) {
		SimpleQueryDefinition sqDef = getSimpleQueryDefinition(clazz.getName());

		try {
			return genHQLQueryImpl(sqDef, condition);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public HQLCondition genHQLCondition(Class clazz, Object condition) {
		SimpleQueryDefinition sqDef = getSimpleQueryDefinition(clazz.getName());

		try {
			return genHQLConditionImpl(sqDef, condition);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public HQLCondition genHQLCondition(Object condition) {
		return genHQLCondition(condition.getClass(), condition);
	}

	public HQLQuery genHQLQuery(Object condition) {
		return genHQLQuery(condition.getClass(), condition);
	}

	@SuppressWarnings("unchecked")
	public ConditionMeta getConditionMeta(Class clazz) {
		SimpleQueryDefinition sqDef = getSimpleQueryDefinition(clazz.getName());

		return new ConditionMeta(sqDef);
	}

	public boolean isOrderColumnMustBeSelect() {
		return orderColumnMustBeSelect;
	}

	public void setOrderColumnMustBeSelect(boolean orderColumnMustSelect) {
		this.orderColumnMustBeSelect = orderColumnMustSelect;
	}

	public Set<Class<Object>> getConditions() {
		return conditions;
	}

	public void setConditions(Set<Class<Object>> conditions) {
		this.conditions = conditions;
	}
}