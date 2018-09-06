package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 电费单
 * @author zhoujianjian
 * @date 2017年12月8日 下午5:03:09
 */
public class ChargeBillResult extends Result{

	private static final long serialVersionUID = 1L;
	
	// 电费周期
	private String period;
	// 当月电费合计（保留两位小数）
	private String totalChargeOfThisMonth;
	// 奖励金（保留两位小数）
	private String award;
	// 电度电费计费方式(0普通       1分时)
	private Integer chargeMode;
	// 电费明细
	private List<Charge> charges;
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTotalChargeOfThisMonth() {
		return totalChargeOfThisMonth;
	}
	public void setTotalChargeOfThisMonth(String totalChargeOfThisMonth) {
		this.totalChargeOfThisMonth = totalChargeOfThisMonth;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public List<Charge> getCharges() {
		return charges;
	}
	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	
}
