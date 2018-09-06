package com.edianniu.pscp.cms.bean;

/**
 * 仪表状态
 * @author zhoujianjian
 * @date 2017年12月19日 下午8:10:38
 */
public enum MeterStatus {

	OFF_LINE(0,"下线（默认）"),
	ON_LINE(1,"上线"),
	OTHERS(2,"其他状态");
	
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
	
	private MeterStatus(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
}
