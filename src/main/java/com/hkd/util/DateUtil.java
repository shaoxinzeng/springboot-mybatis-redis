package com.hkd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具
 * 
 * @author gaoyuhai 2016-6-14 上午09:23:37
 */
public class DateUtil {
	/**
	 * 获取当前时间 format 格式
	 * 
	 * @return
	 */
	public static String getDateFormat(String format) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}

	/**
	 * 获取时间 format 格式
	 * 
	 * @return
	 */
	public static String getDateFormat(Date date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}
	/**
	 * 获取某时间-当前时间 format 格式
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static int getDateFormat(String endDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long days = 0;
		try {
			Date eDate = sdf.parse(endDate);
			Date date = sdf.parse(getDateFormat(format));

			long diff = eDate.getTime() - date.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {

		}
		return (int) days;
	}

	/**
	 * 获取某时间-某时间 format 格式
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getDateFormat(String endDate0, String endDate1,
			String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long days = 0;
		try {
			Date eDate0 = sdf.parse(endDate0);
			Date eDate1 = sdf.parse(endDate1);
			long diff = eDate1.getTime() - eDate0.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {

		}
		return String.valueOf(days);
	}

	/**
	 * 获取下n月
	 * 
	 * @return
	 */
	public static Date getNextMon(int month) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, +month);
		Date time = calendar.getTime();
		return time;
	}

	/**
	 * 获取前days日
	 * 
	 * @param days
	 * @return
	 */
	public static String getDateForDayBefor(int days, String format) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(calendar.getTime());
		return time;
	}

	public static void main(String[] args) {
		String str = getDateForDayBefor(1, "yyyy-MM-dd");
		System.out.println(str);
	}

	/**
	 * 时间相加
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int hour) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 月相加
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 年相加
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 根据字符串和指定格式生成日期
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateString, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String fyTimePattern = "yyyyMMdd"; // 富友交易日期
	/**
	 *
	 * return yyyyMMdd
	 *
	 * @param date
	 * @return
	 */
	public static String fyFormatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat(fyTimePattern);
		String sDate = f.format(date);
		return sDate;
	}
}
