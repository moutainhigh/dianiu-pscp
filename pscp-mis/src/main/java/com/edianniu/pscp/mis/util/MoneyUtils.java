package com.edianniu.pscp.mis.util;

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
	//相加
    public static double add(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }
    //相减
    public static double sub(double d1,double d2){
        BigDecimal b1=new BigDecimal(Double.toString(d1));
        BigDecimal b2=new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }
	
	public static void main(String args[]){
		//System.out.println(MoneyUtils.format("0.005"));
		System.out.println(MoneyUtils.greaterThanOrEqual(-10000D, 0.00D));
		//System.out.println(MoneyUtils.greaterThan(0.10, 0.10));
		//System.out.println(MoneyUtils.greaterThan(0.1, 0.10));
		//System.out.println(MoneyUtils.greaterThan(0.11, 0.10));
		//System.out.println(MoneyUtils.lessThan(0.10, 0.12));
		//System.out.println(MoneyUtils.yuanToStrFen(String.valueOf(0.10D)));
		
	}
}
