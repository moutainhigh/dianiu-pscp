package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

/**
 * 门户：时刻电量
 * @author zhoujianjian
 * @date 2017年12月16日 下午3:07:20
 */
public class QuantityVO implements Serializable{

	private static final long serialVersionUID = 1L;
	// 时间
	private String time;
	// 电量
	private String value;
	// 电费
	private String charge;
	// 类型    1尖    2峰      3平       4谷      0没有数据   
	private Integer type;

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

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public QuantityVO(String time, String value) {
		this.time = time;
		this.value = value;
	}
	
	public QuantityVO(String time, String value, String charge) {
		this.time = time;
		this.value = value;
		this.charge = charge;
	}
	
	public QuantityVO(String time, String value, String charge, Integer type) {
		this.time = time;
		this.value = value;
		this.charge = charge;
		this.type = type;
	}

	public QuantityVO() {}
	
}
