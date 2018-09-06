package com.edianniu.pscp.cs.bean.power;

/**
 * 尖 峰 平 谷
 * @author zhoujianjian
 * @date 2017年12月26日 下午6:12:10
 */
public enum TimeType {

	JIAN(1, "尖"),
	FENG(2, "峰"),
	PING(3, "平"),
	GU(4, "谷");
	
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
	
	private TimeType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	
}
