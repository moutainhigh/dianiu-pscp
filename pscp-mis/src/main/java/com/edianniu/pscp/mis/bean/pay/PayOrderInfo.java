/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月30日 下午4:06:59 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月30日 下午4:06:59 
 * @version V1.0
 */
public class PayOrderInfo implements Serializable{
	private static final long serialVersionUID = 1L; 
	private Long id;
	private Long uid;
	private String orderId;//订单ID
	private String associatedOrderIds;//批量支付关联订单ID
	private Integer orderType;
	private String amount;
	private Integer payType;
	private String payMethod;
	private String title;
	private String body;
	private Integer status;
	private String payTime;
	private String paySyncTime;
	private String payAsyncTime;
	private String memo;
	private String extendParams;
	
	private Integer chargeMode; //缴费类型 1:预缴费(默认)   2:月结算
	private Integer needInvoice;  //未申请:0， 已申请:1， 已开票:2
	
	
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public Integer getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}
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
	public String getAmount() {
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
	public String getPayTime() {
		return payTime;
	}
	public String getPaySyncTime() {
		return paySyncTime;
	}
	public String getPayAsyncTime() {
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
	public void setAmount(String amount) {
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
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public void setPaySyncTime(String paySyncTime) {
		this.paySyncTime = paySyncTime;
	}
	public void setPayAsyncTime(String payAsyncTime) {
		this.payAsyncTime = payAsyncTime;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
}
