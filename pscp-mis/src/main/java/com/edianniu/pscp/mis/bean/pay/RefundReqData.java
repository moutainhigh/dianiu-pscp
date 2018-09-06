/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 上午11:21:13 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 上午11:21:13 
 * @version V1.0
 */
public class RefundReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tradeType="APP";
    private String appType;
	private String appId;
	private String tradeNo;
	private String outTradeNo;
	private String outRefundNo;
	private String totalFee;
	private String refundFee;
	private String refundReason;
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
	public String getTotalFee() {
		return totalFee;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public String getRefundReason() {
		return refundReason;
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
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
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
