package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午12:31:58 
 * @version V1.0
 */
public class NeedsOrder extends BaseDo {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long needsId;
	private Long companyId;
	private String orderId;
	private Double amount;
	private Integer status;	
	private Integer refundStatus;
	private Integer payType;//
	private Integer payStatus;//
	private Double payAmount;
	private Date payTime;
	private Date paySyncTime;//
	private Date payAsyncTime;//
	private String payMemo;//
	private Date accordTime;//
	private Double quotedPrice;//
	private Date quotedTime;
	private Date cooperationTime;
	public Long getId() {
		return id;
	}
	public Long getNeedsId() {
		return needsId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public String getOrderId() {
		return orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Integer getPayType() {
		return payType;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public Date getPayTime() {
		return payTime;
	}
	public Date getPaySyncTime() {
		return paySyncTime;
	}
	public Date getPayAsyncTime() {
		return payAsyncTime;
	}
	public String getPayMemo() {
		return payMemo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getAccordTime() {
		return accordTime;
	}
	public Double getQuotedPrice() {
		return quotedPrice;
	}
	public Date getQuotedTime() {
		return quotedTime;
	}
	public Date getCooperationTime() {
		return cooperationTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNeedsId(Long needsId) {
		this.needsId = needsId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public void setPaySyncTime(Date paySyncTime) {
		this.paySyncTime = paySyncTime;
	}
	public void setPayAsyncTime(Date payAsyncTime) {
		this.payAsyncTime = payAsyncTime;
	}
	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}
	public void setAccordTime(Date accordTime) {
		this.accordTime = accordTime;
	}
	public void setQuotedPrice(Double quotedPrice) {
		this.quotedPrice = quotedPrice;
	}
	public void setQuotedTime(Date quotedTime) {
		this.quotedTime = quotedTime;
	}
	public void setCooperationTime(Date cooperationTime) {
		this.cooperationTime = cooperationTime;
	}
}
