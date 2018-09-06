package com.edianniu.pscp.mis.bean.electrician;

public enum ElectricianAuthStatus {
	
	NOTAUTH(0,"未认证"),AUTHING(1,"认证中"),SUCCESS(2,"认证成功"),FAIL(-1,"认证失败");
	
	private int value;
	
	private String desc;
	
	ElectricianAuthStatus(int value,String desc){
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
