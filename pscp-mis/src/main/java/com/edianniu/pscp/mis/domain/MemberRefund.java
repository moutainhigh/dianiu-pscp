/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午7:43:36 
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员退款记录
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午7:43:36 
 * @version V1.0
 */
public class MemberRefund extends BaseDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long uid;
	private String appId;
	private String mchId;
	private Integer payType;
	private Integer orderType;
	private String tradeType;
	private String outTradeNo;
	private String tradeNo;
	private String outRefundNo;
	private String refundNo;
	private Double totalFee;
	private Double refundFee;
	private String refundFeeType;
	private String refundReason;
	private Integer refundStatus;
	private String refundMsg;
	private String refundMemo;
	private Date refundTime;
	private Integer refundCount;
	public Long getId() {
		return id;
	}
	public String getAppId() {
		return appId;
	}
	public String getMchId() {
		return mchId;
	}
	public Integer getPayType() {
		return payType;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public Double getRefundFee() {
		return refundFee;
	}
	public String getRefundFeeType() {
		return refundFeeType;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public String getRefundMsg() {
		return refundMsg;
	}
	public String getRefundMemo() {
		return refundMemo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}
	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public void setRefundMsg(String refundMsg) {
		this.refundMsg = refundMsg;
	}
	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public Integer getRefundCount() {
		return refundCount;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}
