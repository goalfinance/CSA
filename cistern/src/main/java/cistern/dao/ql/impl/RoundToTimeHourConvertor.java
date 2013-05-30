package cistern.dao.ql.impl;

import java.util.Calendar;

import cistern.dao.ql.impl.ConditionPropertyConvertor;
import cistern.utils.DateUtil;


/**
 * @project: Cistern
 * @description: 对齐到时间的转换器类(精确到小时)
 * @author: GrayBat
 * @create_time: Oct 27, 2008
 * @modify_time: Oct 27, 2008
 */
public class RoundToTimeHourConvertor implements ConditionPropertyConvertor {
	public Object convert(Object conditionProperty) {
		return DateUtil.roundDate((java.util.Date) conditionProperty, Calendar.HOUR_OF_DAY);
	}
}
