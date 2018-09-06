package com.edianniu.pscp.cs.commons;

public enum TrueStatus {
	
	NO(0,"否"),YES(1,"是");
	
	private int value;
	
	private String desc;
	
	TrueStatus(int value,String desc){
		this.value=value;
		this.desc=desc;
	}

	public int getValue() {
		return value;
	}

	
	public String getDesc() {
		return desc;
	}

	
	
	
}
