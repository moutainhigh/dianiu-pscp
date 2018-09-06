package com.edianniu.pscp.renter.mis.domain;


/**
 * 企业电价配置
 * @author zhoujianjian
 * @date 2018年1月3日 下午7:47:49
 */
public class PowerPriceConfig extends BaseDo{

	private static final long serialVersionUID = 1L;

	private Long id;
	// 默认为1      大工业用户
	private Integer type;
	// 电压等级  1：1-10千伏     2：20千伏     3：35千伏       4：110千伏      5：220千伏以上  (默认为0)
	private Integer voltageLevel;
	// 电度电价计费方式    1分时    0普通
	private Integer chargeMode;
	// 电度电单价,0.00表示没有
	private Double basePrice;
	// 尖电单价,0.00表示没有
	private Double apexPrice;
	// 峰电单价,0.00表示没有
	private Double peakPrice;
	// 平电单价,0.00表示没有
	private Double flatPrice;
	// 谷电单价,0.00表示没有
	private Double valleyPrice;
	// 尖时间 09:00--12:00
	private String apexTimeInterval;
	// 峰时间
	private String peakTimeInterval;
	// 平时间
	private String flatTimeInterval;
	// 谷时间
	private String valleyTimeInterval;
	// 基本电价计费方式    1变压器容量     2最大需量
	private Integer baseChargeMode;
	// 变压器容量单价
	private Double transformerCapacityPrice;
	// 最大需量单价
	private Double maxCapacityPrice;
	// 变压器容量/受电容量
	private Double transformerCapacity;
	// 最大需量/核定需量
	private Double maxCapacity;
	// 力调标准
	private Double standardAdjustRate;
	// 电费周期 每月15号
	private String chargeTimeInterval;
	// 客户企业ID
	private Long companyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getVoltageLevel() {
		return voltageLevel;
	}
	public void setVoltageLevel(Integer voltageLevel) {
		this.voltageLevel = voltageLevel;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
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
	
	public String getApexTimeInterval() {
		return apexTimeInterval;
	}
	public void setApexTimeInterval(String apexTimeInterval) {
		this.apexTimeInterval = apexTimeInterval;
	}
	public String getPeakTimeInterval() {
		return peakTimeInterval;
	}
	public void setPeakTimeInterval(String peakTimeInterval) {
		this.peakTimeInterval = peakTimeInterval;
	}
	public String getFlatTimeInterval() {
		return flatTimeInterval;
	}
	public void setFlatTimeInterval(String flatTimeInterval) {
		this.flatTimeInterval = flatTimeInterval;
	}
	public String getValleyTimeInterval() {
		return valleyTimeInterval;
	}
	public void setValleyTimeInterval(String valleyTimeInterval) {
		this.valleyTimeInterval = valleyTimeInterval;
	}
	public Integer getBaseChargeMode() {
		return baseChargeMode;
	}
	public void setBaseChargeMode(Integer baseChargeMode) {
		this.baseChargeMode = baseChargeMode;
	}
	public Double getTransformerCapacityPrice() {
		return transformerCapacityPrice;
	}
	public void setTransformerCapacityPrice(Double transformerCapacityPrice) {
		this.transformerCapacityPrice = transformerCapacityPrice;
	}
	public Double getMaxCapacityPrice() {
		return maxCapacityPrice;
	}
	public void setMaxCapacityPrice(Double maxCapacityPrice) {
		this.maxCapacityPrice = maxCapacityPrice;
	}
	public Double getTransformerCapacity() {
		return transformerCapacity;
	}
	public void setTransformerCapacity(Double transformerCapacity) {
		this.transformerCapacity = transformerCapacity;
	}
	public Double getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(Double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public Double getStandardAdjustRate() {
		return standardAdjustRate;
	}
	public void setStandardAdjustRate(Double standardAdjustRate) {
		this.standardAdjustRate = standardAdjustRate;
	}
	public String getChargeTimeInterval() {
		return chargeTimeInterval;
	}
	public void setChargeTimeInterval(String chargeTimeInterval) {
		this.chargeTimeInterval = chargeTimeInterval;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
	
	
}
