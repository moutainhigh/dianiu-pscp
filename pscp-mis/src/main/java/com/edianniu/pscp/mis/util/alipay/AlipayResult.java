/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月25日 下午4:00:11 
 * @version V1.0
 */
package com.edianniu.pscp.mis.util.alipay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月25日 下午4:00:11
 * @version V1.0
 */
public class AlipayResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sign;
	private String sign_type;
	private AlipayTradeAppPayResponse alipay_trade_app_pay_response;

	public String getSign() {
		return sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public AlipayTradeAppPayResponse getAlipay_trade_app_pay_response() {
		return alipay_trade_app_pay_response;
	}

	public void setAlipay_trade_app_pay_response(
			AlipayTradeAppPayResponse alipay_trade_app_pay_response) {
		this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	

	
}
