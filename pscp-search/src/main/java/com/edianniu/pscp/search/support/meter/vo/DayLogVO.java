/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 日用电数据 包括电量和电费信息(峰谷信息，电价)
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class DayLogVO extends BaseReportVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String date;// 日期(yyyyMMdd)
	private Double totalCharge;// 总计电费
	private Double apexCharge;// 尖电费，
	private Double peakCharge;// 峰电费
	private Double flatCharge;// 平电费
	private Double valleyCharge;// 谷电费
	private Double total;// 总计电量
	private Double apex;// 尖电量，
	private Double peak;// 峰电量
	private Double flat;// 平电量
	private Double valley;// 谷电量
	
	private Double lastTotal;//上期示数
	private Double thisTotal;//本期示数
	private Double lastApex;//上期示数
	private Double thisApex;//本期示数
	private Double lastPeak;//上期示数
	private Double thisPeak;//本期示数
	private Double lastFlat;//上期示数
	private Double thisFlat;//本期示数
	private Double lastValley;//上期示数
	private Double thisValley;//本期示数
	private Double lastReactiveTotal;//上期示数
	private Double thisReactiveTotal;//本期示数
	/**统一单价**/
	private Double basePrice;// 统一单价，非分时
	/**分时单价**/
	private Double apexPrice;// 尖单价，
	private Double peakPrice;// 峰单价
	private Double flatPrice;// 平单价
	private Double valleyPrice;// 谷单价
	/** 无功电量kvar **/
	private Double reactiveTotal;
	private Double factor;// 实际功率因数
	// 当前最新信息
	private Double currentLoad;// 当前负荷
	private Double maxLoad;// 最高负荷
	private Double electric;// 今日用电量
	// 当前最新电压和电流信息(根据间隔时间来吧)
	private Double ua;// A相电压
	private Double ub;// B相电压
	private Double uc;// C相电压
	private Integer uStatus;// 0正常，1异常
	private Integer uaStatus;// 0正常,1过高,-1偏低
	private Integer ubStatus;// 0正常,1过高,-1偏低
	private Integer ucStatus;// 0正常,1过高,-1偏低
	private Double ia;// A相电流
	private Double ib;// B相电流
	private Double ic;// C相电流
	private Double iUnbalanceDegree;// 三相电流不平衡度
	private Double iaUnbalanceDegree;// A相电流不平衡度
	private Double ibUnbalanceDegree;// B相电流不平衡度
	private Double icUnbalanceDegree;// C相电流不平衡度
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getTotalCharge() {
		return totalCharge;
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

	public Double getTotal() {
		return total;
	}

	public Double getApex() {
		return apex;
	}

	public Double getPeak() {
		return peak;
	}

	public Double getFlat() {
		return flat;
	}

	public Double getValley() {
		return valley;
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

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setApex(Double apex) {
		this.apex = apex;
	}

	public void setPeak(Double peak) {
		this.peak = peak;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public void setValley(Double valley) {
		this.valley = valley;
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

	public Double getReactiveTotal() {
		return reactiveTotal;
	}

	public Double getFactor() {
		return factor;
	}

	public Double getCurrentLoad() {
		return currentLoad;
	}

	public Double getMaxLoad() {
		return maxLoad;
	}

	public Double getElectric() {
		return electric;
	}

	public Double getUa() {
		return ua;
	}

	public Double getUb() {
		return ub;
	}

	public Double getUc() {
		return uc;
	}

	public Integer getuStatus() {
		return uStatus;
	}

	public Integer getUaStatus() {
		return uaStatus;
	}

	public Integer getUbStatus() {
		return ubStatus;
	}

	public Integer getUcStatus() {
		return ucStatus;
	}

	public Double getIa() {
		return ia;
	}

	public Double getIb() {
		return ib;
	}

	public Double getIc() {
		return ic;
	}

	public Double getiUnbalanceDegree() {
		return iUnbalanceDegree;
	}

	public Double getIaUnbalanceDegree() {
		return iaUnbalanceDegree;
	}

	public Double getIbUnbalanceDegree() {
		return ibUnbalanceDegree;
	}

	public Double getIcUnbalanceDegree() {
		return icUnbalanceDegree;
	}

	public void setReactiveTotal(Double reactiveTotal) {
		this.reactiveTotal = reactiveTotal;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}

	public void setCurrentLoad(Double currentLoad) {
		this.currentLoad = currentLoad;
	}

	public void setMaxLoad(Double maxLoad) {
		this.maxLoad = maxLoad;
	}

	public void setElectric(Double electric) {
		this.electric = electric;
	}

	public void setUa(Double ua) {
		this.ua = ua;
	}

	public void setUb(Double ub) {
		this.ub = ub;
	}

	public void setUc(Double uc) {
		this.uc = uc;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public void setUaStatus(Integer uaStatus) {
		this.uaStatus = uaStatus;
	}

	public void setUbStatus(Integer ubStatus) {
		this.ubStatus = ubStatus;
	}

	public void setUcStatus(Integer ucStatus) {
		this.ucStatus = ucStatus;
	}

	public void setIa(Double ia) {
		this.ia = ia;
	}

	public void setIb(Double ib) {
		this.ib = ib;
	}

	public void setIc(Double ic) {
		this.ic = ic;
	}

	public void setiUnbalanceDegree(Double iUnbalanceDegree) {
		this.iUnbalanceDegree = iUnbalanceDegree;
	}

	public void setIaUnbalanceDegree(Double iaUnbalanceDegree) {
		this.iaUnbalanceDegree = iaUnbalanceDegree;
	}

	public void setIbUnbalanceDegree(Double ibUnbalanceDegree) {
		this.ibUnbalanceDegree = ibUnbalanceDegree;
	}

	public void setIcUnbalanceDegree(Double icUnbalanceDegree) {
		this.icUnbalanceDegree = icUnbalanceDegree;
	}

	public Double getLastTotal() {
		return lastTotal;
	}

	public Double getThisTotal() {
		return thisTotal;
	}

	public Double getLastApex() {
		return lastApex;
	}

	public Double getThisApex() {
		return thisApex;
	}

	public Double getLastPeak() {
		return lastPeak;
	}

	public Double getThisPeak() {
		return thisPeak;
	}

	public Double getLastFlat() {
		return lastFlat;
	}

	public Double getThisFlat() {
		return thisFlat;
	}

	public Double getLastValley() {
		return lastValley;
	}

	public Double getThisValley() {
		return thisValley;
	}

	public void setLastTotal(Double lastTotal) {
		this.lastTotal = lastTotal;
	}

	public void setThisTotal(Double thisTotal) {
		this.thisTotal = thisTotal;
	}

	public void setLastApex(Double lastApex) {
		this.lastApex = lastApex;
	}

	public void setThisApex(Double thisApex) {
		this.thisApex = thisApex;
	}

	public void setLastPeak(Double lastPeak) {
		this.lastPeak = lastPeak;
	}

	public void setThisPeak(Double thisPeak) {
		this.thisPeak = thisPeak;
	}

	public void setLastFlat(Double lastFlat) {
		this.lastFlat = lastFlat;
	}

	public void setThisFlat(Double thisFlat) {
		this.thisFlat = thisFlat;
	}

	public void setLastValley(Double lastValley) {
		this.lastValley = lastValley;
	}

	public void setThisValley(Double thisValley) {
		this.thisValley = thisValley;
	}

	public Double getLastReactiveTotal() {
		return lastReactiveTotal;
	}

	public Double getThisReactiveTotal() {
		return thisReactiveTotal;
	}

	public void setLastReactiveTotal(Double lastReactiveTotal) {
		this.lastReactiveTotal = lastReactiveTotal;
	}

	public void setThisReactiveTotal(Double thisReactiveTotal) {
		this.thisReactiveTotal = thisReactiveTotal;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
}
