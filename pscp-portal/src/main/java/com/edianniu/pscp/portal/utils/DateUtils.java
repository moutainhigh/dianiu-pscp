package com.edianniu.pscp.portal.utils;

import org.apache.commons.lang3.StringUtils;

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
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

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
            e.printStackTrace();
        }
        return null;
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
