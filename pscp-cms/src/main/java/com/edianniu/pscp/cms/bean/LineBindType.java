package com.edianniu.pscp.cms.bean;

import org.apache.commons.lang3.StringUtils;

public enum LineBindType {
	
	MAIN_LINE(0,"主线"),
	BUILDING(1,"楼宇"),
	EQUIPMENT(2,"设备");
	//FLOOR(3,"楼层");
	
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
	
	private LineBindType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public static Boolean isExit(Integer value){
		LineBindType[] values = LineBindType.values();
		for (LineBindType lineBindType : values) {
			if (value.equals(lineBindType.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据value获取对象
	 * @param value
	 * @return
	 */
	public static LineBindType getByValue(String value){
		if (StringUtils.isBlank(value)) {
			return null;
		}
		LineBindType[] values = LineBindType.values();
		for (LineBindType lineBindType : values) {
			if (value.equals(lineBindType.getValue())) {
				return lineBindType;
			}
		}
		return null;
	}
	
}
