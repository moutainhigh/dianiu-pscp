/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

import java.util.Date;

import com.edianniu.pscp.mis.bean.Result;


/**
 * @author cyl
 *
 */
public class AliPayRefundRespData extends Result{
	private static final long serialVersionUID = 1L;
	private String tradeNo;
	private String outTradeNo;
	private String buyerLogonId;
	private String fundChange;
	private String refundFee;
	private Date refundTime;
	private String  storeName;
	private String buyerUserId;
	public String getTradeNo() {
		return tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public String getBuyerLogonId() {
		return buyerLogonId;
	}
	public String getFundChange() {
		return fundChange;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public String getStoreName() {
		return storeName;
	}
	public String getBuyerUserId() {
		return buyerUserId;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	public void setFundChange(String fundChange) {
		this.fundChange = fundChange;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
    
}
