package com.edianniu.pscp.message.meter.bean;

import java.io.Serializable;

/**
 * 电量信息
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月5日 上午11:29:08 
 * @version V1.0
 */
public class PowerChargeInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Double total;
	private Double apex;
	private Double peak;
	private Double flat;
	private Double valley;
	
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
}
