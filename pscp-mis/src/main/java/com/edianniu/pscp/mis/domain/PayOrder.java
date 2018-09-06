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
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月17日 下午7:43:36 
 * @version V1.0
 */
public class PayOrder extends BaseDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long uid;
	private String orderId;//订单ID
	private String associatedOrderIds;//批量支付关联订单ID
	private Integer orderType;
	private Double amount;
	private Integer payType;
	private String payMethod;
	private String payChannel;//支付渠道
	private String title;
	private String body;
	private Integer status;
	private Date payTime;
	private Date paySyncTime;
	private Date payAsyncTime;
	private String memo;
	private Integer needInvoice=0;//发票状态
	private String invoiceOrderId;//发票编号
	private String extendParams;
	// 第三方支付交易号或者订单 (wx支付订单号 / ali支付宝交易号/余额支付/银联支付)
	private String tppTransactionId;
	// 第三方支付卖家ID (wx支付订单号 / ali支付宝交易号/余额支付/银联支付)
	private String tppSellerId;
	// 第三方支付买家ID (wx支付订单号 / ali支付宝交易号/余额支付/银联支付)
	private String tppBuyerId;
	// 第三方支付交易号或者订单 (wx支付订单号 / ali支付宝交易号/余额支付/银联支付)
	
	private Integer chargeMode; // 电费缴费类型  1:预缴费(默认)   2:月结算
	
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public String getOrderId() {
		return orderId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
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
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Integer getStatus() {
		return status;
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
	public void setStatus(Integer status) {
		this.status = status;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAssociatedOrderIds() {
		return associatedOrderIds;
	}
	public void setAssociatedOrderIds(String associatedOrderIds) {
		this.associatedOrderIds = associatedOrderIds;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getTppTransactionId() {
		return tppTransactionId;
	}
	public String getTppSellerId() {
		return tppSellerId;
	}
	public String getTppBuyerId() {
		return tppBuyerId;
	}
	public void setTppTransactionId(String tppTransactionId) {
		this.tppTransactionId = tppTransactionId;
	}
	public void setTppSellerId(String tppSellerId) {
		this.tppSellerId = tppSellerId;
	}
	public void setTppBuyerId(String tppBuyerId) {
		this.tppBuyerId = tppBuyerId;
	}
	public Integer getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}
	public String getInvoiceOrderId() {
		return invoiceOrderId;
	}
	public void setInvoiceOrderId(String invoiceOrderId) {
		this.invoiceOrderId = invoiceOrderId;
	}

}
