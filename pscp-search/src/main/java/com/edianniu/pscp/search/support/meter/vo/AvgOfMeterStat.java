package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

public class AvgOfMeterStat implements Serializable{

	private static final long serialVersionUID = 1L;
	private String meterId;//仪表ID
	
	/**
	 * 周一至周五的平均电量（不包含周末）
	 * (周一平均 + 周二平均 + ... + 周五平均) / 5
	 */
	private Double avgOfTotal; // 总电量
	private Double avgOfApex;  // 尖电量
	private Double avgOfPeak;  // 峰电量
	private Double avgOfFlat;  // 平电量
	private Double avgOfValley;// 谷电量
	
	
	
	/**
	 * 周一至周日的日平均电量（包含周末）
	 * (周一平均  + 周二平均 + ... + 周日平均) / 7
	 */
	private Double avgOfTotalAll; // 总电量
	private Double avgOfApexAll;  // 尖电量
	private Double avgOfPeakAll;  // 峰电量
	private Double avgOfFlatAll;  // 平电量
	private Double avgOfValleyAll;// 谷电量
	
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Double getAvgOfTotal() {
		return avgOfTotal;
	}
	public void setAvgOfTotal(Double avgOfTotal) {
		this.avgOfTotal = avgOfTotal;
	}
	public Double getAvgOfApex() {
		return avgOfApex;
	}
	public void setAvgOfApex(Double avgOfApex) {
		this.avgOfApex = avgOfApex;
	}
	public Double getAvgOfPeak() {
		return avgOfPeak;
	}
	public void setAvgOfPeak(Double avgOfPeak) {
		this.avgOfPeak = avgOfPeak;
	}
	public Double getAvgOfFlat() {
		return avgOfFlat;
	}
	public void setAvgOfFlat(Double avgOfFlat) {
		this.avgOfFlat = avgOfFlat;
	}
	public Double getAvgOfValley() {
		return avgOfValley;
	}
	public void setAvgOfValley(Double avgOfValley) {
		this.avgOfValley = avgOfValley;
	}
	public Double getAvgOfTotalAll() {
		return avgOfTotalAll;
	}
	public void setAvgOfTotalAll(Double avgOfTotalAll) {
		this.avgOfTotalAll = avgOfTotalAll;
	}
	public Double getAvgOfApexAll() {
		return avgOfApexAll;
	}
	public void setAvgOfApexAll(Double avgOfApexAll) {
		this.avgOfApexAll = avgOfApexAll;
	}
	public Double getAvgOfPeakAll() {
		return avgOfPeakAll;
	}
	public void setAvgOfPeakAll(Double avgOfPeakAll) {
		this.avgOfPeakAll = avgOfPeakAll;
	}
	public Double getAvgOfFlatAll() {
		return avgOfFlatAll;
	}
	public void setAvgOfFlatAll(Double avgOfFlatAll) {
		this.avgOfFlatAll = avgOfFlatAll;
	}
	public Double getAvgOfValleyAll() {
		return avgOfValleyAll;
	}
	public void setAvgOfValleyAll(Double avgOfValleyAll) {
		this.avgOfValleyAll = avgOfValleyAll;
	}
	
}
