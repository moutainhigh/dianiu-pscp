package com.edianniu.pscp.cs.bean.power;

import java.util.List;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.cs.bean.power.vo.CollectorPointVO;

/**
 * 功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午3:03:01
 */
public class PowerFactorResult extends Result{

	private static final long serialVersionUID = 1L;
	// 本月功率因数（保留两位小数）
	private String powerFactor;
	// 电费奖惩率（保留两位小数）
	private String awardRate;
	// 电费奖惩金额（保留两位小数，奖励为正数，惩罚为负数）
	private String awardMoney;
	// 当日实时功率因数
	private List<PowerFactor> powerFactors;
	// 监测点
	private List<CollectorPointVO> collectorPoints;
	// 实时功率因数
	private String powerFactorOfNow;
	// 功率因数临界值
	private List<Type> limits; 
	
	public String getPowerFactor() {
		return powerFactor;
	}
	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}
	public String getAwardRate() {
		return awardRate;
	}
	public void setAwardRate(String awardRate) {
		this.awardRate = awardRate;
	}
	public String getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(String awardMoney) {
		this.awardMoney = awardMoney;
	}
	public List<PowerFactor> getPowerFactors() {
		return powerFactors;
	}
	public void setPowerFactors(List<PowerFactor> powerFactors) {
		this.powerFactors = powerFactors;
	}
	public List<CollectorPointVO> getCollectorPoints() {
		return collectorPoints;
	}
	public void setCollectorPoints(List<CollectorPointVO> collectorPoints) {
		this.collectorPoints = collectorPoints;
	}
	public String getPowerFactorOfNow() {
		return powerFactorOfNow;
	}
	public void setPowerFactorOfNow(String powerFactorOfNow) {
		this.powerFactorOfNow = powerFactorOfNow;
	}
	public List<Type> getLimits() {
		return limits;
	}
	public void setLimits(List<Type> limits) {
		this.limits = limits;
	}
	

}
