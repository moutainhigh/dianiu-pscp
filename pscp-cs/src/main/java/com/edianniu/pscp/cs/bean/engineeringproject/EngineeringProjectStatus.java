package com.edianniu.pscp.cs.bean.engineeringproject;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目状态
 * @author zhoujianjian
 * 2017年9月25日下午11:13:41
 */
public enum EngineeringProjectStatus {
	
	PAYFAILD(-2,"支付失败"),
	CANCLED(-1,"取消"),
	CONFIRMING(0,"确认中"),
	ONGOING(1,"进行中"),
	CONFIRM_COST(2,"费用待确认"),
	SETTLED(3,"已结算");
	
	private Integer value;
	private String desc;
	
	EngineeringProjectStatus(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}

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
	
	public static EngineeringProjectStatus getByDesc(String desc){
		if (StringUtils.isBlank(desc)) {
			return CONFIRMING;
		}
		EngineeringProjectStatus[] values = EngineeringProjectStatus.values();
		for (EngineeringProjectStatus projectStatus : values) {
			if (projectStatus.getDesc().equals(desc)) {
			return projectStatus;	
			}
		}
		return CONFIRMING;
	}

}
