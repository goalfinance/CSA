/*
 * File HQLConditionTemplateGenerator.java
 * Created on 2004-7-15
 */
package cistern.dao.ql.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cistern.dao.ql.HQLCondition;

/**
 * 给予模版的HQL条件生成器类
 * @author seabao
 * @project Cistern
 * @date 2004-7-15
 */
public class HQLConditionTemplateGenerator implements HQLConditionGenerator {
    public static final String DEFAULT_PERSISTENCE_ALIAS_NAME = "persistence-alias";

    public class ConditionMacroVariableContainer implements MacroVariableContainer {
        private List<Object> parameters = new ArrayList<Object>();

        private ConditionMeta conditionMeta;

        private ConditionProperties conditionProperties;

        @SuppressWarnings("unchecked")
        public Object getMacroVariable(String name) {
            if (name.equals(DEFAULT_PERSISTENCE_ALIAS_NAME)) {
                return conditionMeta.getDefaultPersistenceAlias();
            }

            if (SimpleQueryUtil.getOperatorConvertMap().containsKey(name)) {
                return SimpleQueryUtil.getOperatorConvertMap().get(name);
            }

            Object condProp = conditionProperties.getConditionProperty(name);

            if (condProp instanceof Collection) {
                StringBuffer sb = new StringBuffer();
                for (Iterator<Object> it = ((Collection<Object>) condProp).iterator(); it.hasNext();) {
                    parameters.add(it.next());
                    sb.append("?");
                    if (it.hasNext()) {
                        sb.append(", ");
                    }
                }

                return sb.toString();
            } else {
                parameters.add(conditionProperties.getConditionProperty(name));
                return "?";
            }
        }

        public void removeMacroVariable(String name) {
            throw new RuntimeException("Not support this method.");
        }

        public void setMacroVariable(String name, Object value) {
            throw new RuntimeException("Not support this method.");
        }
    }

    public HQLCondition genHQLCondition(ConditionMeta conditionMeta, ConditionProperties conditionProperties,
            String genTemplate, ExpressionBean expBean) {
        ConditionMacroVariableContainer mvc = new ConditionMacroVariableContainer();
        mvc.conditionMeta = conditionMeta;
        mvc.conditionProperties = conditionProperties;

        try {
            String hqlClause = MacroVariableInterpreter.interpret(genTemplate, "${", "}", mvc);
            return HQLCondition.getInstance(hqlClause, mvc.parameters);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}