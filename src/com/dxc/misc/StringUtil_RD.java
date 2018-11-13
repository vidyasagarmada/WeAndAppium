package com.dxc.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtil_RD extends StringUtils {
	public static final char NULL = '\000';

	public static String getTitleCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String getRandomString(String format) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < format.length(); i++) {
			char c = format.charAt(i);
			char a =

			Character.isLetter(c) ? RandomStringUtils.randomAlphabetic(c)
					.charAt(0) : Character.isDigit(c) ? RandomStringUtils
					.randomNumeric(1).charAt(0) : c;
			sb.append(a);
		}
		return sb.toString();
	}

	public boolean containsNumbers(String str) {
		return str.matches(".*\\d.*");
	}

	public static String getFormatedDate(String dateString, String formatFrom,
			String formatTo) {
		SimpleDateFormat aformat = new SimpleDateFormat(formatFrom);
		SimpleDateFormat eformat = new SimpleDateFormat(formatTo);
		Date d;
		try {
		 d = aformat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		return eformat.format(d);
	}

	public static String createRandomString(String prefix) {
		Random r = new Random();

		String token = Long.toString(Math.abs(r.nextLong()), 36);

		return prefix + "_" + token;
	}

	public static String createRandomString() {
		Random r = new Random();

		String token = Long.toString(Math.abs(r.nextLong()), 36);

		return token;
	}

	public static boolean isNullOrEmpty(String s) {
		return (s == null) || (s.isEmpty());
	}

	public static boolean isXpath(String val) {
		return (!isNullOrEmpty(val))
				&& ((val.startsWith("//")) || (val.toLowerCase()
						.startsWith("xpath")));
	}

	public static String getWellFormedXPATH(String val) {
		String fstr = val;
		if (!val.toLowerCase().startsWith("xpath")) {
			fstr = "xpath=(" + val + ")[1]";
		}
		if (!val.endsWith("]")) {
			fstr = val + "[1]";
		}
		return fstr;
	}

	public static String extractParamValueFromUrl(String urlString,
			String paramName) {
		String retVal = "";
		String[] params = urlString.split("\\?|&");
		String[] arrayOfString1;
		int j = (arrayOfString1 = params).length;
		for (int i = 0; i < j; i++) {
			String param = arrayOfString1[i];
			if (param.startsWith(paramName.trim() + "=")) {
				retVal = param.substring(paramName.trim().length() + 1);
				break;
			}
		}
		return retVal;
	}

	public static boolean seleniumEquals(String expectedPattern, String actual) {
		if ((expectedPattern == null) || (actual == null)) {
			return expectedPattern == actual;
		}
		if ((actual.startsWith("regexp:")) || (actual.startsWith("regex:"))
				|| (actual.startsWith("regexpi:"))
				|| (actual.startsWith("regexi:"))
				|| (actual.startsWith("start:")) || (actual.startsWith("end:"))
				|| (actual.startsWith("in:"))) {
			String tmp = actual;
			actual = expectedPattern;
			expectedPattern = tmp;
		}
		if (expectedPattern.startsWith("start:")) {
			return actual
					.startsWith(expectedPattern.replaceFirst("start:", ""));
		}
		if (expectedPattern.startsWith("end:")) {
			return actual.endsWith(expectedPattern.replaceFirst("end:", ""));
		}
		if (expectedPattern.startsWith("in:")) {
			return actual.contains(expectedPattern.replaceFirst("in:", ""));
		}
		Boolean b = handleRegex("regexp:", expectedPattern, actual, 0);
		if (b != null) {
			return b.booleanValue();
		}
		b = handleRegex("regex:", expectedPattern, actual, 0);
		if (b != null) {
			return b.booleanValue();
		}
		b = handleRegex("regexpi:", expectedPattern, actual, 2);
		if (b != null) {
			return b.booleanValue();
		}
		b = handleRegex("regexi:", expectedPattern, actual, 2);
		if (b != null) {
			return b.booleanValue();
		}
		if (expectedPattern.startsWith("exact:")) {
			String expectedExact = expectedPattern.replaceFirst("exact:", "");
			if (!expectedExact.equals(actual)) {
				System.out.println("expected " + actual + " to match "
						+ expectedPattern);
				return false;
			}
			return true;
		}
		String expectedGlob = expectedPattern.replaceFirst("glob:", "");
		expectedGlob = expectedGlob.replaceAll(
				"([\\]\\[\\\\{\\}$\\(\\)\\|\\^\\+.])", "\\\\$1");

		expectedGlob = expectedGlob.replaceAll("\\*", ".*");
		expectedGlob = expectedGlob.replaceAll("\\?", ".");
		if (!Pattern.compile(expectedGlob, 32).matcher(actual).matches()) {
			System.out.println("expected \"" + actual + "\" to match glob \""
					+ expectedPattern
					+ "\" (had transformed the glob into regexp \""
					+ expectedGlob + "\"");
			return false;
		}
		return true;
	}

	public static Boolean handleRegex(String prefix, String expectedPattern,
			String actual, int flags) {
		if (expectedPattern.startsWith(prefix)) {
			String expectedRegEx = expectedPattern.replaceFirst(prefix, "");

			Pattern p = Pattern.compile(expectedRegEx, flags);
			if (!p.matcher(actual).matches()) {
				System.out.println("expected " + actual + " to match regexp "
						+ expectedPattern);
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
		return null;
	}

	public static Map<String, String> toMap(String csvKeyVal,
			boolean ensureKeyUppercase, char... ch) {
		String[] params = parseCSV(csvKeyVal, ch);

		return toMap(params, ensureKeyUppercase);
	}

	public static Map<String, String> toMap(String[] csvKeyVal,
			boolean ensureKeyUppercase) {
		WeakHashMap<String, String> map = new WeakHashMap<String, String>();
		if (csvKeyVal == null) {
			return map;
		}
		String[] arrayOfString1 = csvKeyVal;
		int j = csvKeyVal.length;
		for (int i = 0; i < j; i++) {
			String param = arrayOfString1[i];
			if (isNotBlank(param)) {
				String[] kv = param.split("=");
				map.put(ensureKeyUppercase ? kv[0].toUpperCase() : kv[0],
						kv.length > 1 ? kv[1] : "");
			}
		}
		return map;
	}

	public static String[] parseCSV(String data, char... ch) {
		List<String> values = new ArrayList<String>();
		char seperator = (ch == null) || (ch.length < 1) || (ch[0] == 0) ? ','
				: ch[0];
		char escapeChar = (ch == null) || (ch.length < 2) || (ch[1] == 0) ? '\\'
				: ch[1];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if (c == seperator) {
				values.add(sb.toString());
				sb = new StringBuilder();
			} else {
				if (c == escapeChar) {
					i++;
					c = data.charAt(i);
				}
				sb.append(c);
			}
		}
		values.add(sb.toString());

		return (String[]) values.toArray(new String[values.size()]);
	}

	public static Double[] extractNums(String s) {
		ArrayList<Double> lst = new ArrayList<Double>();
		Pattern p = Pattern.compile("-?\\d+.?\\d+");
		Matcher m = p.matcher(s);
		while (m.find()) {
			lst.add(Double.valueOf(Double.parseDouble(m.group())));
		}
		return (Double[]) lst.toArray(new Double[lst.size()]);
	}

	public static String commaSeparate(Collection<String> strings) {
		StringBuilder buffer = new StringBuilder();
		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			buffer.append(string);
			if (iterator.hasNext()) {
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}
}