package com.edianniu.pscp.renter.mis.bean.renter;

/**
 * 租客账号状态、闸门状态
 * @author zhoujianjian
 * @date 2018年3月30日 下午3:10:43
 */
public enum RenterStatus {
	
	FORBIDEN(0, "禁止、开闸"),
	NORMAL(1, "正常、合闸");
	
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
	
	private RenterStatus(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public static Boolean isValueExist(Integer value){
		if (null == value) {
			return false;
		}
		RenterStatus[] renterStatusList = RenterStatus.values();
		for (RenterStatus renterStatus : renterStatusList) {
			if (value.equals(renterStatus.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static RenterStatus parse(Integer value){
		if (null == value) {
			return NORMAL;
		}
		RenterStatus[] values = RenterStatus.values();
		for (RenterStatus renterStatus : values) {
			if (renterStatus.getValue().equals(value)) {
				return renterStatus;
			}
		}
		return NORMAL;
	}

}
