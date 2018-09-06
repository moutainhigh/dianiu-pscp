package com.edianniu.pscp.cs.bean;

/**
 * 客户设备类型
 * @author zhoujianjian
 * @date 2017年11月2日 上午10:22:30
 */
public enum CustomerEquipmentType {

	SAFETY_EQUIPMENT(1, "安全用具"),
	FIREFIGHTING_EQUIPMENT(2, "消防设施");
	
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
	
    private	CustomerEquipmentType(Integer value, String desc){
    	this.value = value;
    	this.desc = desc;
    }
    
    /**
   	 * 判断value是否存在
   	 * @param value
   	 * @return
   	 */
    public static Boolean isExist(Integer value){
    	CustomerEquipmentType[] values = CustomerEquipmentType.values();
    	for (CustomerEquipmentType customerEquipmentType : values) {
			if (value.equals(customerEquipmentType.getValue())) {
				return true;
			}
		}
    	return false;
    }
    
	
}
