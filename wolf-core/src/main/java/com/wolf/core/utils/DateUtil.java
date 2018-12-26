package com.wolf.core.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	public static final String ISO_DATE = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String HH_mm_ss = "HH:mm:ss";
	public static final String HHmmss = "HHmmss";

	public static String date19() {
		return format(new Date(), yyyy_MM_dd_HH_mm_ss);
	}

	public static String date10() {
		return format(new Date(), yyyy_MM_dd);
	}

	public static String date14() {
		return format(new Date(), yyyyMMddHHmmss);
	}

	public static String date8() {
		return format(new Date(), yyyyMMdd);
	}

	public static String time8() {
		return format(new Date(), HH_mm_ss);
	}

	public static String time6() {
		return format(new Date(), HHmmss);
	}

	public static String format(Date date, String pattern) {
		// return date == null ? null : new SimpleDateFormat(pattern).format(date);
		return date == null ? null : DateFormatUtils.format(date, pattern);
	}

	public static Date parse(String str, String pattern) {
		try {
			return DateUtils.parseDate(str, pattern);
		} catch (ParseException e) {
			return null;
		}
	}
}
