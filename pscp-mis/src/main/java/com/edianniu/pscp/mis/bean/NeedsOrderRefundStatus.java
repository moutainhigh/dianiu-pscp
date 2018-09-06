package com.edianniu.pscp.mis.bean;

/**
 * 需求订单退款状态
 * 0：未退还        
 * 1: 已退还
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月25日 下午3:56:22 
 * @version V1.0
 */
public enum NeedsOrderRefundStatus {
	NORMAL(0,"未退还"),
	SUCCESS(1,"已退还");
	private int value;
	private String desc;
	NeedsOrderRefundStatus(int value,String desc){
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
