package com.edianniu.pscp.mis.bean;

/**
 * 需求状态
 *-3：已超时        
 *-2：已取消
 *-1：审核不通过
 * 0：审核中
 * 1：响应中(审核通过)
 * 2：报价中
 * 3：已报价
 * 4：已合作
 * @author zhoujianjian
 * 2017年9月15日下午3:17:40
 */
public enum NeedsStatus {
	OVERTIME(-3,"已超时"),
	CANCELED(-2,"已取消"),
	AUDIT_FAIL(-1,"审核不通过"),
	AUDITING(0,""),
	AUDIT_SUCCESS(1,"响应中(审核通过)"),
	QUOTING(2,"报价中"),
	QUOTED(3,"已报价"),
	COOPETATED(4,"已合作");
	private int value;
	private String desc;
	NeedsStatus(int value,String desc){
		this.value=value;
		this.desc=desc;
	}
	/*//已超时
	public static final Integer OVERTIME = -3;
	//已取消
	public static final Integer CANCELED = -2;
	//审核不通过
	public static final Integer FAIL_AUDIT = -1;
	//审核中
	public static final Integer AUDITING = 0;
	//响应中（审核通过）
	public static final Integer RESPONDING = 1;
	//报价中
	public static final Integer QUOTING = 2;
	//已报价
	public static final Integer QUOTED = 3;
	//已合作
	public static final Integer COOPETATED = 4;*/
	public int getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
	
}
