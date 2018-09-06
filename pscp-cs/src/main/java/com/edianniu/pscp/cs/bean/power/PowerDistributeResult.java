package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;

/**
 * 电量分布
 * @author zhoujianjian
 * @date 2017年12月8日 上午10:48:40
 */
public class PowerDistributeResult extends Result{

	private static final long serialVersionUID = 1L;
	
	// 设备当月用电量
	private List<Type> quantities;
	
	// 各时段电费
	private List<Type> charges;
	
	// 电度电费计费方式(0普通       1分时)
	private Integer chargeMode;

	public List<Type> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Type> quantities) {
		this.quantities = quantities;
	}

	public List<Type> getCharges() {
		return charges;
	}

	public void setCharges(List<Type> charges) {
		this.charges = charges;
	}

	public Integer getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	
	
}
