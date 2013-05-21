package cistern.dao.hibernate.simplequery;

import cistern.dao.hibernate.HQLCondition;

/**
 * @project: Cistern
 * @description: Ϊ������������ʵ����
 * @author: seabao
 * @create_time: 2008-7-8
 * @modify_time: 2008-7-8
 */
public class IsNullHQLConditionGenerator implements HQLConditionGenerator {

	public HQLCondition genHQLCondition(ConditionMeta conditionMeta, ConditionProperties condition, String genTemplate,
			ExpressionBean expBean) {
		Boolean value = (Boolean) condition.getConditionProperty(expBean.getConditionProperty());
		if (value == null) {
			return null;
		}

		if (value.booleanValue()) {
			return HQLCondition.getInstance(conditionMeta.getDefaultPersistenceAlias() + "."
					+ expBean.getPersistenceProperty(), "IS NULL");
		} else {
			return HQLCondition.getInstance(conditionMeta.getDefaultPersistenceAlias() + "."
					+ expBean.getPersistenceProperty(), "IS NOT NULL");
		}
	}

}
