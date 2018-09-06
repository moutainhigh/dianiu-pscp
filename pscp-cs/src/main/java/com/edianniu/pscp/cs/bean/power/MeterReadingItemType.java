package com.edianniu.pscp.cs.bean.power;
/**
 * 抄表项类型
 * @author zhoujianjian
 * @date 2017年12月27日 下午7:48:04
 */
public enum MeterReadingItemType {

	ACTIVE_TOTAL(1,"有功（总）"),
	ACTIVE_JIAN(2,"有功（尖）"),
	ACTIVE_FENG(3,"有功（峰）"),
	ACTIVE_PING(4,"有功（平）"),
	ACTIVE_GU(5,"有功（谷）"),
	REACTIVE_TOTAL(6,"无功（总）");
	
	private Integer value;
	private String desc;
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
	
	private MeterReadingItemType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
}
