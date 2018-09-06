package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 电费明细
 * @author zhoujianjian
 * @date 2017年12月8日 下午4:50:57
 */
public class Charge implements Serializable{

	private static final long serialVersionUID = 1L;
	// 类型 1:尖电费   2:峰电费   3:平电费   4:谷电费   5:基本电费
	private Integer type;
	// 类型名称
	private String typeName;
	// 颜色十六进制
	private String color;
	// 电费金额（保留两位小数）
	private String charge;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	public Charge(Integer type, String typeName, String charge) {
		this.type = type;
		this.typeName = typeName;
		this.charge = charge;
	}
	public Charge(Integer type, String typeName, String color, String charge) {
		this.type = type;
		this.typeName = typeName;
		this.color = color;
		this.charge = charge;
	}
	
	public Charge() {
	}
	
	
	

}
