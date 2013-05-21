/*
 * File HQLConditionGenerator.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

import cistern.dao.hibernate.HQLCondition;

/**
 * HQL�����������ӿڶ�����
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface HQLConditionGenerator {
    /**
     * ����HQL����
     */
    public HQLCondition genHQLCondition(ConditionMeta conditionMeta, ConditionProperties condition, String genTemplate, ExpressionBean expBean);
}
