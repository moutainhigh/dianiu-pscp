package com.edianniu.pscp.cs.util;

import java.math.BigDecimal;

public class MoneyUtils {
	public static String format(String money){
		double fee=Double.parseDouble(money);
		return format(fee);
	}
	public static int compare(double source,double target){
		Double s=Double.parseDouble(format(source));
		Double t=Double.parseDouble(format(target));
		return s.compareTo(t);
	}
	/**
	 * source==target
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean equals(double source,double target){
		if(compare(source,target)==0){
			return true;
		}
		return false;
	}
	/**
	 * source>target
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean greaterThan(double source,double target){
		int rs=compare(source,target);
		if(rs>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 大于等于
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean greaterThanOrEqual(double source,double target){
		int rs=compare(source,target);
		if(rs>=0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * source 小于 target
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean lessThan(double source,double target){
		int rs=compare(source,target);
		if(rs<0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 小于等于
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean lessThanOrEqual(double source,double target){
		int rs=compare(source,target);
		if(rs<=0){
			return true;
		}
		else{
			return false;
		}
	}
	public static Double formatToMoney(double money){
		return Double.parseDouble(format(money));
	}
	public static Double formatToMoney(String money){
		return Double.parseDouble(format(money));
	}
	public static String format(Double money) {
		if(money==null){
			return "0.00";
		}
        return  String.format("%.2f", money);
	}
	public static String yuanToStrFen(Double money){
		if(money==null)return "0";
		return yuanToStrFen(String.valueOf(money));
	}
	public static String yuanToStrFen(String money){
		BigDecimal bigDecimal=new BigDecimal(money);
		return bigDecimal.movePointRight(2).toString();
	}
	public static int yuanToFen(String money){
		BigDecimal bigDecimal=new BigDecimal(money);
		return bigDecimal.movePointRight(2).intValue();
	}
	public static int yuanToFen(Double money){
		BigDecimal bigDecimal=new BigDecimal(money);
		return bigDecimal.movePointRight(2).intValue();
	}
	
	/**
	 * 钱数
	 * 阿拉伯数字转换中文数字
	 * @param inputMonney
	 * @return
	 */
	public static String moneyToChinese(String inputMonney) {  
		if (!BizUtils.isNumber(inputMonney)) {
			return "钱数不合法";
		}
		Double input = Double.valueOf(inputMonney);
		boolean isNagitive = false;
		if (input < 0) {
			isNagitive = true;
			input = Math.abs(input);
		}
		if (input == 0) {
			return "人民币零元";
		}
	    int decimalDigit = 2;
	    char[] data = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};   
	    char[] units = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟','兆', '拾', '佰', '仟'};  
	    int uint = 0;  
	    long money = (long)(input * Math.pow(10, decimalDigit + 1));  
	    if (money % 10 > 4) {  
	        money = (money / 10) + 1;  
	    } else {  
	        money = money / 10;  
	    }  
	    StringBuffer sbf = new StringBuffer();  
	    while (money != 0) {  
	        sbf.insert(0, units[uint++]);
	        sbf.insert(0, data[(int) (money % 10)]);
	        money = money / 10;  
	    }  
	    if (isNagitive) {
			sbf.insert(0, "负");
		}
	    sbf.insert(0, "人民币");
	    return sbf.toString()
	    		.replaceAll("零[仟佰拾]", "零")
	    		.replaceAll("零+万", "万")
	    		.replaceAll("零+亿", "亿")
	    		.replaceAll("亿万", "亿零")
	    		.replaceAll("零+", "零")
	    		.replaceAll("零元", "元")
	    		.replaceAll("零[角分]", "");  
	}  
	
	public static void main(String args[]){
		
		String aString = "-a";
		String moneyToChinese = moneyToChinese(aString);
		System.out.println(moneyToChinese);
		
		/*System.out.println(MoneyUtils.format("0.005"));
		System.out.println(MoneyUtils.greaterThan(0.005, 0.0D));
		System.out.println(MoneyUtils.greaterThan(0.10, 0.10));
		System.out.println(MoneyUtils.greaterThan(0.1, 0.10));
		System.out.println(MoneyUtils.greaterThan(0.11, 0.10));
		System.out.println(MoneyUtils.lessThan(0.10, 0.12));
		System.out.println(MoneyUtils.yuanToStrFen(String.valueOf(0.10D)));*/
		
		
		
	}
}
