/*
 * File StringUtil.java
 * Created on 2004-6-25
 *
 */
package cistern.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @description 字符串工具类
 * @author seabao
 * @project Cistern
 * @date 2004-6-25
 */
public class StringUtil {
	private StringUtil() {
	}

	public static boolean isSame(String value1, String value2) {
		if ( value1 == null && value2 == null ) {
			return true;
		}
		
		if ( value1 == null && value2 != null ) {
			return false;
		}
		
		if ( value1 != null && value2 == null ) {
			return false;
		}
		
		return value1.equals(value2);
	}
	
	public static String emptyAsNull(String value) {
		if ( value == null ) {
			return value;
		}
		
		if (value.trim().equals("")) {
			return null;
		} else {
			return value;
		}
	}

	public static Boolean asBoolean(String value) {
		value = emptyAsNull(value);
		if (value == null) {
			return null;
		}

		return new Boolean(value);
	}
	
	public static boolean asBool(String value, boolean whenNull) {
		value = emptyAsNull(value);
		if (value == null) {
			return whenNull;
		}

		return new Boolean(value);
	}

	public static Integer asInteger(String value) {
		value = emptyAsNull(value);
		if (value == null) {
			return null;
		}

		return Integer.valueOf(value, 10);
	}
	
	public static int asInt(String value, int whenNull) {
		value = emptyAsNull(value);
		if (value == null) {
			return whenNull;
		}

		return Integer.valueOf(value, 10);
	}

	public static Object asReflect(String value) {
		try {
			int idx = value.lastIndexOf(".");
			String className = value.substring(0, idx);
			String fieldName = value.substring(idx + 1);
			Class<?> c = Class.forName(className);
			Field f = c.getField(fieldName);
			return f.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 获得日期格式串
	 * @param dateSep 日期分隔符
	 * @return 格式串
	 */
	public static String datePattern(String dateSep) {
		return "yyyy" + dateSep + "MM" + dateSep + "dd";
	}

	/**
	 * 获得时间格式串
	 * @param timeSep 时间分隔符
	 * @return 格式串
	 */
	public static String timePattern(String timeSep) {
		return "HH" + timeSep + "mm" + timeSep + "ss";
	}

	/**
	 * 获得日期时间格式串
	 * @param dateSep 日期分隔符
	 * @param dateTimeSep 日期时间分隔符
	 * @param timeSep 时间分隔符
	 * @return 格式串
	 */
	public static String dateTimePattern(String dateSep, String dateTimeSep, String timeSep) {
		return datePattern(dateSep) + dateTimeSep + timePattern(timeSep);
	}

	/**
	 * 按照格式串格式化Java数据对象
	 * @param pattern 格式串
	 * @param obj Java数据对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Object obj) {
		if (obj == null)
			return "";

		if (obj instanceof String) {
			return (String) obj;
		}

		if (pattern == null) {
			return obj.toString();
		}

		if (obj instanceof java.util.Date) {
			return format(pattern, (java.util.Date) obj);
		}

		if (obj instanceof java.math.BigDecimal) {
			return format(pattern, (java.math.BigDecimal) obj);
		}

		if (obj instanceof Double) {
			return format(pattern, (Double) obj);
		}

		if (obj instanceof Float) {
			return format(pattern, (Float) obj);
		}

		if (obj instanceof Long) {
			return format(pattern, (Long) obj);
		}

		if (obj instanceof Integer) {
			return format(pattern, (Integer) obj);
		}

		return obj.toString();
	}

	/**
	 * 按照格式串格式化Date对象
	 * @param pattern 格式串
	 * @param locale 区域属性
	 * @param d Date对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Locale locale, java.util.Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(d);
	}

	/**
	 * 按照格式串格式化Date对象
	 * @param pattern 格式串
	 * @param d Date对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, java.util.Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 按照格式串格式化BigDecimal对象
	 * @param pattern 格式串
	 * @param n BigDecimal对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, java.math.BigDecimal n) {
		if (n == null)
			return "";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * 按照格式串格式化Double对象
	 * @param pattern 格式串
	 * @param n Double对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Double n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * 按照格式串格式化Float对象
	 * @param pattern 格式串
	 * @param n Float对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Float n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * 按照格式串格式化Integer对象
	 * @param pattern 格式串
	 * @param n Integer对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Integer n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * 按照格式串格式化Long对象
	 * @param pattern 格式串
	 * @param n Long对象
	 * @return 格式化后的字符串
	 */
	public static String format(String pattern, Long n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * 按照格式串解析字符串到Date对象
	 * @param pattern 格式串
	 * @param locale 区域属性
	 * @param str 字符串
	 * @return 解析出的Date对象
	 */
	public static Date parseToDate(String pattern, Locale locale, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		return formatter.parse(str);
	}

	/**
	 * 按照格式串解析字符串到Date对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Date对象
	 */
	public static Date parseToDate(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(str);
	}

	public static java.sql.Date parseToSqlDate(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return new java.sql.Date(formatter.parse(str).getTime());
	}

	/**
	 * 按照格式串解析字符串到BigInteger对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Integer对象
	 */
	public static BigInteger parseToBigInteger(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return new BigInteger(str);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new BigInteger(df.parse(str).toString());
	}

	/**
	 * 按照格式串解析字符串到Byte对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Byte对象
	 */
	public static Byte parseToByte(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return Byte.valueOf(str, 10);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Byte(df.parse(str).byteValue());
	}

	/**
	 * 按照格式串解析字符串到Integer对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Integer对象
	 */
	public static Integer parseToInteger(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return Integer.valueOf(str, 10);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Integer(df.parse(str).intValue());
	}

	/**
	 * 按照格式串解析字符串到Long对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Long对象
	 */
	public static Long parseToLong(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return Long.valueOf(str, 10);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Long(df.parse(str).longValue());
	}

	/**
	 * 按照格式串解析字符串到Double对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Double对象
	 */
	public static Double parseToDouble(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return new Double(str);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Double(df.parse(str).doubleValue());
	}

	/**
	 * 按照格式串解析字符串到Float对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Float对象
	 */
	public static Float parseToFloat(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return new Float(str);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Float(df.parse(str).floatValue());
	}

	/**
	 * 按照格式串解析字符串到Short对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的Short对象
	 */
	public static Short parseToShort(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return Short.valueOf(str, 10);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new Short(df.parse(str).shortValue());
	}

	/**
	 * 按照格式串解析字符串到BigDecimal对象
	 * @param pattern 格式串
	 * @param str 字符串
	 * @return 解析出的BigDecimal对象
	 */
	public static BigDecimal parseToBigDecimal(String pattern, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		if (pattern == null) {
			return new BigDecimal(str);
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return new BigDecimal(df.parse(str).doubleValue());
	}

	/**
	 * 解析出字符串，空字符串解析出null
	 * @param str
	 * @return 解析出的String对象
	 */
	public static String parseToString(String str) {
		if (str == null) {
			return null;
		}

		str = str.trim();

		if (str.equals("")) {
			return null;
		}

		return str;
	}
	
	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}

}