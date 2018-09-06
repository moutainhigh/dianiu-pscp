package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.QuantityVO;

/**
 * 门户：峰谷利用
 * @author zhoujianjian
 * @date 2017年12月16日 下午2:56:24
 */
public class UseFengGUResult extends Result{

	private static final long serialVersionUID = 1L;
	// 当日总电量
	private String quantityOfToday;
	// 当日谷电量
	private String quantityOfGu;
	// 当日总电费
	private String chargeOfToday;
	// 当日谷电费
	private String chargeOfGu;
	// 当日尖峰平谷分布及电费单价
	private List<DistributeOfTime> distributeOfTimes;
	// 用电量时刻分布
	private List<QuantityVO> quantities;
	// 监测时间段，指的是当前时间的上一个监测时段
	private String period;
	
	public String getQuantityOfToday() {
		return quantityOfToday;
	}
	public void setQuantityOfToday(String quantityOfToday) {
		this.quantityOfToday = quantityOfToday;
	}
	public String getQuantityOfGu() {
		return quantityOfGu;
	}
	public void setQuantityOfGu(String quantityOfGu) {
		this.quantityOfGu = quantityOfGu;
	}
	public List<DistributeOfTime> getDistributeOfTimes() {
		return distributeOfTimes;
	}
	public void setDistributeOfTimes(List<DistributeOfTime> distributeOfTimes) {
		this.distributeOfTimes = distributeOfTimes;
	}
	public List<QuantityVO> getQuantities() {
		return quantities;
	}
	public void setQuantities(List<QuantityVO> quantities) {
		this.quantities = quantities;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getChargeOfToday() {
		return chargeOfToday;
	}
	public void setChargeOfToday(String chargeOfToday) {
		this.chargeOfToday = chargeOfToday;
	}
	public String getChargeOfGu() {
		return chargeOfGu;
	}
	public void setChargeOfGu(String chargeOfGu) {
		this.chargeOfGu = chargeOfGu;
	}
}
