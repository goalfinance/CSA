/*
 * File DateUtil.java
 * Created on 2004-6-25
 *
 */
package cistern.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description 日期工具类
 * @author seabao
 * @project Cistern
 * @date 2004-6-25
 */
public class DateUtil {
	private static final int[] TIME_FIELD_LEVELS =
		{
			Calendar.YEAR,
			Calendar.MONTH,
			Calendar.DATE,
			Calendar.HOUR_OF_DAY,
			Calendar.MINUTE,
			Calendar.SECOND,
			Calendar.MILLISECOND };
    
    private static Map<String, Integer> periodUnits;
    
    static {
        periodUnits = new HashMap<String, Integer>();
        periodUnits.put("D", new Integer(Calendar.DATE) );
        periodUnits.put("M", new Integer(Calendar.MONTH) );
        periodUnits.put("W", new Integer(Calendar.WEEK_OF_MONTH) );
        periodUnits.put("Y", new Integer(Calendar.YEAR) );
        periodUnits.put("h", new Integer(Calendar.HOUR_OF_DAY));
        periodUnits.put("m", new Integer(Calendar.MINUTE));
        periodUnits.put("s", new Integer(Calendar.SECOND));
    }

	/**
	 * 对齐日期对象到指定精度
	 * @param date 日期对象
	 * @param field 要对齐的时间域，参考Calendar中field的定义
	 * @return 对齐后的日期
	 */
	public static Date roundDate(Date date, int field) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		roundDate(cal, field);

		return cal.getTime();
	}
	
	public static void roundDate(Calendar cal, int field) {
		boolean clearFlag = false;
		for (int i = 0; i < TIME_FIELD_LEVELS.length; i++) {
			if (clearFlag) {
				cal.set(TIME_FIELD_LEVELS[i], cal.getMinimum(TIME_FIELD_LEVELS[i]));
			} else if (TIME_FIELD_LEVELS[i] == field) {
				clearFlag = true;
			}
		}
	}

	/**
	 * 调整日期对象
	 * @param date 日期对象
	 * @param field 时间域，参考Calendar中field的定义
	 * @param amount 调整数量
	 * @return 调整后的日期对象
	 */
	public static Date rollDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(field, amount);
		return cal.getTime();
	}
	
	/**
	 * 获得日期对象的时间域值
	 * @param field 时间域，参考Calendar中field的定义
	 * @return 对应时间域的值
	 */
	public static int getDateField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	/**
	 * 获得修改时间域值后的日期对象
	 * @param date 日期对象
	 * @param field 时间域，参考Calendar中field的定义
	 * @param value 时间域的值
	 * @return 修改后的日期对象
	 */
	public static Date setDateField(Date date, int field, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(field, value);
		return cal.getTime();
	}
    
    public static PeriodDescription parsePeriod(String period) {
        PeriodDescription periodDesc = new PeriodDescription();
        for ( Iterator<String> it = periodUnits.keySet().iterator(); it.hasNext(); ) {
            String periodUnit = it.next();
            int index = period.lastIndexOf(periodUnit);
            if ( index >= 0 ) {
                periodDesc.setPeriodField(((Integer )periodUnits.get(periodUnit)).intValue());
                periodDesc.setPeriodCount(Integer.parseInt(period.substring(0, index), 10));
                
                return periodDesc;
            }
        }
        
        // default periodUnit is "D"
        periodDesc.setPeriodField(Calendar.DATE);
        periodDesc.setPeriodCount(Integer.parseInt(period, 10));
        
        return periodDesc;
    }

}