package com.edianniu.pscp.message.bean;

/**
 * 告警类型
 * @author zhoujianjian
 * @date 2018年3月5日 下午6:02:31
 */
public enum WarningType {
	
	VOLTAGE_HIGH (1, "电压偏高"),
	VOLTAGE_LOW  (2, "电压偏低"),
	VOLTAGE_ERROR(3, "电压异常"),
	CURRENT_UNBALANCE(4, "电流不平衡"),
	POWER_FACTOR_ABNORMAL(10, "功率因数异常(偏低)"),
	LOAD_HIGH(11, "负荷偏高"),
	LOAD_LOW( 12, "负荷偏低"),
	GATEWAY_OFFLINE(13, "设备掉线");
	
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

	private WarningType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	
	 /**
   	 * 判断value是否存在
   	 * @param value
   	 * @return
   	 */
	public static Boolean isExist(Integer value){
		WarningType[] values = WarningType.values();
		for (WarningType warningType : values) {
			if (value.equals(warningType.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据value获取WarningType对象
	 * @param value
	 * @return
	 */
	public static WarningType getByValue(Integer value){
		if (!isExist(value)) {
			return null;
		}
		WarningType[] values = WarningType.values();
		for (WarningType warningType : values) {
			if (value.equals(warningType.value)) {
				return warningType;
			}
		}
		return null;
	}
}
