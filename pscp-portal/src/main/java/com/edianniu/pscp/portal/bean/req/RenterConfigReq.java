package com.edianniu.pscp.portal.bean.req;

import java.io.Serializable;
import java.util.List;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMeterInfo;

public class RenterConfigReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 配置id
	private Long id;
	
	// 1:预缴费(默认)   2:月结算
	private Integer chargeMode;
	
	// 预缴费租客余额提醒临界值
	private String amountLimit;

	// 租客入网日期  yyyy-MM-dd
	private String startTime;
	
	private String endTime;
	
	// 租客解除合约时间
	private Integer subChargeMode;
	
	// 一般单价
	private String basePrice;
	
	// 尖单价
	private String apexPrice;
	
	// 峰单价
	private String peakPrice;
	
	// 平单价
	private String flatPrice;
	
	// 谷单价
	private String valleyPrice;
	
	// 仪表设备
	private List<RenterMeterInfo> meterList;
	
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

	public Integer getSubChargeMode() {
		return subChargeMode;
	}

	public void setSubChargeMode(Integer subChargeMode) {
		this.subChargeMode = subChargeMode;
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

	
}
