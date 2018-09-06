package com.edianniu.pscp.mis.bean.pay;

public enum UnionpayType {
	PHONE(1,"手机控件支付"),DF(2,"代付");
	private String desc;
	
	private int value;
	
	UnionpayType(int value,String desc){
		this.desc=desc;
		this.value=value;
	}
	
	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}
	

}
