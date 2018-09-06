/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 月电费
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class MonthLogVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String date;// 日期(yyyyMM)
	private String cycle;// 电费周期
	private Double totalCharge;// 总计电费
	private Double basicCharge;// 基本电费
	private Double factorCharge;// 力调电费
	private Double apexCharge;// 尖电费，
	private Double peakCharge;// 峰电费
	private Double flatCharge;// 平电费
	private Double valleyCharge;// 谷电费
	private Integer baseChargeMode;//基本电费计费方式
	private Integer chargeMode;//电度电费计费方式
	private Double basicPrice;// 统一单价
	private Double apexPrice;// 尖单价，
	private Double peakPrice;// 峰单价
	private Double flatPrice;// 平单价
	private Double valleyPrice;// 谷单价
	/** 有功电量kwh **/
	private Double total;// 用电量
	private Double thisMonthTotal;// 本期抄表数
	private Double lastMonthTotal;// 上期抄表数
	private Double apex;// 尖电量
	private Double thisMonthApex;// 本期抄表数
	private Double lastMonthApex;// 上期抄表数
	private Double peak;// 峰电量
	private Double thisMonthPeek;// 本期抄表数
	private Double lastMonthPeek;// 上期抄表数
	private Double flat;// 平电量
	private Double thisMonthFlat;// 本期抄表数
	private Double lastMonthFlat;// 上期抄表数
	private Double valley;// 谷电量
	private Double thisMonthValley;// 本期抄表数
	private Double lastMonthValley;// 上期抄表数
	/** 无功电量kvar **/
	private Double reactiveTotal;
	private Double thisMonthReativeTotal;// 本期抄表数
	private Double lastMonthReativeTotal;// 上期抄表数
	/**变压器相关**/
	private Double capacityPrice;//变压器容量单价
	private Double maxCapacityPrice;//最大需量单价
	private Double approvedCapacity;//核定容量
	private Double actualDemand;//实际需量
	private Double electricalCapacity;//受电容量
	private Double activePowerFactor;//实际功率因数
	private Double standardPowerFactor;//力调标准
	private Double adjustmentRate;//调整率
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getCycle() {
		return cycle;
	}

	public Double getTotalCharge() {
		return totalCharge;
	}

	public Double getBasicCharge() {
		return basicCharge;
	}

	public Double getFactorCharge() {
		return factorCharge;
	}

	public Double getApexCharge() {
		return apexCharge;
	}

	public Double getPeakCharge() {
		return peakCharge;
	}

	public Double getFlatCharge() {
		return flatCharge;
	}

	public Double getValleyCharge() {
		return valleyCharge;
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

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public void setBasicCharge(Double basicCharge) {
		this.basicCharge = basicCharge;
	}

	public void setFactorCharge(Double factorCharge) {
		this.factorCharge = factorCharge;
	}

	public void setApexCharge(Double apexCharge) {
		this.apexCharge = apexCharge;
	}

	public void setPeakCharge(Double peakCharge) {
		this.peakCharge = peakCharge;
	}

	public void setFlatCharge(Double flatCharge) {
		this.flatCharge = flatCharge;
	}

	public void setValleyCharge(Double valleyCharge) {
		this.valleyCharge = valleyCharge;
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

	public Double getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}

	public Double getTotal() {
		return total;
	}

	public Double getThisMonthTotal() {
		return thisMonthTotal;
	}

	public Double getLastMonthTotal() {
		return lastMonthTotal;
	}

	public Double getApex() {
		return apex;
	}

	public Double getThisMonthApex() {
		return thisMonthApex;
	}

	public Double getLastMonthApex() {
		return lastMonthApex;
	}

	public Double getPeak() {
		return peak;
	}

	public Double getThisMonthPeek() {
		return thisMonthPeek;
	}

	public Double getLastMonthPeek() {
		return lastMonthPeek;
	}

	public Double getFlat() {
		return flat;
	}

	public Double getThisMonthFlat() {
		return thisMonthFlat;
	}

	public Double getLastMonthFlat() {
		return lastMonthFlat;
	}

	public Double getValley() {
		return valley;
	}

	public Double getThisMonthValley() {
		return thisMonthValley;
	}

	public Double getLastMonthValley() {
		return lastMonthValley;
	}

	public Double getReactiveTotal() {
		return reactiveTotal;
	}

	public Double getThisMonthReativeTotal() {
		return thisMonthReativeTotal;
	}

	public Double getLastMonthReativeTotal() {
		return lastMonthReativeTotal;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setThisMonthTotal(Double thisMonthTotal) {
		this.thisMonthTotal = thisMonthTotal;
	}

	public void setLastMonthTotal(Double lastMonthTotal) {
		this.lastMonthTotal = lastMonthTotal;
	}

	public void setApex(Double apex) {
		this.apex = apex;
	}

	public void setThisMonthApex(Double thisMonthApex) {
		this.thisMonthApex = thisMonthApex;
	}

	public void setLastMonthApex(Double lastMonthApex) {
		this.lastMonthApex = lastMonthApex;
	}

	public void setPeak(Double peak) {
		this.peak = peak;
	}

	public void setThisMonthPeek(Double thisMonthPeek) {
		this.thisMonthPeek = thisMonthPeek;
	}

	public void setLastMonthPeek(Double lastMonthPeek) {
		this.lastMonthPeek = lastMonthPeek;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public void setThisMonthFlat(Double thisMonthFlat) {
		this.thisMonthFlat = thisMonthFlat;
	}

	public void setLastMonthFlat(Double lastMonthFlat) {
		this.lastMonthFlat = lastMonthFlat;
	}

	public void setValley(Double valley) {
		this.valley = valley;
	}

	public void setThisMonthValley(Double thisMonthValley) {
		this.thisMonthValley = thisMonthValley;
	}

	public void setLastMonthValley(Double lastMonthValley) {
		this.lastMonthValley = lastMonthValley;
	}

	public void setReactiveTotal(Double reactiveTotal) {
		this.reactiveTotal = reactiveTotal;
	}

	public void setThisMonthReativeTotal(Double thisMonthReativeTotal) {
		this.thisMonthReativeTotal = thisMonthReativeTotal;
	}

	public void setLastMonthReativeTotal(Double lastMonthReativeTotal) {
		this.lastMonthReativeTotal = lastMonthReativeTotal;
	}

	public Double getAdjustmentRate() {
		return adjustmentRate;
	}
	public void setAdjustmentRate(Double adjustmentRate) {
		this.adjustmentRate = adjustmentRate;
	}
	public Double getActivePowerFactor() {
		return activePowerFactor;
	}

	public Double getStandardPowerFactor() {
		return standardPowerFactor;
	}

	public void setActivePowerFactor(Double activePowerFactor) {
		this.activePowerFactor = activePowerFactor;
	}

	public void setStandardPowerFactor(Double standardPowerFactor) {
		this.standardPowerFactor = standardPowerFactor;
	}

	public Double getApprovedCapacity() {
		return approvedCapacity;
	}

	public Double getActualDemand() {
		return actualDemand;
	}

	public Double getElectricalCapacity() {
		return electricalCapacity;
	}

	public void setApprovedCapacity(Double approvedCapacity) {
		this.approvedCapacity = approvedCapacity;
	}

	public void setActualDemand(Double actualDemand) {
		this.actualDemand = actualDemand;
	}

	public void setElectricalCapacity(Double electricalCapacity) {
		this.electricalCapacity = electricalCapacity;
	}

	public Integer getBaseChargeMode() {
		return baseChargeMode;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setBaseChargeMode(Integer baseChargeMode) {
		this.baseChargeMode = baseChargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Double getCapacityPrice() {
		return capacityPrice;
	}

	public Double getMaxCapacityPrice() {
		return maxCapacityPrice;
	}

	public void setCapacityPrice(Double capacityPrice) {
		this.capacityPrice = capacityPrice;
	}

	public void setMaxCapacityPrice(Double maxCapacityPrice) {
		this.maxCapacityPrice = maxCapacityPrice;
	}

	

}
