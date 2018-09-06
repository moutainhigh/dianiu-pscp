package com.edianniu.pscp.das.meter.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumericUtils {

	public static Double format(Double input) {
		BigDecimal bd = new BigDecimal(input);
		return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	public static void main(String args[]){
		System.out.println(NumericUtils.format(0.90721123D));
		System.out.println(NumericUtils.format(0.90521123D));
	}

}
