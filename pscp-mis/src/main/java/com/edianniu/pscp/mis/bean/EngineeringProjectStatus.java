package com.edianniu.pscp.mis.bean;
/**
 * 项目状态
 * -1：取消
 * 0：确认中
 * 1：进行中    
 * 2：费用待确认
 * 3：已结算
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午10:46:46 
 * @version V1.0
 */
public enum EngineeringProjectStatus {
	CANCLE(-1,"取消"),COFIRMING(0,"确认中"),EXECUTING(1,"进行中"),COST_TO_BE_CONFIRMED(2,"费用待确认"),SETTLED(3,"已结算");
	private int value;
	private String desc;
	EngineeringProjectStatus(int value,String desc){
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
