package com.edianniu.pscp.cs.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class BizUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(BizUtils.class);
	
    public static String TOKEN_COTAIN = "123456789";
    public static String CK_COTAIN = "123456789abcdefghijklmnopqrstuvwxyz";

    public static String createToken() {
        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());
        char[] temp = new char[8];
        for (int i = 0; i < 8; i++) {
            temp[i] = TOKEN_COTAIN.charAt(rd.nextInt(TOKEN_COTAIN.length()));
        }
        return new String(temp);
    }

    public static String createCk() {
        Random rd = new Random();
        rd.setSeed(System.currentTimeMillis());
        char[] temp = new char[4];
        for (int i = 0; i < 4; i++) {
            temp[i] = CK_COTAIN.charAt(rd.nextInt(CK_COTAIN.length()));
        }
        return new String(temp);
    }

    public static boolean checkEmail(String email) {
        if (StringUtils.isNoneBlank(new CharSequence[]{email})) {
            return match(email.trim(),
                    "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        }
        return false;
    }
    
    /**
	 * 时间格式验证  HH:mm
	 */
	public static boolean cheackTimePattern(String time){
		if (StringUtils.isNoneBlank(new CharSequence[] { time })) {
			return match(time.trim(), 
					"^([0-1][0-9]|(2[0-3])):([0-5][0-9])$");
		}
		return false;
	}
    
    /**
     * 待优化
     * 证件号码验证
     * @param cardNo
     * @return
     */
    public static boolean checkCardNo(String cardNo) {
        //TODO
        if (StringUtils.isNoneBlank(cardNo)) {
            if (cardNo.length() == 15 || cardNo.length() == 18) {
                return true;
            }
        }
        return false;
    }

    /**
     * 证件号码加密
     *
     * @param cardNo
     * @return
     */
    public static String formatCardNo(String cardNo) {
        //TODO
        if (cardNo.length() <= 7) {
            return cardNo;
        }
        String start = cardNo.substring(0, 3);
        String end = cardNo.substring(cardNo.length() - 4, cardNo.length());
        StringBuffer sb = new StringBuffer(20);
        sb.append(start);
        for (int i = 0; i < cardNo.length() - 8; i++) {
            sb.append("*");
        }
        sb.append(end);
        return sb.toString();
    }

    public static boolean checkMobile(String mobile) {
        if (StringUtils.isNoneBlank(new CharSequence[]{mobile})) {
            return match(mobile.trim(),
                    "^((170)|(177)|(13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}");
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
     *
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
     *
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
     * 是否数字
     *
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
        return prefix + System.nanoTime();
    }

    /**
     * 获取手机号尾号 4位
     * 13666688420 显示8420
     *
     * @param mobile
     * @return
     */
    public static String getTailMobile(String mobile) {
        if (checkMobile(mobile)) {
            return mobile.substring(mobile.length() - 4, mobile.length());
        }
        return "";
    }

    /**
     * 格式化手机号13666688420 转变为13****8420
     *
     * @param mobile
     * @return
     */
    public static String getFormatMobile(String mobile) {
        if (checkMobile(mobile)) {
            return mobile.substring(0, 2) + "*****" + mobile.substring(mobile.length() - 4, mobile.length());
        }
        return "";
    }

    /**
     * 毫秒转换为天 小时 分钟
     *
     * @param time
     * @return
     */
    public static String toDHM(long time) {
        long day = time / (1000 * 24 * 60 * 60);
        long hour = (time - day * 1000 * 24 * 60 * 60) / (1000 * 60 * 60);
        long minutes = (time - day * 1000 * 24 * 60 * 60 - hour * 1000 * 60 * 60) / (1000 * 60);
        return day + "天" + hour + "小时" + (minutes < 0 ? 0 : minutes) + "分钟";
    }

    public static String toHMS(long time) {
        long hour = (time) / (1000 * 60 * 60);
        long minutes = (time - hour * 1000 * 60 * 60) / (1000 * 60);
        long seconds = (time - hour * 1000 * 60 * 60 - minutes * 1000 * 60) / 1000;
        String h = String.valueOf(hour).length() < 2 ? (0 + String.valueOf(hour)) : String.valueOf(hour);
        minutes = (minutes < 0 ? 0 : minutes);
        String m = String.valueOf(minutes).length() < 2 ? ("0" + String.valueOf(minutes)) : String.valueOf(minutes);
        seconds = (seconds < 0 ? 0 : seconds);
        String s = String.valueOf(seconds).length() < 2 ? ("0" + String.valueOf(seconds)) : String.valueOf(seconds);
        return h + ":" + m + ":" + s;
    }

    public static boolean isBankCardValid(String cardNo) {
        String[] ary = new String[cardNo.length()];
        int length = cardNo.length();
        for (int i = 0; i < length; i++) {
            ary[i] = cardNo.substring(length - i - 1, length - i);
        }
        int sum = 0;
        for (int j = 0; j < length; j++) {
            int num = Integer.parseInt(ary[j]);
            //如果是奇数位
            if ((j + 1) % 2 == 0) {
                num = num * 2;
                if (num > 9) {
                    num = num - 9;
                }
            }
            sum += num;
        }

        boolean flag = false;
        if (sum % 10 == 0) {
            flag = true;
        }

        return flag;

    }

    /**
     * 根据日期获取车型价格类型
     *
     * @return
     */
    public static String getPriceType() {
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

    public static boolean checkUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        boolean flag = match(url.trim(), "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
        return flag;

    }

    /**
     * 秒 to day
     * 12小时以内 半天
     * 12小时以外 1天
     *
     * @param time
     * @return
     */
    public static double toD(long time) {
        long day = time / (1000 * 24 * 60 * 60);
        long hour = (time - day * 1000 * 24 * 60 * 60) / (1000 * 60 * 60);
        if (hour == 0) {
            return day;
        }
        if (hour > 12) {
            return day + 1;
        } else {
            return day + 0.5D;
        }
    }
    
    /**
     * double装String,并保留两位小数
     * @param input
     * @return
     */
    public static String doubleToString(Double input){
    	if (null == input) {
			return "0.00";
		}
    	DecimalFormat decimalFormat=new DecimalFormat("0.00");
    	return decimalFormat.format(input);
    }
    
    /**
     * 时间节点集合
     * @param start   起始时间  HH:mm:ss
     * @param period  时间间隔 单位分钟
     * @return
     */
    public static List<String> timeListFormat(String start, Integer period, String pattern){
    	ArrayList<String> list = new ArrayList<>();
    	if (null == start) {
			start = "00:00:00";
		}
    	String end = "23:59:59";
    	try {
    		SimpleDateFormat format = new SimpleDateFormat(pattern);
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(format.parse(start));
        	start = DateUtils.format(DateUtils.parse(start, pattern), pattern);
        	list.add(start);
        	for ( ; ; ) {
    			calendar.add(Calendar.MINUTE, period);
        		Date time = calendar.getTime();
        		if (time.after(format.parse(end))) {
    				break;
    			}
        		list.add(format.format(time));
    		}
		} catch (Exception e) {
			logger.error("日期转换异常");
		}
    	return list;
    }
    
    public static void main(String[] args) throws ParseException {
    	
    	List<String> timeListFormat = timeListFormat("00:00:00", 15, DateUtils.HHmm);
    	for (String string : timeListFormat) {
			System.out.println(string);
		}
    	
    	
        /*System.out.println("isPositiveNumber:" + isPositiveNumber("0.01"));
        System.out.println("isFloat:" + isFloat("1231232.123"));
        System.out.println("isFloat:" + isFloat("-1231232.123"));
        isBankCardValid("6212261202033317133");
        isBankCardValid("6228480322625138514");
        isBankCardValid("1234567890123456789");*/
        //System.out.println("mobile:" + checkMobile("17066688429"));
        /*
        System.out.println("password:" + checkPassword("-/\\//,,,,,,,,,,,,?111111"));
		System.out.println("password:" + checkPassword("eeeee中文a"));
		System.out.println("password:" + checkPassword("EEEEEE"));
		System.out.println("password:" + checkPassword("$fffff-_="));
		System.out.println("password:" + checkPassword("111111111111111111111"));
		
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
     * 求取三相电流不平衡度
     * 算法：MAX（相电流-三相平均电流）/三相平均电流
     * 比如三相电流分别为IA=9A IB=8A IC=4A，则三相平均电流为7A，相电流-三相平均电流分别为2A 1A 3A，
     * 取差值最大那个，故MAX（相电流-三相平均电流）=3A，所以三相电流不平衡度=3/7
     * @return
     */
    public static double calculateUnbalance(double ia, double ib, double ic){
    	double result = 0.00;
    	double avg = (ia + ib + ic) / 3.0;
    	if (avg == 0.00) {
			return result;
		}
    	double max = Math.max(Math.max(Math.abs(avg - ia), Math.abs(avg - ib)), Math.abs(avg - ic));
    	result = max / avg;
    	return result;
    }
    
    /**
     * 判断日期格式  yyyy-mm  支持2011-01和 2011-1两种格式
     * 范围 1900年到2099年
     * @param date
     * @return
     */
    public static boolean checkYearAndMonth(String date){
    	if (StringUtils.isNoneBlank(new CharSequence[] {date})) {
			return match(date.trim(), 
					"^((19|20)\\d{2})-(0?[1-9]|1[0-2])");
		}
    	return false;
    }
    
    /**
     * 判断日期格式是否为yyyy-MM-dd
     * 支持“2017-01-01”（不支持“2017-8-1”）
     * 范围 1900年到2099年
     */
    public static boolean checkYmd(String time){
    	if (StringUtils.isNoneBlank(new CharSequence[] {time})) {
			time = time.trim();
			String tem = "^((((19|20)[0-9]{2})-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[0-1]))|"
				        +  "(((19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))|"
				         + "(((19|20)[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))|"
					        + "((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-02-(0[1-9]|[1-2][0-9])))$";
			if (match(time, tem)) {
				return true;
			}
		}
    	return false;
    }
    
    /**
     * 判断日期格式是否为yyyyMMdd或yyyyMM或yyyy格式
     * 支持“20170101”（不支持“201781”）     支持“201701”（不支持“20171”）
     * 范围 1900年到2099年
     */
    public static boolean checkYMD(String time){
    	if (StringUtils.isNoneBlank(new CharSequence[] {time})) {
    		time = time.trim();
			if (match(time, "^(19|20)[0-9]{2}$")) {
				return true;
			}
    		if (match(time, "^(19|20)[0-9]{2}(0[1-9]|1[0-2])$")) {
    			return true;
			}
    		String tem = "^((((19|20)[0-9]{2})(0[13578]|1[02])(0[1-9]|[12][0-9]|3[0-1]))|"
    				    +  "(((19|20)[0-9]{2})(0[469]|11)(0[1-9]|[12][0-9]|30))|"
    				     + "(((19|20)[0-9]{2})02(0[1-9]|1[0-9]|2[0-8]))|"
    				    + "((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))02(0[1-9]|[1-2][0-9])))$";
    		if (match(time, tem)) {
    			return true;
			}
		}
    	return false;
    }
}

