package com.edianniu.pscp.renter.mis.bean.renter.vo;

import java.io.Serializable;

/**
 * 租客账单
 * @author zhoujianjian
 * @date 2018年4月8日 下午5:49:03
 */
public class OrderVO implements Serializable{
	private static final long serialVersionUID = 1L;
	// 账单ID
	private Long id;
	// 账单编号
	private String orderId;
	// 上期抄表日期
	private String lastCheckDate;
	// 本期抄表日期
	private String thisCheckDate;
	// 用电量（两位小数）
	private String quantity;
	// 金额（两位小数）
	private String charge;
	// 缴费日期 yyyy-MM-dd
	private String payTime;
	// 缴费状态：0:未缴费 (月结)、未结清(预付)     1:已缴费(月结)、已结清(预付)
	private Integer payStatus=0;
	// 电费周期 yyyy-MM-dd - yyyy-MM-dd
	private String period;
	// 未结金额（预付租客）
	private String noPayCount;
	// 缴费方式：1:预付   2:月结
	private Integer chargeMode; 
	// 付款单号，未付款为空
	private String payOrderId;
	
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public String getNoPayCount() {
		return noPayCount;
	}
	public void setNoPayCount(String noPayCount) {
		this.noPayCount = noPayCount;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
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
	public String getLastCheckDate() {
		return lastCheckDate;
	}
	public void setLastCheckDate(String lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}
	public String getThisCheckDate() {
		return thisCheckDate;
	}
	public void setThisCheckDate(String thisCheckDate) {
		this.thisCheckDate = thisCheckDate;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
}
