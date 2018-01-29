/*
 * Created on 2006-4-4 11:53:14, by luoka
 */
package com.file.manager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * 日期和时间相关工具方法. <BR>
 * 
 * @author anychem
 */
public class DateUtil {
	
	private static final Logger LOG = Logger.getLogger(DateUtil.class);

	/**
	 * @since luoka @ Mar 8, 2011
	 */
	static final String STR_YYYYMMDD_HH = "yyyy-MM-dd HH";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 默认时间格式
	 */
	public static final String FMT_TRS_yMdHm = "yyyy.MM.dd HH:mm" ;
	/**
	 * 默认时间格式
	 */
	public static final String FMT_TRS_yMd = "yyyy.MM.dd" ;
	
	/**
	 * 默认时间格式
	 */
	public static final String FMT_CRC_yMd = "yyyy年MM月dd日" ;
	
	/**
	 * 默认时间格式
	 */
	public static final String FMT_CRC_yMdHm = "yyyy年MM月dd日 HH:mm时" ;

	/**
	 * 一天的总秒数
	 */
	public static final long ONE_DAY_SECONDS = 24 * 3600;
	/**
	 * 一小时的总秒数
	 */
	public static final long ONE_HOUR_SCONDS = 3600;
	/**
	 * 一分钟的总秒数
	 */
	public static final long ONE_MINUTE_SCONDS = 60;

	/**
	 * 默认格式.
	 */
	public final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/**
	 * 默认日期格式.
	 */
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * 默认时间格式.
	 */
	public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm:ss");
	/**
	 * 精确到小时的时间格式.
	 */
	public final static SimpleDateFormat TIMEAREA_FORMAT = new SimpleDateFormat(
			STR_YYYYMMDD_HH);
	/**
	 * 精确到分钟的时间格式.
	 */
	public final static SimpleDateFormat TIMEAREA_MIN_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @param pattern
	 *            与time一致的时间格式
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */

	public static final long getDateAsMillis(String time, String pattern) {
		if (time == null) {
			return -1;
		}
		Date theTime = stringToDate(time,
				(pattern == null) ? DEFAULT_TIME_PATTERN : pattern);
		if (theTime == null) {
			return -1;
		}
		return theTime.getTime();
	}

	/**
	 * 将使用的毫秒数转化为可读的字符串, 如1天1小时1分1秒. <BR>
	 * <code>assertEquals("1天1小时1分1秒", DateUtil.timeToString(90061000));</code>
	 * 
	 * @param msUsed
	 *            使用的毫秒数.
	 * @return 可读的字符串, 如1天1小时1分1秒.
	 */
	public static String timeToString(long msUsed) {
        if (msUsed < 0) {
            return String.valueOf(msUsed);
        }
        if (msUsed < 1000) {
			return String.valueOf(msUsed) + "毫秒";
        }
		// 长于1秒的过程，毫秒不计
        msUsed /= 1000;
        if (msUsed < 60) {
			return String.valueOf(msUsed) + "秒";
        }
        if (msUsed < 3600) {
            long nMinute = msUsed / 60;
            long nSecond = msUsed % 60;
			return String.valueOf(nMinute) + "分" + String.valueOf(nSecond) + "秒";
        }
        //3600 * 24 = 86400
        if (msUsed < 86400) {
            long nHour = msUsed / 3600;
            long nMinute = (msUsed - nHour*3600) / 60;
            long nSecond = (msUsed - nHour*3600) % 60;
			return String.valueOf(nHour) + "小时" + String.valueOf(nMinute) + "分" + String.valueOf(nSecond) + "秒";
        }

        long nDay = msUsed / 86400;
        long nHour = (msUsed - nDay*86400) / 3600;
        long nMinute = (msUsed - nDay*86400 - nHour*3600) / 60;
        long nSecond = (msUsed - nDay*86400 - nHour*3600) % 60;
		return String.valueOf(nDay) + "天" + String.valueOf(nHour) + "小时" + String.valueOf(nMinute) + "分" + String.valueOf(nSecond) + "秒";
    }

	/**
	 * 以"HH:mm:SS"格式显示一段时间, 如果不存在小时部分, 则仅显示"mm:SS"形式.
	 * 
	 * @param seconds
	 *            时间长度
	 * @since luoka @ Jul 7, 2011
	 */
//	public static String secondsToHMS(int seconds) {
//		return new Duration(seconds).defaultShow();
//	}

	/**
	 * 取本周一.
	 * 
	 * @return 本周一
	 */
    public static Calendar getThisMonday() {
        return getThatMonday(Calendar.getInstance());
    }

	/**
	 * 获取cal所在周的周一.
	 * 
	 * @param cal
	 *            给定日期
	 * @return cal所在周的周一
	 */
    public static Calendar getThatMonday(Calendar cal) {
        int n = cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        cal.add(Calendar.DATE, n);
        return cal;
    }

	/**
	 * 取本周日.
	 * 
	 * @return 本周日
	 */
    public static Calendar getThisSunday() {
        return getThatSunday(Calendar.getInstance());
    }

	/**
	 * 获取cal所在周的周日.
	 * 
	 * @param cal
	 *            给定日期
	 * @return cal所在周的周日
	 */
    public static Calendar getThatSunday(Calendar cal) {
        int n = (Calendar.SUNDAY + 7) - cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, n);
        return cal;
    }

	/**
	 * 获取两个日期相差的天数.
	 * 
	 * @return 两个日期相差的天数.
	 */
    public static int minus(Calendar cal1, Calendar cal2) {
        if (cal1.after(cal2)) {
            int nBase = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 365;
            return nBase + cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);
        } 
        return minus(cal2, cal1);
    }

	/**
	 * 获取当前年份.
	 * 
	 * @since luoka @ Jun 13, 2011
	 */
	public static int getThisYear() {
		return getYear(System.currentTimeMillis());
	}

	/**
	 * 获取当前年份.
	 * 
	 * @since luoka @ Jun 13, 2011
	 */
	public static int getYear(long timestamp) {
		Date now = new Date(timestamp);
		Calendar cal = toCalendar(now);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 从Date对象得到Calendar对象. <BR>
	 * JDK提供了Calendar.getTime()方法, 可从Calendar对象得到Date对象,
	 * 但没有提供从Date对象得到Calendar对象的方法.
	 * 
	 * @param date
	 *            给定的Date对象
	 * @return 得到的Calendar对象. 如果date参数为null, 则得到表示当前时间的Calendar对象.
	 */
    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return cal;
    }

	/**
	 * 获取当前的时间（毫秒）
	 * 
	 * @return 当前的时间，以毫秒为单位
	 * @since fangxiang @ May 15, 2010
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前系统时间的毫秒数，可以指定想要获取的时间格式<br>
	 * 如需要获取当前系统时间精确到日期的毫秒数，则时间格式为yyyy-MM-dd<br>
	 * 如需要获取当前系统时间精确到小时的毫秒数，则时间格式为yyyy-MM-dd HH<br>
	 * 若传入null或者空或者不符合规定的格式，则返回精确到毫秒的时间，即System.currentTimeMillis()
	 * 
	 * @param dateFormat
	 *            指定当前时间的格式
	 * @return
	 * @since zhangshi @ 2011-11-11
	 */
	public static long getCurrentTimeMillisWithFormat(String dateFormat) {
		if (StringUtils.isEmpty(dateFormat))
			return getCurrentTimeMillis();

		String date = DateUtil.date2String(new Date(), dateFormat);
		if (null == date)
			return getCurrentTimeMillis();

		long currentSystemTimeWithFormat = DateUtil.getDateAsMillis(date, dateFormat, 0);
		return currentSystemTimeWithFormat;
	}

	/**
	 * 完成日期串到日期对象的转换. <BR>
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param dateFormat
	 *            日期格式
	 * @return date 日期对象
	 */
    public static Date stringToDate(String dateString, String dateFormat) {
        if ("".equals(dateString) || dateString == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(dateFormat).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

	/**
	 * 获取和指定cal对象相隔指定天数的cal对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param cal
	 *            指定cal对象
	 * @param relativeDay
	 *            相隔指定天数
	 * @return cal对象
	 */
    public static Calendar getCalendar(Calendar cal, int relativeDay) {
        cal.add(Calendar.DATE, relativeDay);
        return cal;
    }

	/**
	 * 获取和当天相隔指定天数的Date对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param relativeDay
	 *            相隔指定天数
	 * @return Date对象
	 * @see #getCalendar(Calendar, int)
	 */
    public static Date getDate(int relativeDay) {
        return getCalendar(Calendar.getInstance(), relativeDay).getTime();
    }

    public static String date2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            return null;
        }
    }

	/**
	 * 检查一个时间是否接近于一个时间。
	 * 
	 * @param date
	 *            要比较的时间
	 * @param baseDate
	 *            基础时间
	 * @param seconds
	 *            秒数
	 * 
	 * @return 如果 date 在 baseDate前后 seonds 秒数，则返回true，否则返回false。
	 */
    public static boolean isDateClose(Date date, Date baseDate, int seconds){

		long m_time = date.getTime() ;
		long b_time = baseDate.getTime() ;
		long ms = seconds * 1000 ;

		if(m_time == b_time) return true ;

		if (m_time > b_time) { // 现在时间在基础时间之前
			return b_time + ms > m_time ;
		} else if (m_time < b_time) { // 现在时间在基础时间之后
			return m_time + ms > b_time ;
		} else { // 同一个时间
			return true ;
		}
	}

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @param pattern
	 *            与time一致的时间格式
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */

	public static final long getDateAsMillis(String time, String pattern,
			int deltaDay) {
		if (time == null) {
			return -1;
		}
		Date theTime = stringToDate(time,
				(pattern == null) ? DEFAULT_TIME_PATTERN : pattern);
		if (theTime == null) {
			return -1;
		}
		//
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theTime);
		calendar.add(Calendar.DAY_OF_MONTH, deltaDay);
		//
		return theTime.getTime();
	}

	/**
	 * 转换时间成毫秒
	 * 
	 * @param time
	 *            待转换的时间
	 * @return 转换后的毫秒数
	 * @since fangxiang @ Apr 17, 2010
	 */
	public static final long getDateAsMillis(String time) {
		return getDateAsMillis(time, DEFAULT_TIME_PATTERN);
	}

	/**
	 * 将Date类型转换成时间表达形式
	 * 
	 * @param date
	 *            待转换的Date类型
	 * @return 转换后的时间表达形式
	 * @since fangxiang @ Apr 17, 2010
	 */
	public static final String date2String(Date date) {
		return date2String(date, DEFAULT_TIME_PATTERN);
    }

	/**
	 * 将整数型的时间转换成字符串格式的时间
	 * 
	 * @param dateMillis
	 *            毫秒时间
	 * @param pattern
	 *            字符串的格式
	 * @return 转换后的时间字符串
	 * @since fangxiang @ May 31, 2010
	 */
	public static final String millis2String(long dateMillis, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(dateMillis));
	}

	/**
	 * 获取指定月份起始点的毫秒数
	 * 
	 * @param date
	 *            指定月份
	 * @param pattern
	 *            时间格式
	 * @return 月份起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getMonthStartAsMillis(String date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date, pattern));
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return cal.getTime().getTime();
	}

	/**
	 * 获取指定月份结束点的毫秒数
	 * 
	 * @param date
	 *            指定月份
	 * @param pattern
	 *            时间格式
	 * @return 月份结束点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getMonthEndAsMillis(String date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date, pattern));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime().getTime();
	}

	/**
	 * 获取指定日期结束点的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @param pattern
	 *            时间格式
	 * @return 日期起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getDayEndAsMillis(String date, String pattern) {
		Calendar current = toCalendar(stringToDate(date,
				pattern));
		current.roll(Calendar.DAY_OF_MONTH, true);
		return current.getTimeInMillis();
	}

	/**
	 * 获取下一天的间隔
	 * 
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static final long getDayDurationAsMillis(int duration) {
		Calendar current = Calendar.getInstance();
		Calendar nextDay = Calendar.getInstance();
		nextDay.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH)
				+ duration);
		nextDay.set(Calendar.HOUR, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		return nextDay.getTimeInMillis() - current.getTimeInMillis();
	}

	/**
	 * 获取下一天的间隔
	 * 
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static final long getHourDurationAsMillis(int duration) {
		Calendar current = Calendar.getInstance();
		Calendar nextHour = Calendar.getInstance();
		nextHour.set(Calendar.HOUR, current.get(Calendar.HOUR) + duration);
		nextHour.set(Calendar.MINUTE, 0);
		nextHour.set(Calendar.SECOND, 0);
		return nextHour.getTimeInMillis() - current.getTimeInMillis();
	}

	/**
	 * 获取指定日期起始点的毫秒数
	 * 
	 * @param date
	 *            指定日期
	 * @param pattern
	 *            时间格式
	 * @return 日期起始点的毫秒数
	 * @since fangxiang @ May 31, 2010
	 */
	public static final long getDayStartAsMillis(String date, String pattern) {
		Calendar current = toCalendar(stringToDate(date,
				pattern));
		current.roll(Calendar.DAY_OF_MONTH, true);
		return current.getTimeInMillis();
	}

	/**
	 * 获取给定时刻(即和此刻相差多少天)的毫秒数.
	 * 
	 * @param deltaDay
	 *            多少天以前；大于0表示过去，小于0表示将来
	 * @return 毫秒数
	 * @since luoka @ Mar 9, 2011
	 */
	public static long getMillisForDeltaDay(int deltaDay) {
		long deltaMillis = deltaDay * 1000 * 60 * 60 * 24;
		return System.currentTimeMillis() - deltaMillis;
	}

	/**
	 * 将整数型的毫秒数转换成精确到分钟的时间格式
	 * 
	 * @param timeMillis
	 *            整数型的毫秒数
	 * @return 字符串类型的时间
	 */
	public static String formatMillisMin(long timeMillis) {
		return TIMEAREA_MIN_FORMAT.format(new Date(timeMillis));
	}

	/**
	 * 将整数型的毫秒数转换成默认的时间格式
	 * 
	 * @param timeMillis
	 *            整数型的毫秒数
	 * @return 字符串类型的时间
	 */
	public static String formatMillis(long timeMillis) {
		return DEFAULT_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 * @since cl @ 2010-4-28
	 */
	public static String formatTimeArea(long timeMillis) {
		return TIMEAREA_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis2Date(long timeMillis) {
		return DATE_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis2Time(long timeMillis) {
		return TIME_FORMAT.format(new Date(timeMillis));
	}

	/**
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatMillis(long timeMillis, String format) {
		return formatMillis(timeMillis, format, Locale.SIMPLIFIED_CHINESE);
	}

	/**
	 * 按指定地域的习惯来显示时间.
	 * 
	 * @since luoka @ May 25, 2011
	 */
	public static String formatMillis(long timeMillis, String format,
			Locale locale) {
		SimpleDateFormat sdf = (locale == null) ? new SimpleDateFormat(format)
				: new SimpleDateFormat(format, locale);
		return sdf.format(new Date(timeMillis));
	}

	/**
	 * 将<code>{@link DateUtil#DATE_FORMAT}</code>格式的日期字符串转换为当天0时的毫秒时间
	 * 
	 * @param date
	 *            <code>{@link DateUtil#DATE_FORMAT}</code>格式的日期字符串
	 * @return 当天0时的毫秒时间
	 * @creator changpeng @ 2009-4-30
	 */
	public static long formatDate2Millis(String date) {
		try {
			return DATE_FORMAT.parse(date).getTime();
		} catch (ParseException e) {
			LOG.error("parse date [" + date + "] fail", e);
			return 0;
		}
	}

	/**
	 * 转换日期字符串为当前小时数.
	 * 
	 * @param sDate
	 *            格式为 {@link #TIMEAREA_FORMAT}
	 * @return 整型表示的时间.
	 * @since cl @ 2010-4-28
	 */
	public static long formatTimeArea(String sDate) {
		Date date = null;
		try {
			date = TIMEAREA_FORMAT.parse(sDate);
			return date.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 将<code>{@link DateUtil#DEFAULT_FORMAT}</code>格式的时间转换为毫秒时间
	 * 
	 * @param dateTime
	 * @return
	 * @creator changpeng @ 2009-5-14
	 */
	public static long formatDateTime(String dateTime) {
		try {
			return DEFAULT_FORMAT.parse(dateTime).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 *
	 * @param dateTime
	 * @return
	 */
	public static Date format(String dateTime) {
		try {
			return DEFAULT_FORMAT.parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 *
	 * @param dateTime
	 * @return
	 */
	public static String format2String(Date date) {
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 将<code>{@link DateUtil#DEFAULT_FORMAT}</code>格式的时间转换为毫秒时间，若格式不正确返回默认值
	 * 
	 * @param dateTime
	 * @return
	 * @creator changpeng @ 2009-5-14
	 */
	public static long formatDateTime(String dateTime, long defaultValue) {
		try {
			return DEFAULT_FORMAT.parse(dateTime).getTime();
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	/**
	 * 将时间戳显示为<code>{@link DateUtil#DEFAULT_FORMAT}</code>的格式，若时间戳为负值则返回默认值.
	 * 
	 * @param timeMillis
	 *            时间戳
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @creator changpeng @ 2009-5-20
	 */
	public static String formatMillisWithDefaultValue(long timeMillis,
			String defaultValue) {
		return (timeMillis < 0) ? defaultValue : DEFAULT_FORMAT
				.format(new Date(timeMillis));
	}

	/**
	 * 将毫秒时间转换为Date类型，若小于0则返回null
	 * 
	 * @param millis
	 * @return
	 * @creator changpeng @ 2009-5-20
	 */
	public static Date millis2DateType(long millis) {
		return (millis < 0) ? null : new Date(millis);
	}

	/**
	 * 获取当前的日期时间的字符串表示
	 * 
	 * @return
	 * @creator changpeng @ 2009-6-1
	 */
	public static String getCurrentDateTime() {
		return formatMillis(System.currentTimeMillis());
	}

	/**
	 * 根据年月日构建<code>Calendar</code>对象.
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月，取值为0-11, 0表示一月.
	 * @param date
	 * @return
	 * @creator luoka @ Feb 18, 2010
	 */
	public static Calendar getCalendar(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar;
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * 
	 * @param date
	 * @return
	 * @since fangxiang @ May 31, 2010
	 */
	public static int getMondayPlus(long date) {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		cd.setTimeInMillis(date);
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		}
		return 1 - dayOfWeek;
	}

	/**
	 * 获得上周星期一的日期
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 上个星期一凌晨的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getPreviousWeekday(long date) {
		int weeks = -1;
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		return currentDate.getTimeInMillis();
	}

	/**
	 * 获得本周一的日期
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 本周一凌晨的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getMondayOFWeek(long date) {
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		return currentDate.getTimeInMillis();
	}

	/**
	 * 上月第一天的时间
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 上月第一天的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getPreviousMonthFirst(long date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(date);
		lastDate.set(Calendar.DATE, 1); // 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1); // 减一个月，变为下月的1号
		return lastDate.getTimeInMillis();
	}

	/**
	 * 获取当月第一天的时间
	 * 
	 * @param date
	 *            long型的时间格式
	 * @return 当月第一天的时间
	 * @since cl @ 2010-4-29
	 */
	public static long getFirstDayOfMonth(long date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeInMillis(date);
		lastDate.set(Calendar.DATE, 1); // 设为当前月的1号
		return lastDate.getTimeInMillis();
	}

	/**
	 * 以默认的长格式, 显示当前时间.
	 * 
	 * @since luoka @ Dec 6, 2010
	 */
	public static String nowAsLongFormat() {
		return formatMillis(getCurrentTimeMillis(), "yyyy年MM月dd日 E");
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @since fangxiang @ Dec 23, 2010
	 */
	public static String format2String(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 获取今天剩余的总秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getTodayRemainAsSecond() {
		return (getNextDayBeginAsMillis() - DateUtil.getCurrentTimeMillis()) / 1000;
	}

	/**
	 * 获取本小时剩余的总秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getCurrentHourRemainAsSecond() {
		return (getNextHourBeginAsMillis() - DateUtil.getCurrentTimeMillis()) / 1000;
	}

	/**
	 * 获取明天的开始毫秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextDayBeginAsMillis() {
		return getNextDayBeginAsMillis(1);
	}

	/**
	 * 获取指定N天后的开始毫秒数
	 * 
	 * @param day
	 *            间隔天数，从1开始；
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextDayBeginAsMillis(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days); // 当前时间加一 ，然后时分秒设为0，00:00:00
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取下一个小时开始的毫秒数
	 * 
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextHourBeginAsMillis() {
		return getNextHourBeginAsMillis(1);
	}

	/**
	 * 获取下N个小时开始的毫秒数
	 * 
	 * @param hours
	 *            间隔的小时数，从1开始
	 * @return
	 * @since fangxiang @ Jan 13, 2011
	 */
	public static long getNextHourBeginAsMillis(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1); // 当前时间加一 ，然后时分秒设为0，00:00:00
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取和给定日期相隔指定天数的Date对象. 大于0表示之后, 小于0表之前.
	 * 
	 * @param date
	 *            给定日期
	 * @param relativeDay
	 *            相隔天数
	 * @return
	 * @since v3.5
	 * @creator luoka @ Dec 14, 2009
	 */
	public static Date getDate(Date date, int relativeDay) {
		return getCalendar(toCalendar(date), relativeDay).getTime();
	}

	public static int month2second(int month) {
		return month * 30 * 24 * 3600;
	}

	/**
	 * 获取两个日期相差的天数，取绝对值.
	 * 
	 * @return 两个日期相差的天数。只可能为零或者正数。
	 * @see #minus(Calendar, Calendar)
	 */
	public static int betweenDays(Calendar begin, Calendar end) {
		long msBegin = begin.getTimeInMillis();
		long msEnd = end.getTimeInMillis();
		long between_days = (msEnd - msBegin) / (1000 * 3600 * 24);
		between_days = Math.abs(between_days);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个日期相差的天数，不取绝对值.
	 * 
	 * @param calBegin
	 *            开始日期
	 * @param calEnd
	 *            结束日期
	 * @return
	 * @since v3.5
	 * @creator luoka @ Dec 14, 2009
	 * @see #minus(Calendar, Calendar)
	 * @see #minusWithoutAbs(Date, Date)
	 */
	public static int minusWithoutAbs(Calendar calBegin, Calendar calEnd) {
		long msBegin = calBegin.getTimeInMillis();
		long msEnd = calEnd.getTimeInMillis();
		long between_days = (msEnd - msBegin) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个日期相差的天数，不取绝对值.
	 * 
	 * @param begin
	 * @param end
	 * @return
	 * @since v3.5
	 * @creator luoka @ Dec 14, 2009
	 */
	public static int minusWithoutAbs(Date begin, Date end) {
		long between_days = (end.getTime() - begin.getTime())
				/ (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 判断给定日期是否早于今天.
	 * 
	 * @param someDate
	 * @return
	 * @since v3.5
	 * @creator luoka @ Dec 14, 2009
	 */
	public static boolean isBeforeToday(Date someDate) {
		return minusWithoutAbs(someDate, new Date()) > 0;
	}

	/**
	 * 指定日期是否晚于今天。
	 * 
	 * @param expireDate
	 * @return 如果晚于今天返回<code>true</code>, 否则返回<code>false</code>.
	 * @since v3.5
	 * @creator luoka @ Dec 13, 2009
	 */
	public static boolean isAfterToday(Date date) {
		return minusWithoutAbs(Calendar.getInstance(), toCalendar(date)) > 0;
	}

	/**
	 * @param expireDate
	 * @return
	 * @since v3.5
	 * @creator luoka @ Dec 14, 2009
	 */
	public static String formatDate(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 智能解析时间；（依次按 {@link #prepareDefaultPatterns()} 返回的格式去解析)
	 * 
	 * @since luoka @ Dec 16, 2009
	 * @see #parseDate(String, List)
	 */
	public static Date parseDate(String date) {
		return parseDate(date, prepareDefaultPatterns());
	}

	/**
	 * 智能解析时间；（依次按格式去解析，返回第一个符合的)
	 * 
	 * @param date
	 *            字符串表示的时间
	 * @return
	 * @since luoka @ Dec 16, 2009
	 */
	public static Date parseDate(String date, List<String> patterns) {
		if (date == null) {
			return null;
		}

		for (Iterator<String> iterator = patterns.iterator(); iterator
				.hasNext();) {
			String pattern = iterator.next();
			try {
				return new SimpleDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return null;
	}

	// TODO
	private static List<String> prepareDefaultPatterns() {
		List<String> patterns = new ArrayList<String>();
		patterns.add("yyyy-MM-dd HH:mm:ss");
		patterns.add("yyyy-MM-dd");
		patterns.add("yyyy-MM");
		patterns.add("yyyy/MM/dd HH:mm:ss");
		patterns.add("yyyy/MM/dd");
		patterns.add("yyyy/MM");
		
		patterns.add("yyyy.MM.dd HH:mm:ss");
		patterns.add("yyyy.MM.dd");
		patterns.add("yyyy.MM");
		
		patterns.add("yyyy年MM月dd日 HH时mm分ss秒");
		patterns.add("yyyy年MM月dd日");
		patterns.add("yyyy年MM月");
		patterns.add("yyyy年");
		
		patterns.add("yyyyMMdd");
		patterns.add("yyyyMM");
		patterns.add("yyyy");
		return patterns;
	}
	
	/**
	 * 
	 * 是否为yyyy.MM.dd HH格式
	 * 
	 * @param date
	 *            日期
	 * @return 是否为小时格式
	 */
	public static boolean isTimeHourFormat(String date) {
		String regex = "[0-9]+\\.[0-9]+\\.[0-9]+\\s[0-9][0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 获得与当前时间的相隔时间
	 * 
	 * @param datetime
	 *            时间
	 * @return 相隔时间
	 * @since songjia @ 2013-4-7
	 */
	public static String getNowBetweenDateString(String datetime) {
		if (StringUtils.isEmpty(datetime)) {
			return "";
		}
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		Calendar ca = Calendar.getInstance();
		Date dateTemp;
		try {
			dateTemp = new SimpleDateFormat(DEFAULT_TIME_PATTERN).parse(datetime);
		} catch (ParseException e) {
			return datetime;
		}
		ca.setTime(dateTemp);
		String M = ((ca.get(Calendar.MONTH) + 1) + "").length() == 1 ? "0" + (ca.get(Calendar.MONTH) + 1) : (ca.get(Calendar.MONTH) + 1) + "";
		String d = (ca.get(Calendar.DATE) + "").length() == 1 ? "0" + ca.get(Calendar.DATE) : ca.get(Calendar.DATE) + "";
		String h = (ca.get(Calendar.HOUR_OF_DAY) + "").length() == 1 ? "0" + ca.get(Calendar.HOUR_OF_DAY) : ca.get(Calendar.HOUR_OF_DAY) + "";
		String m = (ca.get(Calendar.MINUTE) + "").length() == 1 ? "0" + ca.get(Calendar.MINUTE) : ca.get(Calendar.MINUTE) + "";
		if (calendar.get(Calendar.YEAR) != ca.get(Calendar.YEAR) || calendar.get(Calendar.MONTH) != ca.get(Calendar.MONTH)) {
			return ca.get(Calendar.YEAR) + "." + M + "." + d;
		} else if (calendar.get(Calendar.DATE) > ca.get(Calendar.DATE)) {
			int date = calendar.get(Calendar.DATE) - ca.get(Calendar.DATE);
			switch (date) {
				case 1:
					return "昨天 " + h + ":" + m;
				case 2:
					return "前天 " + h + ":" + m;
				default:
					return ca.get(Calendar.YEAR) + "." + M + "." + d;
			}

		} else if (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) > 1
				|| (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1 && calendar.get(Calendar.MINUTE) > ca.get(Calendar.MINUTE))) {
			int hour = calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY);
			return hour + "小时前";
		} else if (calendar.get(Calendar.MINUTE) > ca.get(Calendar.MINUTE) || (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1)) {
			int hm = (calendar.get(Calendar.HOUR_OF_DAY) - ca.get(Calendar.HOUR_OF_DAY) == 1) ? 60 : 0;
			int minute = calendar.get(Calendar.MINUTE) - ca.get(Calendar.MINUTE) + hm;
			return minute + "分钟前";
		} else {
			return "刚刚";
		}
	}

	/**
	 * 时间格式转换  2015-08-11 转为 2015年08月11日
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {
		
		String ret = "";
		if (StringUtils.isEmpty(date)) {
			return ret;
		}

		String fmt1 = "%s年%s月%s日";
		String fmt2 = "%s年%s月";
		String fmt3 = "%s年";
		
		date = date.replace("-", "");
		date = date.replace("/", "");
		
		if (date.length() == 8) {
			
			String y = date.substring(0, 4);
			String m = date.substring(4, 6);
			String d = date.substring(6, 8);
			
			ret = String.format(fmt1, y, removeLeftZero(m), removeLeftZero(d));
		} 
		
		if (date.length() == 6) {
			String y = date.substring(0, 4);
			String m = date.substring(4, 6);
			
			ret = String.format(fmt2, y, removeLeftZero(m));
		} 
		
		if (date.length() == 4) {
			
			String y = date.substring(0, 4);

			ret = String.format(fmt3, y);
		} 
		
		return ret;
	}

	public static String removeLeftZero(String s) {
		
		if (StringUtils.isEmpty(s)) {
			return "";
		}
		
		if (s.startsWith("0")) {
			
			return removeLeftZero(s.substring(1));
		}
		
		return s;
	}
	
	/**
	 * 获取两个日期间的所有月份集合.
	 * 
	 * @return
	 * @see #minus(Calendar, Calendar)
	 */
	public static List<String> getMonthCollection(String begin, String end) {
		//System.out.println("*************method getMonthCollection****************");
		Date date1 = parseDate(begin);
		String date1Str = new SimpleDateFormat(FMT_TRS_yMd).format(date1);
		//System.out.println("date1Str : " + date1Str);
		Date date2 = parseDate(end);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		//定义集合存放月份
		List<String> list = new ArrayList<String>();
		//添加第一个月，即开始时间
		list.add(date1Str.substring(0, date1Str.lastIndexOf(".")));
		c1.setTime(date1);
		c2.setTime(date2);
		while (c1.compareTo(c2) < 0) {
			c1.add(Calendar.MONTH, 1);// 开始日期加一个月直到等于结束日期为止
			Date ss = c1.getTime();
			String str = new SimpleDateFormat(FMT_TRS_yMd).format(ss);
			str = str.substring(0, str.lastIndexOf("."));
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 获取两个日期间的所有连续日期序列.
	 * 
	 * @return
	 * 
	 */
	public static List<String> getContinuousDateList(String begin, String end) {
		List<String> continuousDateList = new ArrayList<String>();
		Calendar startCalendar = DateUtil.getCalendar(Integer.parseInt(splitCurrentDate(begin).get("year")), Integer.parseInt(splitCurrentDate(begin).get("month")), Integer.parseInt(splitCurrentDate(begin).get("day")));
		Calendar endCalendar = DateUtil.getCalendar(Integer.parseInt(splitCurrentDate(end).get("year")), Integer.parseInt(splitCurrentDate(end).get("month")), Integer.parseInt(splitCurrentDate(end).get("day")));
		int intervalDays = DateUtil.betweenDays(startCalendar,endCalendar);
		for (int i = 0; i < intervalDays; i++) {
			Date date = DateUtil.getDate(DateUtil.parseDate(begin), i);
			String nextDate = format2String(date,FMT_TRS_yMd);
			continuousDateList.add(nextDate);
		}
		String endDate = format2String(DateUtil.parseDate(end),FMT_TRS_yMd);
		continuousDateList.add(endDate);
		return continuousDateList;
	}
	
	public static Map<String,String> splitCurrentDate(String date){
		Map<String,String> dateSplitMap = new HashMap<String, String>();
		String[] dateSplit = date.split("-");
		dateSplitMap.put("year", dateSplit[0]);
		dateSplitMap.put("month", dateSplit[1]);
		dateSplitMap.put("day", dateSplit[2]);
		//System.out.println(dateSplitMap);
		return dateSplitMap;
	}
	
	public String compleDate(String timeStr){
		timeStr = timeStr.trim();
		if (timeStr.length() == 4) {
			timeStr += "-01-01";
		} else if (timeStr.length() == 7) {
			timeStr += "-01";
		}
		return timeStr;
	}
}
