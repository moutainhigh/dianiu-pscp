package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

/**
 * ua,ub,bc
 * @author zhoujianjian
 * @date 2017年12月15日 下午4:19:08
 */
public class VoltageVO implements Serializable{

	private static final long serialVersionUID = 1L;
	// 时刻
	private String time;
	// 电压值
	private String value;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public VoltageVO(String time, String value) {
		this.time = time;
		this.value = value;
	}

	public VoltageVO() {
	}
	
	

}
