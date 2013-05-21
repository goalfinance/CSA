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
 * @description �ַ���������
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
	 * ������ڸ�ʽ��
	 * @param dateSep ���ڷָ���
	 * @return ��ʽ��
	 */
	public static String datePattern(String dateSep) {
		return "yyyy" + dateSep + "MM" + dateSep + "dd";
	}

	/**
	 * ���ʱ���ʽ��
	 * @param timeSep ʱ��ָ���
	 * @return ��ʽ��
	 */
	public static String timePattern(String timeSep) {
		return "HH" + timeSep + "mm" + timeSep + "ss";
	}

	/**
	 * �������ʱ���ʽ��
	 * @param dateSep ���ڷָ���
	 * @param dateTimeSep ����ʱ��ָ���
	 * @param timeSep ʱ��ָ���
	 * @return ��ʽ��
	 */
	public static String dateTimePattern(String dateSep, String dateTimeSep, String timeSep) {
		return datePattern(dateSep) + dateTimeSep + timePattern(timeSep);
	}

	/**
	 * ���ո�ʽ����ʽ��Java���ݶ���
	 * @param pattern ��ʽ��
	 * @param obj Java���ݶ���
	 * @return ��ʽ������ַ���
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
	 * ���ո�ʽ����ʽ��Date����
	 * @param pattern ��ʽ��
	 * @param locale ��������
	 * @param d Date����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, Locale locale, java.util.Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(d);
	}

	/**
	 * ���ո�ʽ����ʽ��Date����
	 * @param pattern ��ʽ��
	 * @param d Date����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, java.util.Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * ���ո�ʽ����ʽ��BigDecimal����
	 * @param pattern ��ʽ��
	 * @param n BigDecimal����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, java.math.BigDecimal n) {
		if (n == null)
			return "";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * ���ո�ʽ����ʽ��Double����
	 * @param pattern ��ʽ��
	 * @param n Double����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, Double n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * ���ո�ʽ����ʽ��Float����
	 * @param pattern ��ʽ��
	 * @param n Float����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, Float n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * ���ո�ʽ����ʽ��Integer����
	 * @param pattern ��ʽ��
	 * @param n Integer����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, Integer n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * ���ո�ʽ����ʽ��Long����
	 * @param pattern ��ʽ��
	 * @param n Long����
	 * @return ��ʽ������ַ���
	 */
	public static String format(String pattern, Long n) {
		if (n == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	/**
	 * ���ո�ʽ�������ַ�����Date����
	 * @param pattern ��ʽ��
	 * @param locale ��������
	 * @param str �ַ���
	 * @return ��������Date����
	 */
	public static Date parseToDate(String pattern, Locale locale, String str) throws ParseException {
		if (str == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		return formatter.parse(str);
	}

	/**
	 * ���ո�ʽ�������ַ�����Date����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Date����
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
	 * ���ո�ʽ�������ַ�����BigInteger����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Integer����
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
	 * ���ո�ʽ�������ַ�����Byte����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Byte����
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
	 * ���ո�ʽ�������ַ�����Integer����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Integer����
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
	 * ���ո�ʽ�������ַ�����Long����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Long����
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
	 * ���ո�ʽ�������ַ�����Double����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Double����
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
	 * ���ո�ʽ�������ַ�����Float����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Float����
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
	 * ���ո�ʽ�������ַ�����Short����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������Short����
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
	 * ���ո�ʽ�������ַ�����BigDecimal����
	 * @param pattern ��ʽ��
	 * @param str �ַ���
	 * @return ��������BigDecimal����
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
	 * �������ַ��������ַ���������null
	 * @param str
	 * @return ��������String����
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