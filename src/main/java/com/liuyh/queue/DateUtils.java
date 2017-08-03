package com.liuyh.queue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 *
 * @author zhaoyongya
 */
public class DateUtils {

	static Logger logger = Logger.getLogger(DateUtils.class);

	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_NORMAL = "yyyyMMdd";
	public static final String DATE_FORMAT_NORMAL_MIN = "yyyy-MM-dd";
	public static final String DATE_FORMAT_NORMAL_MAX = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_TIME = "HH:mm:ss";
	public static final String DATE_FORMAT_NORMAL2 = "yyyy-MM-dd";

	/**
	 * 获取当前时间
	 * 
	 * 
	 * @return
	 */
	public static Date current() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 把字符串格式化为日期 ，字符传格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * 
	 * @param formatDate
	 * @return
	 * @throws Exception
	 */
	public static Date paseDate(String formatDate) throws Exception {
		return paseDate(formatDate, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 获取 当前 时间字符传，格式 HH:mm:ss
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date) {
		return getTimeStr(date, DATE_FORMAT_TIME);
	}

	/**
	 * 获取时间字符传
	 * 
	 * 
	 * @param date
	 *            传入的时间
	 * @param formatStr
	 *            时间格式化字符串
	 * @return
	 */
	public static String getTimeStr(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	/**
	 * 根据字符串和格式化字符串转换成日期
	 * 
	 * 
	 * @param formatDate
	 *            日期字符串
	 * @param formatStr
	 *            格式化字符串
	 * @return
	 * @throws Exception
	 */
	public static Date paseDate(String formatDate, String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(formatDate);
		} catch (ParseException e) {
			logger.error(e.getMessage() + ",date:" + formatDate, e);
			throw e;
		}
		return date;
	}

	/**
	 * 
	 * 把字符传 格式化为日期的最小时间
	 * 
	 * @param formatDate
	 *            日期字符串
	 * @param formatStr
	 *            格式化字符串
	 * @return
	 * @throws Exception
	 */
	public static Date paseDateMin(String formatDate, String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(formatDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			date = calendar.getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage() + ",date:" + formatDate, e);
			throw e;
		}
		return date;
	}

	/**
	 * 
	 * 把字符传 格式化为日期的最小时间，默认日期格式是：yyyyMMdd
	 * 
	 * @param formatDate
	 *            日期字符串
	 * @return
	 * @throws Exception
	 */
	public static Date paseDateMin(String formatDate) throws Exception {
		return paseDateMin(formatDate, DATE_FORMAT_NORMAL);
	}

	/**
	 * 
	 * 把字符传 格式化为日期的最大时间
	 * 
	 * @param formatDate
	 *            日期字符串
	 * @param formatStr
	 *            格式化字符串
	 * @return
	 * @throws Exception
	 */
	public static Date paseDateMax(String formatDate, String formatStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NORMAL);
		Date date = null;
		try {
			date = format.parse(formatDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime());
			// System.out.println("---" + str);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			date = calendar.getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage() + ",date:" + formatDate, e);
			throw e;
		}
		return date;
	}

	/**
	 * 
	 * 把字符传 格式化为日期的最大时间，默认日期格式是：yyyyMMdd
	 * 
	 * @param formatDate
	 *            日期字符串
	 * @return
	 * @throws Exception
	 */
	public static Date paseDateMax(String formatDate) throws Exception {
		return paseDateMax(formatDate, DATE_FORMAT_NORMAL);
	}

	/**
	 * 把时间转换成字符传
	 * 
	 * 
	 * @param date
	 *            日期
	 * @param formatStr
	 * @return
	 */
	public static String dateToStr(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		return str;
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, DATE_FORMAT_NORMAL);
	}
	
	/**
	 * 重试间隔 当前时间与插入时间大于5分钟
	 * @param date
	 * @param interval 
	 * @return
	 */
	static public boolean checkRetryInterval(Date date, int interval) {
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
		calendar.add(Calendar.MINUTE, interval);
		//小于5分钟返回true
		return calendar.compareTo(Calendar.getInstance()) == 1;
	}
}
