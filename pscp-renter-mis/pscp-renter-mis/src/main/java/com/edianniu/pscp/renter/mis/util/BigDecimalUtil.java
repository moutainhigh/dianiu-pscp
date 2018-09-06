package com.edianniu.pscp.renter.mis.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by tanbobo
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {

    }

    /**
     * 加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 减法运算
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }
    
    /**
     * 减法运算  并保留两位小数
     * @param v1
     * @param v2
     * @return
     */
    public static String sub(String v1, String v2){
    	boolean flag1 = BizUtils.isNumber(v1);
    	boolean flag2 = BizUtils.isNumber(v2);
    	if (!(flag1 && flag2)) {
			return "0.00";
		}
    	BigDecimal b1 = new BigDecimal(v1);
    	BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    public static void main(String[] args) {
		String a = "1222.2";
		String b = "13";
		String sub = sub(a, b);
		System.out.println(sub);
	}

    /**
     * 乘法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 除法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数
        //除不尽的情况
    }

    /**
     * string型保留两位小数
     *
     * @param value
     * @return
     */
    public static String CovertTwoDecimal(String value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(Double.parseDouble(value));
    }

    /**
     * double型保留两位小数
     */
    public static String dobCoverTwoDecimal(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }

}
