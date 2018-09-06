package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 尖峰平谷时间分布及电费单价
 * @author zhoujianjian
 * @date 2017年12月16日 下午2:59:20
 */
public class DistributeOfTime implements Serializable, Comparable<DistributeOfTime>{

	private static final long serialVersionUID = 1L;
	// 时刻
	private String time;
	// 种类   JIAN  FENG  PING  GU
	private String type;
	// 单价
	private String price;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	// 时间升序 排序
	@Override
	public int compareTo(DistributeOfTime o) {
		int compareTo = this.getTime().compareTo(o.getTime());
		return compareTo;
	}
	
	

}
