/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * 日用电信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayElectricChargeReqData extends AbstractReportReq implements Serializable{
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
	
	private Double apexPrice;// 尖单价，
	private Double peakPrice;// 峰单价
	private Double flatPrice;// 平单价
	private Double valleyPrice;// 谷单价
	/**无功电量kvar**/
	//private Double reactiveTotal;
	/***分项能耗***/
	//private Double power;//动力用电量
	//private Double air;//空调用电量
	//private Double lighting;//照明用电量
	//private Double special;//特殊用电量
	//private Double other;//其他用电量
	
	@Override
	public String getId() {//主键
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+this.getDate();
	}

	public String getDate() {
		return date;
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

	public void setDate(String date) {
		this.date = date;
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

	
	
}
