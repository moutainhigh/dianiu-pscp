/**
 * 
 */
package com.edianniu.pscp.mis.bean.user;

/**
 * 用户状态
 * @author cyl
 *
 */
public enum UserStatus {
	ENABLE(1,"启用"), DISABLE(0,"禁用");
	private int value;
	private String desc;

	UserStatus(int value,String desc) {
		this.value = value;
		this.desc=desc;
	}

	public int getValue() {
		return value;
	}
	public String getDesc(){
		return desc;
	}
	public static UserStatus parse(Integer value){
		if(value==null){
			return DISABLE;
		}
		if(value==DISABLE.getValue()){
			return DISABLE;
		}
		if(value==ENABLE.getValue()){
			return ENABLE;
		}
		return DISABLE;
	}

}
