package com.edianniu.pscp.cs.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.TimeUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式(HH:mm:ss)
     */
    public final static String HHmmss = "HH:mm:ss";
    
    public final static String HHmm = "HH:mm";
    
    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 时间戳
     * @param format
     * @return
     */
    public static String timeStampFormatStr(Long timeStamp, String format) {
        if (timeStamp == null || timeStamp == 0L) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DATE_TIME_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timeStamp));
    }
    
    /**
     * 字符串转时间戳  
     * @param date    要转换的时间
     * @param pattern 时间格式
     * @return
     */
    public static Long strFormatTimeStamp(String time, String pattern){
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	long result = 0L;
		try {
			Date parse = sdf.parse(time);
			result = parse.getTime();
		} catch (ParseException e) {
			return result;
		}
    	return result;
    }
    
    /**
     * 根据年、月获取对应的月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, month - 1);
    	calendar.set(Calendar.DATE, 1);
    	calendar.roll(Calendar.DATE, -1);
    	int maxDate = calendar.get(Calendar.DATE);
    	return maxDate;
    }
    
    
    /**
     * 求某一时刻间隔指定时间后的时刻
     * @param time      起始时间
     * @param period    时间间隔   毫秒值
     * @param pattern   起始时间的格式
     * @return
     */
    public static String strAddTimeStamp(String ininTime, Long period, String pattern){
    	if (ininTime == null || period == null || pattern == null) {
			return "";
		}
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	String result = "";
    	try {
			Date parse = sdf.parse(ininTime);
			result = timeStampFormatStr(parse.getTime() + period, pattern);
		} catch (ParseException e) {
			return "";
		}
    	return result;
    }
    
    /**
     * 求某一时刻间隔指定时间后的时刻
     * @param time      起始时间
     * @param period    时间间隔   分钟
     * @param pattern   起始时间的格式
     * @return
     */
    public static String strAddMinutes(String ininTime, Integer period, String pattern){
    	if (ininTime == null || period == null || pattern == null) {
			return "";
		}
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	String result = "";
    	try {
			Date parse = sdf.parse(ininTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.MINUTE, period);
			result = format(calendar.getTime(), pattern);
		} catch (ParseException e) {
			return "";
		}
    	return result;
    }
    
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
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            logger.error("计算两个日期之间相差的天数异常：{}", e);
        }
        return 0;
    }
    
    /**
     * 计算两个日期之间相差的小时数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差小时数
     * @throws ParseException
     */
    public static Integer hoursBetween(Date smdate, Date bdate){
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_hours = (time2 - time1) / (1000 * 3600);

            return Integer.parseInt(String.valueOf(between_hours));
		} catch (Exception e) {
			logger.error("计算两个日期之间相差的小时数异常：{}", e);
		}
    	return 0;
    }
    
    /**
     * 计算两个日期之间相差的时间间隔，xx时xx分xx秒
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     */
    public static String hmsBetween(Date smdate, Date bdate){
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(smdate);
			long time1 = calendar.getTimeInMillis();
			calendar.setTime(bdate);
			long time2 = calendar.getTimeInMillis();
			int bt = Math.abs((int)(time2 - time1));
			int hours = 0;
			int minutes = 0;
			if (bt >= 60 * 60 * 1000) {
				hours = bt / (60 * 60 * 1000);  
				bt = bt - hours * (60 * 60 * 1000);
			}
			if (bt >= 60 * 1000) {
				minutes = bt / (60 * 1000);
				bt = bt - minutes * (60 * 1000);
			}
			int seconds = bt / 1000;
			return hours + "时" + minutes + "分" + seconds + "秒";
		} catch (Exception e) {
			logger.error("计算两个时间之间的间隔异常：{}", e);
		}
    	return "";
    }
    
    public static void main(String[] args) throws ParseException {
    	//1517414400000  1517979839767
    	String timeStampFormatStr = timeStampFormatStr(1520422200000L, DATE_TIME_PATTERN);
    	System.out.println(timeStampFormatStr);
    	
    	Date date1 = parse("2018-03-08 12:12:12", DATE_TIME_PATTERN);
    	Date date2 = parse("2018-03-05 11:21:43", DATE_TIME_PATTERN);
    	String hmsBetween = hmsBetween(date1, date2);
    	System.out.println(hmsBetween);
    }
    
    /**
     * 获取昨天的日期
     * @param today      今天日期    格式yyyyMMdd
     * @return yesterday 昨天日期    格式yyyyMMdd
     */
    public static String getYesterdayDate(String today){
    	String yesterday = null;
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    		Date todayDate = sdf.parse(today);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(todayDate);
    		cal.add(Calendar.DATE, -1);
    		Date yesterdayDate = cal.getTime();
    		yesterday = format(yesterdayDate, "yyyyMMdd");
		} catch (Exception e) {
			logger.error("计算昨天日期异常：{}", e);
		}
    	return yesterday;
    }
    
    /**
     * 获取上月月份
     * @param  thisMonth  yyyyMM
     * @return lastMonth  yyyyMM
     */
    public static String getLastMonth(String thisMonth){
    	String lastMonth = null;
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    		Date date = sdf.parse(thisMonth);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		cal.add(Calendar.MONTH, -1);
    		lastMonth = format(cal.getTime(), "yyyyMM");
		} catch (Exception e) {
			logger.error("计算上月月份异常：{}", e);
		}
    	return lastMonth;
    }

    
    
    
    /**
     * 比较两个时间大小，忽略时分秒
     * @param d1
     * @param d2
     * @return
     */
    public static boolean beforeSmallThanAfter(Date d1, Date d2){
    	if (d1 == null || d1 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
    	d1 = formatToParse(d1, DATE_PATTERN);
    	d2 = formatToParse(d2, DATE_PATTERN);
    	return d1.before(d2);
    }
    
    /**
     * 判断两个日期是否相同，忽略时分秒
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2){
    	return org.apache.commons.lang.time.DateUtils.isSameDay(d1, d2);
    }
    
    /**
     * 计算给定日期加给定间隔后的日期
     * @param date
     * @param times
     * @param timeUnit
     * @return
     */
    public static Date dateAddTime(Date date, Integer times, Integer timeUnit){
    	if (null == date || null == times || null == timeUnit) {
			return null;
		}
    	Date result = null;
    	if (TimeUnit.DAY.getValue().equals(timeUnit)) {
    		result = dateAddDays(date, times);
		}
    	if (TimeUnit.MONTH.getValue().equals(timeUnit)) {
			result = dateAddMonths(date, times);
		}
    	if (TimeUnit.YEAR.getValue().equals(timeUnit)) {
    		result = dateAddYears(date, times);
		}
    	return result;
    }

    /**
     * 计算给定日期加给定天后的日期
     * @param date 要运算的日期
     * @param days 要加的天数
     * @return     运算后的日期
     */
    public static Date dateAddDays(Date date, Integer days){
    	if (null == date || null == days) {
			return null;
		}
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date); 
		calendar.add(Calendar.DAY_OF_MONTH, days);
		
    	return calendar.getTime();
    }
    
    /**
     * 计算给定日期加给定月后的日期
     * @param date
     * @param months
     * @return
     */
    public static Date dateAddMonths(Date date, Integer months){
    	if (null == date || null == months) {
			return null;
		}
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, months);
    	
    	return calendar.getTime();
    }
    
    /**
     * 计算给定日期加给定年后的日期
     * @param date
     * @param years
     * @return
     */
    public static Date dateAddYears(Date date, Integer years){
    	if (null == date || null == years) {
			return null;
		}
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.YEAR, years);
    	
    	return calendar.getTime();
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
     * @param date 日期对象
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
     * @param date 日期对象
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
    
    

}
