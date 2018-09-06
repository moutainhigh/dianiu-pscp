package com.edianniu.pscp.mis.bean.pay;

public enum TradeType {
	
	XIAOFEI(1,"消费"),RECHARGE(2,"充值"),DAIFU(3,"代付");
	private int value;
	
	private String desc;
	
	TradeType(int value,String desc){
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
