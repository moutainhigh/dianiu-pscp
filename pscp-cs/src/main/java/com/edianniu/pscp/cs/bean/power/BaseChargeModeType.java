package com.edianniu.pscp.cs.bean.power;

/**
 * 基本电费计费方式
 * @author zhoujianjian
 * @date 2018年2月1日 上午11:58:42
 */
public enum BaseChargeModeType {
	
	MAX_CAPACITY(1, "变压器容量"),
	MAX_DEMAND(2, "最大需量");
	
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
	
	private BaseChargeModeType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public static boolean isExist(Integer value){
		BaseChargeModeType[] values = BaseChargeModeType.values();
		for (BaseChargeModeType type : values) {
			if (value.equals(type.getValue())) {
				return true;
			}
		}
		return false;
	}
}
