package com.edianniu.pscp.cms.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class BizUtils {
	public static String TOKEN_COTAIN = "123456789";
	public static String CK_COTAIN = "123456789abcdefghijklmnopqrstuvwxyz";

	public static String createToken() {
		Random rd = new Random();
		rd.setSeed(new Date().getTime());
		char[] temp = new char[8];
		for (int i = 0; i < 8; i++) {
			temp[i] = TOKEN_COTAIN.charAt(rd.nextInt(TOKEN_COTAIN.length()));
		}
		return new String(temp);
	}

	public static String createCk() {
		Random rd = new Random();
		rd.setSeed(new Date().getTime());
		char[] temp = new char[4];
		for (int i = 0; i < 4; i++) {
			temp[i] = CK_COTAIN.charAt(rd.nextInt(CK_COTAIN.length()));
		}
		return new String(temp);
	}

	public static boolean checkEmail(String email) {
		if (StringUtils.isNoneBlank(new CharSequence[] { email })) {
			return match(email.trim(),
					"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		}
		return false;
	}
	/**
	 * 待优化
	 * 证件号码验证
	 * @param cardNo
	 * @return
	 */
	public static boolean checkCardNo(String cardNo){
		//TODO
		if(StringUtils.isNoneBlank(cardNo)){
			if(cardNo.length()==15||cardNo.length()==18){
				return true;
			}
		}
		return false;
	}
	/**
	 * 证件号码加密
	 * @param cardNo
	 * @return
	 */
	public static String formatCardNo(String cardNo){
		//TODO
		if(cardNo.length()<=7){
			return cardNo;
		}
		String start=cardNo.substring(0, 3);
		String end=cardNo.substring(cardNo.length()-4, cardNo.length());
		StringBuffer sb=new StringBuffer(20);
		sb.append(start);
		for(int i=0;i<cardNo.length()-8;i++){
			sb.append("*");
		}
		sb.append(end);
		return sb.toString();
	}

	public static boolean checkMobile(String mobile) {
		if (StringUtils.isNoneBlank(new CharSequence[] { mobile })) {
			return match(mobile.trim(),
					"^((170)|(13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}");
		}
		return false;
	}

	/**
	 * 1)包含数字+字母+特殊字符
	 * ^[\\w\\D]{6,20}$
	 * 2)数字+字母，数字+特殊字符，字母+特殊字符，数字+字母+特殊字符组合，而且不能是纯数字，纯字母，纯特殊字符
	 * ^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$
	 * 3)数字+字母
	 * ^[\\w]{6,20}$
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		if (StringUtils.isNoneBlank(password)) {
			return match(password.trim(), "^[\\w\\D]{6,20}$");
		}
		return false;
	}

	/**
	 * 检查字符串长度
	 * @param input
	 * @param maxLength
	 * @return
	 */
	public static boolean checkLength(String input, int maxLength) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		if (input.trim().length() <= maxLength) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查字符串是否为“数字1-数字2”结构(数字为正数或0)
	 */
	public static boolean checkNum2Num(String input){
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input, 
				"^((\\d+)(\\.\\d+)?)-((\\d+)(\\.\\d+)?)$");
	}
	
	/**
	 * 检查字符串是否为“时:分-时:分”结构，24小时制
	 */
	public static boolean checkTime2Time(String input){
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input, 
				"^(([0-1][0-9]|2[0-4]):([0-5][0-9]))-(([0-1][0-9]|2[0-4]):([0-5][0-9]))$");
	}
	
	public static void main(String[] args) {
		
		String ttttt = "23:52-12:00";
		boolean checkTime2Time = checkTime2Time(ttttt);
		System.out.println(checkTime2Time);
		
		/*isBankCardValid("6212261202033317133");
		isBankCardValid("6228480322625138514");
		isBankCardValid("1234567890123456789");*/
		//System.out.println("mobile:" + checkMobile("17066688429"));
		/*
		System.out.println("password:" + checkPassword("-/\\//,,,,,,,,,,,,?111111"));
		System.out.println("password:" + checkPassword("eeeee中文a"));
		System.out.println("password:" + checkPassword("EEEEEE"));
		System.out.println("password:" + checkPassword("$fffff-_="));
		System.out.println("password:" + checkPassword("111111111111111111111"));

		System.out.println("isFloat:" + isFloat("1231232"));
		System.out.println("isFloat:" + isFloat("1231232.123"));
		System.out.println("isFloat:" + isFloat("-1231232.123"));
		System.out.println("isPositiveFloat:" + isPositiveFloat("1231232.123"));
		System.out
				.println("isPositiveFloat:" + isPositiveFloat("-1231232.123"));
		System.out.println("isNegativeFloat:" + isNegativeFloat("1231232.123"));
		System.out
				.println("isNegativeFloat:" + isNegativeFloat("-1231232.123"));
		
		System.out.println(""+getOrderId("M").length());*/
		//System.out.println(""+formatCardNo("1234612345675321"));
		/*String orderId=getOrderId("M");
		String newOrderId=orderId+"_"+WxPayUtil.getTimeStamp();
		System.out.println("orderId="+orderId+",len="+orderId.length());
		System.out.println("newOrderId="+newOrderId+",len="+newOrderId.length());*/
		/*
		String tailMobile=getTailMobile("13666688420");
		String formatMobile=getFormatMobile("13666688420");
		System.out.println("tailMobile="+tailMobile);
		System.out.println("formatMobile="+formatMobile);
		*/
		/*System.out.println(toHMS(63000L));*/
	}

	/**
	 * 是否数字
	 * @param input
	 * @return
	 */
	public static boolean isNumber(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input.trim(), "^(-?\\d+)(\\.\\d+)?$");
	}
	public static boolean isPositiveNumber(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input.trim(), "^(\\d+)(\\.\\d+)?$");
	}

	/**
	 * 是否浮点数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isFloat(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input.trim(), "^(-?\\d+)(\\.\\d+)$");
	}

	/**
	 * 是否负浮点数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNegativeFloat(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input.trim(), "^(-\\d+)(\\.\\d+)$");
	}

	/**
	 * 是否正浮点数
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isPositiveFloat(String input) {
		if (StringUtils.isBlank(input)) {
			return false;
		}
		return match(input.trim(), "^(\\d+)(\\.\\d+)$");
	}

	public static boolean match(String input, String regex) {
		Pattern pattern = Pattern.compile(regex);
		boolean tf = pattern.matcher(input).matches();
		return tf;
	}
	
    /**
     * 是否为整数
     * @param input
     * @return
     */
    public static boolean isInt(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        return match(input.trim(), "^-?\\d+$");
    }
    
    /**
     * 是否为正整数
     * @param input
     * @return
     */
    public static boolean isPositiveInt(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        return match(input.trim(), "^\\d+$");
    }
    
    /**
     * 是否为负整数
     * @param input
     * @return
     */
    public static boolean isNegativeInt(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        return match(input.trim(), "^-\\d+$");
    }

	/**
	 * 距离转换
	 * 
	 * @param distance
	 * @return
	 */
	public static String getDistance(Double distance) {
		if (distance == null)
			distance = new Double(0);
		Double dis = distance / 1000;
		if (dis.intValue() == 0) {
			return String.valueOf(distance.intValue()) + "m";
		} else {
			return String.valueOf(dis.intValue()) + "km";
		}
	}

	public static String getOrderId(String prefix) {
		return prefix+System.nanoTime();
	}
	/**
	 * 获取手机号尾号 4位
	 * 13666688420 显示8420
	 * @param mobile
	 * @return
	 */
	public static String getTailMobile(String mobile){
		if(checkMobile(mobile)){
			return mobile.substring(mobile.length()-4, mobile.length());
		}
		return "";
	}
	/**
	 * 格式化手机号13666688420 转变为13****8420 
	 * @param mobile
	 * @return
	 */
	public static String getFormatMobile(String mobile){
		if(checkMobile(mobile)){
			return mobile.substring(0, 2)+"*****"+mobile.substring(mobile.length()-4, mobile.length());
		}
		return "";
	}
	/**
	 * 毫秒转换为天 小时 分钟
	 * @param time
	 * @return
	 */
	public static String toDHM(long time){
		long day=time/(1000*24*60*60);
		long hour=(time-day*1000*24*60*60)/(1000*60*60);
		long minutes=(time-day*1000*24*60*60-hour*1000*60*60)/(1000*60);
		return day+"天"+hour+"小时"+(minutes<0?0:minutes)+"分钟";
	}
	public static String toHMS(long time){
		long hour=(time)/(1000*60*60);
		long minutes=(time-hour*1000*60*60)/(1000*60);
		long seconds=(time-hour*1000*60*60-minutes*1000*60)/1000;
		String h=String.valueOf(hour).length()<2?(0+String.valueOf(hour)):String.valueOf(hour);
		minutes=(minutes<0?0:minutes);
		String m=String.valueOf(minutes).length()<2?("0"+String.valueOf(minutes)):String.valueOf(minutes);
		seconds=(seconds<0?0:seconds);
		String s=String.valueOf(seconds).length()<2?("0"+String.valueOf(seconds)):String.valueOf(seconds);
		return h+":"+m+":"+s;
	}
	/**
	 * 秒 to day
	 * 12小时以内 半天
	 * 12小时以外 1天
	 * @param time
	 * @return
	 */
	public static double toD(long time){
		long day=time/(1000*24*60*60);
		long hour=(time-day*1000*24*60*60)/(1000*60*60);
		if(hour==0){
			return day;
		}
		if(hour>12){
			return day+1;
		}
		else{
			return day+0.5D;
		}
	}
	
	public static boolean isBankCardValid(String cardNo){
		String [] ary=new String[cardNo.length()];
		int length=cardNo.length();
		for(int i=0;i<length;i++){
			ary[i]=cardNo.substring(length - i - 1, length- i);			
		}
		int sum=0;
		for(int j=0;j<length;j++){
			int num=Integer.parseInt(ary[j]);
			//如果是奇数位
			if((j+1)%2==0){				
				num=num*2;
				if(num>9){
					num=num-9;					
				}					
			}
			sum+=num;			
		}
		
		boolean flag=false;
		if(sum%10==0){
			flag=true;
		}
		
		return flag;
		
	}
	/**
	 * 根据日期获取车型价格类型
	 * @return
	 */
	public static String getPriceType(){
		// TODO
		// 工作日 rent1
		// 周末 rent2
		// 元旦 rent3
		// 春节
		// 清明节
		// 劳动节
		// 端午节
		// 中秋节
		// 国庆节
		String priceType = "rent1";
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		// 星期一为一周的第一天 MON TUE WED THU FRI SAT SUN
		// DAY_OF_WEEK返回值 1 2 3 4 5 6 7
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 1 || week == 2 || week == 3 || week == 4) {
			priceType = "rent1";
		} else {
			priceType = "rent2";
		}
		return priceType;
	}
	
	public static boolean checkUrl(String url){
		if (StringUtils.isBlank(url)) {
			return false;
		}
		boolean flag=match(url.trim(),"http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
		return flag;
		
	}
}

