/*
 * File HQLConditionGenerator.java
 * Created on 2004-7-13
 */
package cistern.dao.hibernate.simplequery;

import cistern.dao.hibernate.HQLCondition;

/**
 * HQL条件生成器接口定义类
 * @author seabao
 * @project Cistern
 * @date 2004-7-13
 */
public interface HQLConditionGenerator {
    /**
     * 生成HQL条件
     */
    public HQLCondition genHQLCondition(ConditionMeta conditionMeta, ConditionProperties condition, String genTemplate, ExpressionBean expBean);
}
