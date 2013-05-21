/*
 * File SimpleQueryUtil.java
 * Created on 2004-7-14
 */
package cistern.dao.hibernate.simplequery;

import java.util.HashMap;
import java.util.Map;

/**
 * SimpleQuery的工具类
 * @author seabao
 * @project Cistern
 * @date 2004-7-14
 */
public class SimpleQueryUtil {
    public static final String OPERATOR_LT = "lt";

    public static final String OPERATOR_LE = "le";

    public static final String OPERATOR_EQ = "eq";

    public static final String OPERATOR_NEQ = "neq";

    public static final String OPERATOR_GE = "ge";

    public static final String OPERATOR_GT = "gt";

    private static Map<String, String> operatorConvertMap;

    static {
        operatorConvertMap = new HashMap<String, String>();
        operatorConvertMap.put(OPERATOR_LT, "<");
        operatorConvertMap.put(OPERATOR_LE, "<=");
        operatorConvertMap.put(OPERATOR_EQ, "=");
        operatorConvertMap.put(OPERATOR_NEQ, "!=");
        operatorConvertMap.put(OPERATOR_GE, ">=");
        operatorConvertMap.put(OPERATOR_GT, ">");
    }
    
    private SimpleQueryUtil() {
    }

    static Map<String, String> getOperatorConvertMap() {
        return operatorConvertMap;
    }
    
    public static String convertOperator(String operator) {
        if (operator == null) {
            operator = OPERATOR_EQ;
        }

        String lowerCaseOperator = operator.toLowerCase();
        String cOperator = (String) operatorConvertMap.get(lowerCaseOperator);
        if (cOperator != null) {
            return cOperator;
        } else {
            return operator;
        }
    }
    
    public static String getDefaultPersistenceAlias(String fromClause) {
        int s = fromClause.indexOf(" ");
        String aliasLeadStr;
        if (s < 0) {
            aliasLeadStr = fromClause;
        } else {
            aliasLeadStr = fromClause.substring(s + 1);
        }

        s = aliasLeadStr.indexOf(",");
        if (s < 0) {
            return aliasLeadStr.trim();
        }

        return aliasLeadStr.substring(0, s).trim();
    }

    public static Object convertProperty(Class<ConditionPropertyConvertor> convertorClazz, Object condProp) throws Exception {
        ConditionPropertyConvertor convertor = convertorClazz.newInstance();
        return convertor.convert(condProp);
    }
}