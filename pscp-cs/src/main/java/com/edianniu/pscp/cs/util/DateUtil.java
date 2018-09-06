package com.edianniu.pscp.cs.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {

    public static final long MILISECONSE_2_SECONDS = 1000L;
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    private static final String POINT_FORMAT = "yyyy.MM.dd.HH.mm.ss";
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String YYYY_MM_DD_HH_MM_SS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String YYYY_MM_FORMAT = "yyyy-MM";
    public static final String DEFAULE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * Get now date, fomate like 'yyyy.MM.dd.HH.mm.ss'
     *
     * @return
     */
    public static String getPointFormatDate() {
        return getFormatDate(POINT_FORMAT);
    }

    public static String getFormatDate(String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(new Date());
    }

    public static String getFormatDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String getFormatDate(Long time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(new Date(time));
    }

    public static Date getHour(int hour) {
        return new Date(hour * 60 * 60 * 1000);
    }

    public static Date getMin(int minute) {
        return new Date(minute * 60 * 1000);
    }

    public static Date getSeconds(int seconds) {
        return new Date(seconds * 1000);
    }

    /**
     * @param clientHttpFormatTime      : Http Header 中定义的时间类型, 例如: Thu, 17 Jan 2013 06:56:03 GMT
     * @param serverTimeInMilliseconds: milliseconds since January 1, 1970, 00:00:00 GMT
     * @return : 是否
     * @throws ParseException
     * @see Netty Link :HttpStaticFileServerHandler
     */
    public static boolean ifModifiedSince(final String clientHttpFormatTime, final long serverTimeInMilliseconds) {
        if (StringUtils.isBlank(clientHttpFormatTime) || serverTimeInMilliseconds <= 0) {
            return false;
        }

        try {
            final long apkLastModifiedMilliSeconds = new Date(serverTimeInMilliseconds).getTime();
            //获取客户端时间参数的毫秒数
            final SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
            final Date ifModifiedSinceDate = dateFormatter.parse(clientHttpFormatTime);
            //只需要比较秒数, 因为下发的时间精确到秒数
            final long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / MILISECONSE_2_SECONDS;
            final long apkLastModifiedInSeconds = apkLastModifiedMilliSeconds / MILISECONSE_2_SECONDS;
            //避免时区的问题,精确比较
            return ifModifiedSinceDateSeconds == apkLastModifiedInSeconds;
        } catch (Exception e) {
            logger.error("parse exception in DateUtils, case by : {}", e.getMessage());
        }
        return false;
    }


    /**
     * @param srcTime : 需要转化的时间, milliseconds since January 1, 1970, 00:00:00 GMT
     * @return : Http Header 定义的时间类型
     */
    public static String convertDBTime2HttpTime(final long srcTime) {
        if (srcTime <= 0) {
            throw new IllegalArgumentException("参数 src<=0, src=" + srcTime);
        }

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        dateFormatter.format(srcTime);
        return dateFormatter.format(srcTime);
    }

    public static void main(String args[]) {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * String 转date
     *
     * @param time
     * @param format
     * @return
     */
    public static Date formString(String time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(time);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormatDate(Date createTime) {
        return getFormatDate(createTime, DEFAULE_DATE_TIME_FORMAT);
    }

    public static Date formString(String time) {
        return formString(time, DEFAULE_DATE_TIME_FORMAT);
    }

}
