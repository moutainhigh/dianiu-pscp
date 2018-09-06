package com.edianniu.pscp.renter.mis.domain;

import java.util.Date;

public class RenterChargeOrder extends BaseDo{

	private static final long serialVersionUID = 1L;
	public static final int SUCCESS=1;//账单已截止
	public static final int GENERATION=0;//账单未截止
	private Long id;
	// 账单编号
	private String orderId;
	// 	支付订单编号
	private String payOrderId;
	// 租客ID
	private Long renterId;
	// 业主公司ID（指的是客户）
	private Long companyId;
	// 上期抄表日期
	private Date lastCheckDate;
	// 本期抄表日期
	private Date thisCheckDate;
	// 本期示数
	private Double thisPeriodReading;
	// 上期示数
	private Double lastPeriodReading;
	// 支付发起时间
	private Date payTime;
	// 支付同步通知时间
	private Date paySyncTime;
	// 支付异步通知时间
	private Date payasyncTime;
    // 支付类型   0余额，1支付宝，2微信支付，3银联支付
	private Integer payType;
	// 支付方式    APP:APP支付，    PC:PC支付
	private String payMethod;
	// 当前用量
	private Double quantity;
	// 电费金额
	private Double charge;
	//预付电费金额
	private Double prepaidCharge;
	/**
	 * 0：表示账单未截止(予付费时，账单生成时默认为0，只有当此次生成账单时，下一次账单是下一个月账单时，状态改为1)
	 * 1: 表示账单已截止(月结算，账单生成时默认为1)
	 */
	private Integer status;
	// 缴费状态    0:未支付    1:支付确认中(客户端)  2:支付成功(服务端异步通知)  3:支付失败   4:用户取消
	private Integer payStatus;
	// 1:预缴费(默认)   2:月结算
	private Integer chargeMode;
	
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public Long getRenterId() {
		return renterId;
	}
	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}
	public Date getLastCheckDate() {
		return lastCheckDate;
	}
	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}
	public Date getThisCheckDate() {
		return thisCheckDate;
	}
	public void setThisCheckDate(Date thisCheckDate) {
		this.thisCheckDate = thisCheckDate;
	}
	public Double getThisPeriodReading() {
		return thisPeriodReading;
	}
	public void setThisPeriodReading(Double thisPeriodReading) {
		this.thisPeriodReading = thisPeriodReading;
	}
	public Double getLastPeriodReading() {
		return lastPeriodReading;
	}
	public void setLastPeriodReading(Double lastPeriodReading) {
		this.lastPeriodReading = lastPeriodReading;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getPaySyncTime() {
		return paySyncTime;
	}
	public void setPaySyncTime(Date paySyncTime) {
		this.paySyncTime = paySyncTime;
	}
	public Date getPayasyncTime() {
		return payasyncTime;
	}
	public void setPayasyncTime(Date payasyncTime) {
		this.payasyncTime = payasyncTime;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
