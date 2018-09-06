package com.edianniu.pscp.mis.bean.pay;

public enum UnionpayStatus {
	SUCCESS(1,"支付成功"),PAYING(0,"已受理，正在支付中"),FALSE(-1,"支付失败");
	
	private int value;
	
	private String desc;
	
	 UnionpayStatus(int value,String desc) {
		 
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	 
}
