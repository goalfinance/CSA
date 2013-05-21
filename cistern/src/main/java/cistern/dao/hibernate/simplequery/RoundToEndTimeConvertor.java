/*
 * File RoundToEndTimeConvertor.java
 * Created on 2004-7-14
 */
package cistern.dao.hibernate.simplequery;

import java.util.Calendar;
import java.util.Date;

import cistern.utils.DateUtil;

/**
 * ���뵽��ֹʱ���ת������
 * @author seabao
 * @project Cistern
 * @date 2004-7-14
 */
public class RoundToEndTimeConvertor implements ConditionPropertyConvertor {
    public Object convert(Object conditionProperty) {
        Date d = DateUtil.roundDate( (java.util.Date )conditionProperty, Calendar.DATE );
		return DateUtil.rollDate( d, Calendar.DATE, 1 );
    }

}
