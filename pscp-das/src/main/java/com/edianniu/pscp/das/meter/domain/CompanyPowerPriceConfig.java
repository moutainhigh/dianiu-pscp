package com.edianniu.pscp.das.meter.domain;

import com.edianniu.pscp.das.common.BaseDo;


public class CompanyPowerPriceConfig extends BaseDo {
	private static final long serialVersionUID = 1L;
	public static final int NORMAL_CHARGE_MODE=0;//普通电价
	public static final int TIME_OF_USER_CHARGE_MODE=1;//分时电价
    private Long id;
    private Integer type;
    private Integer voltageLevel;
    private Integer chargeMode;//电度电价计费方式 0普通，1分时
    private Double basePrice;
    private Double apexPrice;
    private Double peakPrice;
    private Double flatPrice;
    private Double valleyPrice;
    private String apexTimeInterval;
    private String peakTimeInterval;
    private String flatTimeInterval;
    private String valleyTimeInterval;
    private Integer baseChargeMode;//计费方式 1变压器容量，2最大需量
    private Double standardAdjustRate;//力调标准
    private Double transformerCapacityPrice;
    private Double maxCapacityPrice;
    private Double transformerCapacity;
    private Double maxCapacity;
    private String chargeTimeInterval;
    private Long companyId;
    
	public Long getId() {
		return id;
	}
	public Integer getType() {
		return type;
	}
	public Integer getVoltageLevel() {
		return voltageLevel;
	}
	public Integer getChargeMode() {
		return chargeMode;
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
	public String getApexTimeInterval() {
		return apexTimeInterval;
	}
	public String getPeakTimeInterval() {
		return peakTimeInterval;
	}
	public String getFlatTimeInterval() {
		return flatTimeInterval;
	}
	public String getValleyTimeInterval() {
		return valleyTimeInterval;
	}
	public Integer getBaseChargeMode() {
		return baseChargeMode;
	}
	public Double getTransformerCapacityPrice() {
		return transformerCapacityPrice;
	}
	public Double getMaxCapacityPrice() {
		return maxCapacityPrice;
	}
	public Double getTransformerCapacity() {
		return transformerCapacity;
	}
	public Double getMaxCapacity() {
		return maxCapacity;
	}
	public String getChargeTimeInterval() {
		return chargeTimeInterval;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setVoltageLevel(Integer voltageLevel) {
		this.voltageLevel = voltageLevel;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
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
	public void setApexTimeInterval(String apexTimeInterval) {
		this.apexTimeInterval = apexTimeInterval;
	}
	public void setPeakTimeInterval(String peakTimeInterval) {
		this.peakTimeInterval = peakTimeInterval;
	}
	public void setFlatTimeInterval(String flatTimeInterval) {
		this.flatTimeInterval = flatTimeInterval;
	}
	public void setValleyTimeInterval(String valleyTimeInterval) {
		this.valleyTimeInterval = valleyTimeInterval;
	}
	public void setBaseChargeMode(Integer baseChargeMode) {
		this.baseChargeMode = baseChargeMode;
	}
	public void setTransformerCapacityPrice(Double transformerCapacityPrice) {
		this.transformerCapacityPrice = transformerCapacityPrice;
	}
	public void setMaxCapacityPrice(Double maxCapacityPrice) {
		this.maxCapacityPrice = maxCapacityPrice;
	}
	public void setTransformerCapacity(Double transformerCapacity) {
		this.transformerCapacity = transformerCapacity;
	}
	public void setMaxCapacity(Double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public void setChargeTimeInterval(String chargeTimeInterval) {
		this.chargeTimeInterval = chargeTimeInterval;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Double getStandardAdjustRate() {
		return standardAdjustRate;
	}
	public void setStandardAdjustRate(Double standardAdjustRate) {
		this.standardAdjustRate = standardAdjustRate;
	}
}
