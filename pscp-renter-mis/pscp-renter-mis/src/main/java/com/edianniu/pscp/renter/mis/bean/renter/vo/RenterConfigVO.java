package com.edianniu.pscp.renter.mis.bean.renter.vo;

import java.io.Serializable;
import java.util.List;
import com.edianniu.pscp.renter.mis.bean.renter.RenterMeterInfo;

public class RenterConfigVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 配置id
	private Long id;
	// 1:预缴费(默认)   2:月结算
	private Integer chargeMode;
	// 租客入网日期  yyyy-MM-dd
	private String startTime;
	// 租赁结束时间yyyy-MM-dd
	private String endTime;
	// 设备
	private List<RenterMeterInfo> meterList;
	
	/**统一单价**/
	private Double basePrice=0.00D;// 统一单价，非分时
	/**尖峰平谷电价**/
	private Double apexPrice=0.00D;// 尖单价，
	private Double peakPrice=0.00D;// 峰单价
	private Double flatPrice=0.00D;// 平单价
	private Double valleyPrice=0.00D;// 谷单价
	// 预缴费租客余额提醒临界值
	private Double amountLimit;
	

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getApexPrice() {
		return apexPrice;
	}

	public void setApexPrice(Double apexPrice) {
		this.apexPrice = apexPrice;
	}

	public Double getPeakPrice() {
		return peakPrice;
	}

	public void setPeakPrice(Double peakPrice) {
		this.peakPrice = peakPrice;
	}

	public Double getFlatPrice() {
		return flatPrice;
	}

	public void setFlatPrice(Double flatPrice) {
		this.flatPrice = flatPrice;
	}

	public Double getValleyPrice() {
		return valleyPrice;
	}

	public void setValleyPrice(Double valleyPrice) {
		this.valleyPrice = valleyPrice;
	}

	public Double getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Double amountLimit) {
		this.amountLimit = amountLimit;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	

}
