package com.edianniu.pscp.sps.bean;

/**
 * 项目状态
 * @author zhoujianjian
 * 2017年9月25日上午9:54:09
 */
public enum ProjectStatus {
	
	PAYFAILD(-2,"支付失败"),
	CANCLED(-1,"取消"),
	CONFIRMING(0,"确认中"),
	ONGOING(1,"进行中"),
	CONFIRM_COST(2,"费用待确认"),
	SETTLED(3,"已结算");
	
	ProjectStatus(Integer value,String desc){
		this.value=value;
		this.desc=desc;
	}
	
	private Integer value;
	private String desc;
	
	public Integer getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
	

}
