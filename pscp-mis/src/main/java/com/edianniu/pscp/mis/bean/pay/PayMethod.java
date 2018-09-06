/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

import org.apache.commons.lang3.StringUtils;

/**
 * 支付方式
 * 
 * @author cyl
 *
 */
public enum PayMethod {
	APP("APP","", "APP支付"), PC("PC","", "PC网站支付"), PC_SCAN_CODE("PC_SCAN_CODE","", "PC网站扫码支付"),WAP("WAP","","手机网站支付");
	private String value;
	private String tradeType;
	private String desc;

	PayMethod(String value,String tradeType, String desc) {
		this.value = value;
		this.tradeType=tradeType;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static boolean include(String value, String exclude) {
		if (StringUtils.isBlank(value) || StringUtils.isBlank(exclude)) {
			return false;
		}
		if (value.equals(exclude)) {
			return false;
		}

		for (PayMethod payMethod : PayMethod.values()) {
			if (payMethod.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean include(String value) {

		for (PayMethod payMethod : PayMethod.values()) {
			if (payMethod.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public String getTradeType() {
		return tradeType;
	}
}
