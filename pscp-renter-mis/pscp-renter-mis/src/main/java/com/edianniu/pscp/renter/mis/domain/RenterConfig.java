package com.edianniu.pscp.renter.mis.domain;

import java.util.Date;

/**
 * 租客配置信息
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:40:14
 */
public class RenterConfig extends BaseDo{
	public static final int NORMAL_CHARGE_MODE=0;//普通电价
	public static final int TIME_OF_USER_CHARGE_MODE=1;//分时电价
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long renterId;
	
	// 1:预缴费(默认)   2:月结算
	private Integer chargeMode = 1;
	// 0 统一单价，1分时
	private Integer subChargeMode=0;
	/**统一单价**/
	private Double basePrice=0.00D;// 统一单价，非分时
	/**尖峰平谷电价**/
	private Double apexPrice=0.00D;// 尖单价，
	private Double peakPrice=0.00D;// 峰单价
	private Double flatPrice=0.00D;// 平单价
	private Double valleyPrice=0.00D;// 谷单价
	
	// 预缴费租客余额提醒临界值
	private Double amountLimit;
	
	// 入网时间
	private Date startTime;
	// 离网时间
	private Date endTime;
	//上一次账单生成时间
	private Date prevBillCreateTime;//
	//下一次账单生成时间
	private Date nextBillCreateTime;//
	
	private Integer billTaskStatus;//0未执行，1执行中
	
	private Date billTaskExcuteTime;//执行时间
	
	// 闸门状态（ 0：开闸   1：合闸）
	private Integer switchStatus = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Double getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Double amountLimit) {
		this.amountLimit = amountLimit;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Date getEndTime() {
		return endTime;
	}
	public Date getPrevBillCreateTime() {
		return prevBillCreateTime;
	}

	public Date getNextBillCreateTime() {
		return nextBillCreateTime;
	}

	public Integer getBillTaskStatus() {
		return billTaskStatus;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setPrevBillCreateTime(Date prevBillCreateTime) {
		this.prevBillCreateTime = prevBillCreateTime;
	}

	public void setNextBillCreateTime(Date nextBillCreateTime) {
		this.nextBillCreateTime = nextBillCreateTime;
	}

	public void setBillTaskStatus(Integer billTaskStatus) {
		this.billTaskStatus = billTaskStatus;
	}

	public Date getBillTaskExcuteTime() {
		return billTaskExcuteTime;
	}

	public void setBillTaskExcuteTime(Date billTaskExcuteTime) {
		this.billTaskExcuteTime = billTaskExcuteTime;
	}

	public Integer getSubChargeMode() {
		return subChargeMode;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public Double getApexPrice() {
		return apexPrice;
	}

	public Double getPeakPrice() {
		return peakPrice;
	}

	public Double getFlatPrice() {
		return flatPrice;
	}

	public Double getValleyPrice() {
		return valleyPrice;
	}

	public void setSubChargeMode(Integer subChargeMode) {
		this.subChargeMode = subChargeMode;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public void setApexPrice(Double apexPrice) {
		this.apexPrice = apexPrice;
	}

	public void setPeakPrice(Double peakPrice) {
		this.peakPrice = peakPrice;
	}

	public void setFlatPrice(Double flatPrice) {
		this.flatPrice = flatPrice;
	}

	public void setValleyPrice(Double valleyPrice) {
		this.valleyPrice = valleyPrice;
	}
}
