package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 当日实时功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午2:51:35
 */
public class PowerFactor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 时刻
	private String time;
	// 功率因数
	private String powerFactor;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}

	public PowerFactor(String time, String powerFactor) {
		this.time = time;
		this.powerFactor = powerFactor;
	}

	public PowerFactor() {
	}
	
	

	
}
