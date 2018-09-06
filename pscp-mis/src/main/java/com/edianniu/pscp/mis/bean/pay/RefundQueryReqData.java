/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 下午2:11:07 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 下午2:11:07 
 * @version V1.0
 */
public class RefundQueryReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tradeType="APP";
	
    private String appType;
	 //AppID
    private String appId;
    /*
     * 以下4个参数 四选一
     */
    // 支付订单号
    private String tradeNo;
    // 商户订单号
    private String outTradeNo;
    // 商户退款单号
    private String outRefundNo;
    // 支付退款单号
    private String refundNo;
	public String getAppId() {
		return appId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getTradeType() {
		return tradeType;
	}
	public String getAppType() {
		return appType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
}
