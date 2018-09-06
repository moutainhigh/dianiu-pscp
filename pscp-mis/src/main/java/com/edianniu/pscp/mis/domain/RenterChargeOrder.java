/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午6:37:12 
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;

import java.util.Date;

/**
 * RenterChargeOrder
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年3月29日 下午6:37:12 
 * @version V1.0
 */
public class RenterChargeOrder extends BaseDo{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer type;//1:预付费(默认)   2:月结算
	private String orderId;
	private Long renterId;
	private Date lastCheckDate;
	private Date thisCheckDate;
	private Double thisPeriodReading;//
	private Double lastPeriodReading;//
	
	private Double quantity;
	private Double charge;
	private Double prepaidCharge;//已支付金额
	//0：表示账单未截止(予付费时，账单生成时默认为0，只有当此次生成账单时，下一次账单是下一个月账单时，状态改为1)
	//1: 表示账单已截止(后付费，账单生成时默认为1)
	private Integer status;//
	private Integer payType; 
	//支付状态    0:未支付    1:支付确认 (客户端)   2:支付成功(服务端异步通知)  3:支付失败      4:取消支付
	private Integer payStatus;
	//支付金额
	private Double payAmount;
	//支付时间  发起支付时间
	private Date payTime;
	//支付时间   成功时间(同步)
	private Date paySyncTime;
	//支付时间   成功时间(异步)
	private Date payAsyncTime;
	//支付备注  
	private String payMemo;
	private String payOrderId;
	public String getOrderId() {
		return orderId;
	}
	public Long getRenterId() {
		return renterId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getLastCheckDate() {
		return lastCheckDate;
	}
	public Date getThisCheckDate() {
		return thisCheckDate;
	}
	public Double getThisPeriodReading() {
		return thisPeriodReading;
	}
	public Double getLastPeriodReading() {
		return lastPeriodReading;
	}
	public Date getPayTime() {
		return payTime;
	}
	public Double getQuantity() {
		return quantity;
	}
	public Double getCharge() {
		return charge;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}
	public void setThisCheckDate(Date thisCheckDate) {
		this.thisCheckDate = thisCheckDate;
	}
	public void setThisPeriodReading(Double thisPeriodReading) {
		this.thisPeriodReading = thisPeriodReading;
	}
	public void setLastPeriodReading(Double lastPeriodReading) {
		this.lastPeriodReading = lastPeriodReading;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public Double getPrepaidCharge() {
		return prepaidCharge;
	}
	public Integer getStatus() {
		return status;
	}
	public void setPrepaidCharge(Double prepaidCharge) {
		this.prepaidCharge = prepaidCharge;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public Integer getPayType() {
		return payType;
	}
	public Double getPayAmount() {
		return payAmount;
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
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
