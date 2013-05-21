package cistern.utils;

import java.io.*;
import java.text.*;

public class StringFormat {
	private StringFormat() {
	}

	public static String toString(String pattern, Object obj) {
		if (obj == null)
			return "";

		if (obj instanceof String) {
			return (String) obj;
		}

		if (pattern == null) {
			return obj.toString();
		}

		if (obj instanceof java.util.Date) {
			return toString(pattern, (java.util.Date) obj);
		}

		if (obj instanceof java.math.BigDecimal) {
			return toString(pattern, (java.math.BigDecimal) obj);
		}

		if (obj instanceof Double) {
			return toString(pattern, ((Double) obj).doubleValue());
		}

		if (obj instanceof Float) {
			return toString(pattern, ((Float) obj).floatValue());
		}

		if (obj instanceof Long) {
			return toString(pattern, ((Long) obj).longValue());
		}

		if (obj instanceof Integer) {
			return toString(pattern, ((Integer) obj).intValue());
		}

		return obj.toString();
	}

	public static String toString(String pattern, java.util.Date d) {
		if (d == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	public static String toString(String pattern, java.math.BigDecimal n) {
		if (n == null)
			return "";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String toString(String pattern, double n) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String toString(String pattern, float n) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String toString(String pattern, int n) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String toString(String pattern, long n) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String roundString(String value, boolean leftAlign, int maxLength) {
		try {
			StringBuffer sb = new StringBuffer(value);
			int length = value.getBytes("GBK").length;
			if (length < maxLength) {
				if (leftAlign) {
					for (int i = 0; i < (maxLength - length); i++) {
						sb.append(' ');
					}
				} else {
					for (int i = 0; i < (maxLength - length); i++) {
						sb.insert(0, ' ');
					}
				}
			}

			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static java.util.Date fromStringToDate(String pattern, String value) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(value);
	}

	public static java.math.BigDecimal fromStringToBigDecimal(String pattern, String value) {
		DecimalFormat df = new DecimalFormat(pattern);
		return new java.math.BigDecimal(df.parse(value, new ParsePosition(0)).doubleValue());
	}

	public static String moneyToString(java.math.BigDecimal value, String symbol, Integer scale) {
		if (value == null)
			return "";
		String svalue = doubleToNumberString(value.doubleValue(), null, scale);
		if (symbol != null)
			return symbol + svalue;
		return svalue;
	}

	public static String doubleToNumberString(double value, Integer width, Integer scale) {
		DecimalFormat df = new DecimalFormat();

		if (width != null && scale != null) {
			int integerDigits = 3 * (width.intValue() - scale.intValue()) / 4;
			df.setMaximumIntegerDigits(integerDigits);
		}

		df.setMinimumIntegerDigits(1);
		if (scale != null) {
			df.setMinimumFractionDigits(scale.intValue());
			df.setMaximumFractionDigits(scale.intValue());
		}
		return df.format(value);
	}

	public static String longToNumberString(long value, int width) {
		int integerDigits = 3 * (width + 1) / 4;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumIntegerDigits(integerDigits);
		df.setMinimumIntegerDigits(1);
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		return df.format(value);
	}

	public static java.util.Date toTime(String aString, String sep) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("hh" + sep + "mm" + sep + "ss");
		return formatter.parse(aString);
	}

	public static java.util.Date toDate(String aString, String sep) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy" + sep + "MM" + sep + "dd");
		return formatter.parse(aString);
	}

	public static java.util.Date toDateTime(String aString, String daySep, String dayTimeSep, String timeSep)
		throws ParseException {
		SimpleDateFormat formatter =
			new SimpleDateFormat(
				"yyyy" + daySep + "MM" + daySep + "dd" + dayTimeSep + "HH" + timeSep + "mm" + timeSep + "ss");
		return formatter.parse(aString);
	}

	public static String DateToString(java.util.Date aDate, String sep) {
		if (aDate == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + sep + "MM" + sep + "dd");
		return sdf.format(aDate);
	}

	public static String DateTimeToString(java.util.Date aDate, String daySep, String dayTimeSep, String timeSep) {
		SimpleDateFormat sdf =
			new SimpleDateFormat(
				"yyyy" + daySep + "MM" + daySep + "dd" + dayTimeSep + "HH" + timeSep + "mm" + timeSep + "ss");
		return sdf.format(aDate);
	}

	public static Boolean toBoolean(String value) {
		if (value == null)
			return null;

		if (value.equals("1") == true) {
			return Boolean.TRUE;
		}

		if (value.equals("0") == true) {
			return Boolean.FALSE;
		}

		return Boolean.valueOf(value);
	}
}