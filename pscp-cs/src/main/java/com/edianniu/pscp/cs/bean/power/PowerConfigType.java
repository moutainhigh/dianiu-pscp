package com.edianniu.pscp.cs.bean.power;

/**
 * 客户用电相关配置类型
 * @author zhoujianjian
 * @date 2018年1月10日 下午4:13:56
 */
public enum PowerConfigType {
	
	LOAD(1, "负荷"),
	POWER_FACTOR(2, "功率因数"),
	VOLTAGE(3, "电压");
	
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
	
	private PowerConfigType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	/**
	 * 判断value是否存在
	 * @param value
	 * @return
	 */
	public static Boolean isExist(Integer value){
		PowerConfigType[] values = PowerConfigType.values();
		for (PowerConfigType powerConfigType : values) {
			if (value.equals(powerConfigType.getValue())) {
				return true;
			}
		}
		return false;
	}

}
