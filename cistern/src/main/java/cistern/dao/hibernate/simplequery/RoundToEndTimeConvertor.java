/*
 * File RoundToEndTimeConvertor.java
 * Created on 2004-7-14
 */
package cistern.dao.hibernate.simplequery;

import java.util.Calendar;
import java.util.Date;

import cistern.utils.DateUtil;

/**
 * 对齐到截止时间的转换器类
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
