package com.edianniu.pscp.cs.bean.power;

/**
 * 用电设备类型 
 * @author zhoujianjian
 * @date 2017年12月14日 上午9:57:35
 */
public enum EquipmentType {
	
	INTEGRATION(0, "综合"),
	POWER(1, "动力"),
	LIGHTING(2, "照明"),
	AIR_CONDITIONER(3, "空调"),
	SPECIAL(4, "特殊");
	
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
	
	private EquipmentType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	/**
	 * 判断Value是否存在
	 * @param value
	 * @return
	 */
	public static Boolean isExist(Integer value){
		EquipmentType[] values = EquipmentType.values();
		for (EquipmentType equipmentType : values) {
			if (value.equals(equipmentType.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据value获取EquipmentType对象
	 * @param value
	 * @return
	 */
	public static EquipmentType getByValue(Integer value){
		if (null == value) {
			return null;
		}
		EquipmentType[] values = EquipmentType.values();
		for (EquipmentType equipmentType : values) {
			if (value.equals(equipmentType.getValue())) {
				return equipmentType;
			}
		}
		return null;
	}
	
	
}
