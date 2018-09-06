package com.edianniu.pscp.cs.bean.power;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.LoadVO;

/**
 * 用电负荷
 * @author zhoujianjian
 * @date 2017年12月7日 下午7:08:57
 */
public class PowerLoadResult extends Result{

	private static final long serialVersionUID = 1L;
	// 负荷信息
	private LoadVO load;
	// 本月用电
	private String quantityOfThisMonth;
	// 本月电费
	private String chargeOfThisMonth;
	// 本日最高（负荷）
	private String maxLoadOfToday;
	// 上日最高（负荷）
	private String maxLoadOfLastDay;
	// 本月功率因数
	private String powerFactor ;
	// 刷新频率（单位：毫秒）
	private Integer frequency;

	public LoadVO getLoad() {
		return load;
	}

	public void setLoad(LoadVO load) {
		this.load = load;
	}

	public String getQuantityOfThisMonth() {
		return quantityOfThisMonth;
	}

	public void setQuantityOfThisMonth(String quantityOfThisMonth) {
		this.quantityOfThisMonth = quantityOfThisMonth;
	}

	public String getChargeOfThisMonth() {
		return chargeOfThisMonth;
	}

	public void setChargeOfThisMonth(String chargeOfThisMonth) {
		this.chargeOfThisMonth = chargeOfThisMonth;
	}

	public String getMaxLoadOfToday() {
		return maxLoadOfToday;
	}

	public void setMaxLoadOfToday(String maxLoadOfToday) {
		this.maxLoadOfToday = maxLoadOfToday;
	}

	public String getMaxLoadOfLastDay() {
		return maxLoadOfLastDay;
	}

	public void setMaxLoadOfLastDay(String maxLoadOfLastDay) {
		this.maxLoadOfLastDay = maxLoadOfLastDay;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	

}
