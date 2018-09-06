package com.edianniu.pscp.renter.mis.bean.renter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 租客配置
 * @author zhoujianjian
 * @date 2018年3月30日 下午2:36:20
 */
public class SaveConfigReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 客户uid
	private Long uid;
	
	// 租客ID
	private Long renterId;
	
	// 配置id
	private Long id;
	
	// 结算方式        1:预缴费(默认)   2:月结算
	private Integer chargeMode;
	
	// 预缴费租客余额提醒临界值
	private String amountLimit;
	
	// 租客入网日期,租赁开始日期(从这一天开始计费)  yyyy-MM-dd
	private String startTime;
	
	// 租赁结束日期(计费截止到这一天)，留空表示永不结束  yyyy-MM-dd
	private String endTime;
	
	// 设备
	private List<RenterMeterInfo> meterList;
	
	// 首个账单生成日期
	private Date firstOrderTime;
	
	private String basePrice;
	
	private String apexPrice;
	
	private String peakPrice;
	
	private String flatPrice;
	
	private String valleyPrice;
	
	private Integer subChargeMode;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<RenterMeterInfo> getMeterList() {
		return meterList;
	}

	public void setMeterList(List<RenterMeterInfo> meterList) {
		this.meterList = meterList;
	}

	public String getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getFirstOrderTime() {
		return firstOrderTime;
	}

	public void setFirstOrderTime(Date firstOrderTime) {
		this.firstOrderTime = firstOrderTime;
	}

	public String getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

	public String getApexPrice() {
		return apexPrice;
	}

	public void setApexPrice(String apexPrice) {
		this.apexPrice = apexPrice;
	}

	public String getPeakPrice() {
		return peakPrice;
	}

	public void setPeakPrice(String peakPrice) {
		this.peakPrice = peakPrice;
	}

	public String getFlatPrice() {
		return flatPrice;
	}

	public void setFlatPrice(String flatPrice) {
		this.flatPrice = flatPrice;
	}

	public String getValleyPrice() {
		return valleyPrice;
	}

	public void setValleyPrice(String valleyPrice) {
		this.valleyPrice = valleyPrice;
	}

	public Integer getSubChargeMode() {
		return subChargeMode;
	}

	public void setSubChargeMode(Integer subChargeMode) {
		this.subChargeMode = subChargeMode;
	}
}
