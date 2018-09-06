package com.edianniu.pscp.cs.bean.power;

/**
 * 电度电费计费方式
 * @author zhoujianjian
 * @date 2018年1月16日 下午2:36:08
 */
public enum ChargeModeType {

	NORMAL(0, "普通"),
	DIVIDE_TIME(1, "分时");
	
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
	
	private ChargeModeType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public static boolean isExist(Integer value){
		ChargeModeType[] values = ChargeModeType.values();
		for (ChargeModeType chargeModeType : values) {
			if (value.equals(chargeModeType.getValue())) {
				return true;
			}
		}
		return false;
	}
}
