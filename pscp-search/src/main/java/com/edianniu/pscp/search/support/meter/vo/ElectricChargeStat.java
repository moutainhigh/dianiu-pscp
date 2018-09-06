/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 电量电费数据统计
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48
 * @version V1.0
 */
public class ElectricChargeStat  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String date;// 日期(yyyyMM|yyyyMMdd|yyyy)
	private String meterId;//仪表ID
	private Double totalElectric;//总电量
	private Double totalCharge;//总电费
	private Double lastTotal;//上期读数
	private Double thisTotal;//本期读数
	private Double apexPrice;//尖电价
	private Double peekPrice;//峰电价
	private Double flatPrice;// 平电价
	private Double valleyPrice;//谷电价
	
	private Double apexElectric;// 尖电量，
	private Double lastApex;//上期读数
	private Double thisApex;//本期读数
	private Double peakElectric;// 峰电量
	private Double lastPeak;//上期读数
	private Double thisPeak;//本期读数
	private Double flatElectric;// 平电量
	private Double lastFlat;//上期读数
	private Double thisFlat;//本期读数
	private Double valleyElectric;//谷电量
	private Double lastValley;//上期读数
	private Double thisValley;//本期读数
	
	private Double apexCharge;// 尖电费，
	private Double peakCharge;// 峰电费
	private Double flatCharge;// 平电费
	private Double valleyCharge;// 谷电费
	
	private String subTermCode;//分项编码 转换为：分项类型-1:动力设备2:照明设备 3:空调设备 4:特殊设备
	private Integer rate;//倍率
	
	public String getDate() {
		return date;
	}
	public String getMeterId() {
		return meterId;
	}
	public Double getTotalElectric() {
		return totalElectric;
	}
	public Double getTotalCharge() {
		return totalCharge;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public void setTotalElectric(Double totalElectric) {
		this.totalElectric = totalElectric;
	}
	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}
	public Double getApexPrice() {
		return apexPrice;
	}
	public Double getPeekPrice() {
		return peekPrice;
	}
	public Double getFlatPrice() {
		return flatPrice;
	}
	public Double getValleyPrice() {
		return valleyPrice;
	}
	public Double getApexElectric() {
		return apexElectric;
	}
	public Double getPeakElectric() {
		return peakElectric;
	}
	public Double getFlatElectric() {
		return flatElectric;
	}
	public Double getValleyElectric() {
		return valleyElectric;
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
	public Integer getRate() {
		return rate;
	}
	public void setApexPrice(Double apexPrice) {
		this.apexPrice = apexPrice;
	}
	public void setPeekPrice(Double peekPrice) {
		this.peekPrice = peekPrice;
	}
	public void setFlatPrice(Double flatPrice) {
		this.flatPrice = flatPrice;
	}
	public void setValleyPrice(Double valleyPrice) {
		this.valleyPrice = valleyPrice;
	}
	public void setApexElectric(Double apexElectric) {
		this.apexElectric = apexElectric;
	}
	public void setPeakElectric(Double peakElectric) {
		this.peakElectric = peakElectric;
	}
	public void setFlatElectric(Double flatElectric) {
		this.flatElectric = flatElectric;
	}
	public void setValleyElectric(Double valleyElectric) {
		this.valleyElectric = valleyElectric;
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
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public String getSubTermCode() {
		return subTermCode;
	}
	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
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
}
