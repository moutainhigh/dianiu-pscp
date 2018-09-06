package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

/**
 * 实时负荷，门户使用
 * @author zhoujianjian
 * @date 2017年12月12日 下午8:59:24
 */
public class RealTimeLoadVO implements Serializable{

	private static final long serialVersionUID = 1L;
	// 时刻
	private String time;
	// 负荷值
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
	public RealTimeLoadVO(String time, String value) {
		this.time = time;
		this.value = value;
	}
	public RealTimeLoadVO() {
	}
	
	

}
