/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.vo;

import java.io.Serializable;

/**
 * 日用电量
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayElectricVO extends BaseReportVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;//日期(yyyyMMdd)
	/**有功电量kwh**/
	private Double total;//用电量
	private Double apex;//尖电量
	private Double peak;//峰电量
	private Double flat;//平电量
	private Double valley;//谷电量
	/**无功电量kvar**/
	private Double reactiveTotal;
	/***分项能耗***/
	private Double power;//动力用电量
	private Double air;//空调用电量
	private Double lighting;//照明用电量
	private Double special;//特殊用电量
	private Double other;//其他用电量
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
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
	public Double getReactiveTotal() {
		return reactiveTotal;
	}
	public Double getPower() {
		return power;
	}
	public Double getAir() {
		return air;
	}
	public Double getLighting() {
		return lighting;
	}
	public Double getSpecial() {
		return special;
	}
	public Double getOther() {
		return other;
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
	public void setReactiveTotal(Double reactiveTotal) {
		this.reactiveTotal = reactiveTotal;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public void setAir(Double air) {
		this.air = air;
	}
	public void setLighting(Double lighting) {
		this.lighting = lighting;
	}
	public void setSpecial(Double special) {
		this.special = special;
	}
	public void setOther(Double other) {
		this.other = other;
	}
	
}
