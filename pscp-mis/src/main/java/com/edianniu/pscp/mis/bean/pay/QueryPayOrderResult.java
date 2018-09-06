package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;
import java.util.Date;

import com.edianniu.pscp.mis.bean.Result;

public class QueryPayOrderResult  extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long uid;
	private String orderId;//订单ID
	private String associatedOrderIds;//批量支付关联订单ID
	private Integer orderType;
	private Double amount;
	private Integer payType;
	private String payMethod;
	private String title;
	private String body;
	private Integer status;
	private Date payTime;
	private Date paySyncTime;
	private Date payAsyncTime;
	private String memo;
	private String extendParams;
	public Long getId() {
		return id;
	}
	public Long getUid() {
		return uid;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getAssociatedOrderIds() {
		return associatedOrderIds;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public Double getAmount() {
		return amount;
	}
	public Integer getPayType() {
		return payType;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public Integer getStatus() {
		return status;
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
	public String getMemo() {
		return memo;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setAssociatedOrderIds(String associatedOrderIds) {
		this.associatedOrderIds = associatedOrderIds;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
}
