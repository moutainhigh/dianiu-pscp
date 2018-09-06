/**
 * 
 */
package com.edianniu.pscp.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author elliot.chen
 * 
 */
public class DateUtils {

    /**
     * 日期转字符串
     *             
     * @param date
     * @return (yyyy-MM-dd hh:mm:ss)
     * @author Allen / 2012-10-4 
     * @since CPOPEN 1.0
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转字符串
     * 
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);

    }
    
    /**
     * 字符串转日期
     * 
     * @param input
     * @return (yyyy-MM-dd hh:mm:ss)
     * @author Allen / 2012-10-4 
     * @since CPOPEN 1.0
     */
    public static Date parse(String input) {
        return parse(input, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串转日期
     * 
     * @param input
     * @param format
     * @return
     */
    public static Date parse(String input, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(input);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

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

    public static void main(String args[]) {
     /*   String result = DateUtils.format(new Date(), "yyyy-MM-dd");
        System.out.println("result=" + result);
        Date date = DateUtils.parse(result + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        // System.out.println("date="+date.toGMTString());
        String result2 = DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println("result=" + result2);
        String result3 = DateUtils.getDateOffsetDay(new Date(), -1, "yyyy-MM-dd HH:mm:ss");
        System.out.println("result=" + result3);
        List<String> dateDescList=DateUtils.getDateDescList(date, 31, "yyyy-MM-dd");
        System.out.println("dateDescList=" + dateDescList);
        List<String> dateAscList=DateUtils.getDateAscList(date,1, 31, "yyyy-MM-dd");
        System.out.println("dateAscList=" + dateAscList);*/
        
        /*String result4 = DateUtils.getDateOffsetMonth(new Date(), -12, "yyyy-MM");
        System.out.println("result4=" + result4);
        String startDate="2012-01";
        String endDate="2013-04";
        String format="yyyy-MM";
        List<String> dateList=new ArrayList<String>();
		dateList.add(startDate);
		if(format.equals("yyyy-MM")){
			String result="";
			for(int i=1;!result.equals(endDate);i++){
				result=DateUtils.getDateOffsetMonth(DateUtils.parse(startDate, format), i, format);
				dateList.add(result);
			}
		}
		dateList.add(endDate);
		System.out.println("dateList="+dateList.size());*/
    	String result4 = DateUtils.getDateOffsetMonth(DateUtils.parse("2017-03-32 17:32:22", "yyyy-MM-dd HH:mm:ss"), -1, "yyyy-MM");
    	System.out.println("result4="+result4);
    	
    	String result5=DateUtils.getDateOffsetMinute(DateUtils.parse("2017-03-32 23:50:22", "yyyy-MM-dd HH:mm:ss"),15, "HH:mm");
    	System.out.println("result5="+result5);
    	
    }
    
    
    public static int getBettwenDays(Date startDate,Date endDate){
    	
    	Calendar start=Calendar.getInstance();
    	start.setTime(startDate);
    	Calendar end=Calendar.getInstance();
    	end.setTime(endDate);
    	Long startMillis=start.getTimeInMillis();
    	Long endMillis=end.getTimeInMillis();
    	int day=(int) ((endMillis-startMillis)/(3600 * 24 * 1000));
    	
    	return day;
    }
    
    
   public static int getBettwenMonths(Date startDate,Date endDate){
    	
    	Calendar start=Calendar.getInstance();
    	start.setTime(startDate);
    	Calendar end=Calendar.getInstance();
    	end.setTime(endDate);
    	Long startMillis=start.getTimeInMillis();
    	Long endMillis=end.getTimeInMillis();
    	int month=(int) ((endMillis-startMillis)/(3600 * 24 * 1000 ));
    	
    	return month/30;
    }
}
