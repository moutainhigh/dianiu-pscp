package com.edianniu.pscp.das.meter.bean;

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
	private Double lastTotal;//上期示数
	private Double thisTotal;//本期示数
	private Double apex;
	private Double lastApex;//上期示数
	private Double thisApex;//本期示数
	private Double peak;
	private Double lastPeak;//上期示数
	private Double thisPeak;//本期示数
	private Double flat;
	private Double lastFlat;//上期示数
	private Double thisFlat;//本期示数
	private Double valley;
	private Double lastValley;//上期示数
	private Double thisValley;//本期示数
	private Double reactiveTotal;
	private Double lastReactiveTotal;//上期示数
	private Double thisReactiveTotal;//本期示数
	
	public Double getReactiveTotal() {
		return reactiveTotal;
	}
	public void setReactiveTotal(Double reactiveTotal) {
		this.reactiveTotal = reactiveTotal;
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
	public Double getLastReactiveTotal() {
		return lastReactiveTotal;
	}
	public Double getThisReactiveTotal() {
		return thisReactiveTotal;
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
	public void setLastReactiveTotal(Double lastReactiveTotal) {
		this.lastReactiveTotal = lastReactiveTotal;
	}
	public void setThisReactiveTotal(Double thisReactiveTotal) {
		this.thisReactiveTotal = thisReactiveTotal;
	}
}
