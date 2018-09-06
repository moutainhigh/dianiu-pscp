package com.edianniu.pscp.renter.mis.bean.renter;

/**
 * 租客缴费方式
 * @author zhoujianjian
 * @date 2018年4月18日 下午3:28:28
 */
public enum ChargeModeType {

	PAY_FIRST(1, "预缴费"),
	MONTH_SETTLE(2, "月结算");
	
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
	
	public static Boolean isValueExist(Integer value){
		if (null == value) {
			return false;
		}
		ChargeModeType[] chargeModeTypes = ChargeModeType.values();
		for (ChargeModeType chargeModeType : chargeModeTypes) {
			if (value.equals(chargeModeType.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static ChargeModeType parse(Integer value){
		if (null == value) {
			return PAY_FIRST;
		}
		ChargeModeType[] values = ChargeModeType.values();
		for (ChargeModeType chargeModeType : values) {
			if (value.equals(chargeModeType.getValue())) {
				return chargeModeType;
			}
		}
		return PAY_FIRST;
	}
	
}
