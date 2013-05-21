package cistern.dao.hibernate.simplequery;

import java.util.Calendar;

import cistern.dao.hibernate.simplequery.ConditionPropertyConvertor;
import cistern.utils.DateUtil;


/**
 * @project: Cistern
 * @description: ���뵽ʱ���ת������(��ȷ��Сʱ)
 * @author: GrayBat
 * @create_time: Oct 27, 2008
 * @modify_time: Oct 27, 2008
 */
public class RoundToTimeHourConvertor implements ConditionPropertyConvertor {
	public Object convert(Object conditionProperty) {
		return DateUtil.roundDate((java.util.Date) conditionProperty, Calendar.HOUR_OF_DAY);
	}
}
