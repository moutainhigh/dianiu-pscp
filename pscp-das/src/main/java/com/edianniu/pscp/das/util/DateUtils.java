package com.edianniu.pscp.das.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月20日 下午2:10:09
 * @version V1.0
 */
public class DateUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(DateUtils.class);

	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 时间格式(yyyy.MM.dd)
	 */
	public final static String DATE_PATTERN_OTHER = "yyyy.MM.dd";
	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 时间格式(yyyyMMdd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN2 = "yyyyMMdd HH:mm:ss";
	/**
	 * 时间格式(HH:mm:ss)
	 */
	public final static String TIME_PATTERN = "HH:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static Date formatToParse(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return parse(df.format(date), pattern);
		}
		return null;
	}

	public static Date parse(String dateStr) {
		return parse(dateStr, DATE_PATTERN);
	}

	public static Date parse(String dateStr, String pattern) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			logger.error("字符串转日期异常：{}", e);
		}
		return null;
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
	public static String daysBetween(Date smdate, Date bdate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return String.valueOf(between_days);
		} catch (ParseException e) {
			logger.error("计算两个日期之间相差的天数异常：{}", e);
		}
		return "0";
	}

	public static void main(String[] args) {
		Date smdate = getStartDate(new Date());
		Date bdate = getEndDate(new Date());
		System.out.println(daysBetween(smdate, bdate));
		System.out.println(parse("20180308 07:30:00", DATE_TIME_PATTERN2).getTime());
		System.out.println(parse("20180308 07:30:59", DATE_TIME_PATTERN2).getTime());
	}
	 /**
     * 日期往后或者往前几天
     * 
     * @param date
     * @param days
     * @return (yyyy-MM-dd hh:mm:ss)
     * @author Allen / 2012-10-4 
     * @since CPOPEN 1.0
     */
    public static String getDateOffsetDay(Date date, int days) {
        return getDateOffsetDay(date, days, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期往后或者往前几天
     * 
     * @param date
     * @param days
     * @param format
     * @return
     */
    public static String getDateOffsetDay(Date date, int days, String format) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.add(Calendar.DAY_OF_YEAR, days);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(startDate.getTime());
    }
    /**
     * 获取DAY_OF_MONTH
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date){
    	 Calendar startDate = Calendar.getInstance();
    	 startDate.setTime(date);
    	 return startDate.get(Calendar.DAY_OF_MONTH);
    }
    public static String getDateOffsetMonth(Date date, int months, String format) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.add(Calendar.MONTH, months);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(startDate.getTime());
    }
    public static Date getDateOffsetMonth(Date date, int months) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.add(Calendar.MONTH, months);
        return startDate.getTime();
    }
    public static String getDateOffsetMinute(Date date, int minutes, String format) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.add(Calendar.MINUTE, minutes);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(startDate.getTime());
    }
    public static List<String> getDateDescList(Date date,int count,String format){
    	List<String> dateList=new ArrayList<String>();
    	for(int i=0;i<count;i++){
    		dateList.add(getDateOffsetDay(date,i,format));
    	}
		return dateList;
    	
    }
    public static List<String> getDateAscList(Date date,int start,int count,String format){
    	List<String> dateList=new ArrayList<String>();
    	for(int i=start;i<count;i++){
    		dateList.add(getDateOffsetDay(date,-i-1,format));
    	}
		return dateList;
    	
    }
	/**
	 * 字符串的日期格式的计算
	 */
	public static Integer daysBetween(String smdate, String bdate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			logger.error("字符串的日期格式的计算异常：{}", e);
		}
		return 0;
	}

	/**
	 * 获取某天的起始时间, e.g. 2005-10-01 00:00:00.000
	 *
	 * @param date
	 *            日期对象
	 * @return 该天的起始时间
	 */
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取某天的结束时间, e.g. 2005-10-01 23:59:59.999
	 *
	 * @param date
	 *            日期对象
	 * @return 该天的结束时间
	 */
	public static Date getEndDate(Date date) {

		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	public static Date addMinutes(Date date, int minutes) {
		return org.apache.commons.lang.time.DateUtils.addMinutes(date, minutes);
	}

	public static int getBettwenDays(Date startDate, Date endDate) {

		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Long startMillis = start.getTimeInMillis();
		Long endMillis = end.getTimeInMillis();
		int day = (int) ((endMillis - startMillis) / (3600 * 24 * 1000));

		return day;
	}

	public static int getBettwenMonths(Date startDate, Date endDate) {

		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Long startMillis = start.getTimeInMillis();
		Long endMillis = end.getTimeInMillis();
		int month = (int) ((endMillis - startMillis) / (3600 * 24 * 1000));

		return month / 30;
	}

}
