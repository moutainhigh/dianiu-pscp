package com.edianniu.pscp.cs.bean.power;

public enum ChargeType {
	
	COUNT(0,"电度电费"),
	JIAN(1,"尖电费"),
	FENG(2,"峰电费"),
	PING(3,"平电费"),
	GU(4,"谷电费"),
	BASIC(5,"基本电费"),
	FACTOR(6,"力调电费");
	
	private Integer value;
	private String desc;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	private ChargeType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	

}
