/*
 * File RoundToStartTimeConvertor.java
 * Created on 2004-7-14
 */
package cistern.dao.hibernate.simplequery;

import java.util.Calendar;

import cistern.utils.DateUtil;

/**
 * ���뵽��ʼʱ���ת������
 * @author seabao
 * @project Cistern
 * @date 2004-7-14
 */
public class RoundToStartTimeConvertor implements ConditionPropertyConvertor {
    public Object convert(Object conditionProperty) {
        return DateUtil.roundDate( (java.util.Date )conditionProperty, Calendar.DATE );
    }

}
