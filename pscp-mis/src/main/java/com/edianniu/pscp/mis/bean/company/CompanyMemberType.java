/**
 * 
 */
package com.edianniu.pscp.mis.bean.company;

/**
 * 用户状态
 * @author cyl
 *
 */
public enum CompanyMemberType {
	FACILITATOR(1,"服务商"), CUSTOMER(2,"客户");
	private int value;
	private String desc;

	CompanyMemberType(int value,String desc) {
		this.value = value;
		this.desc=desc;
	}

	public int getValue() {
		return value;
	}
	public String getDesc(){
		return desc;
	}
	public static CompanyMemberType parse(Integer value){
		if(value==null){
			return null;
		}
		if(value==CUSTOMER.getValue()){
			return CUSTOMER;
		}
		if(value==FACILITATOR.getValue()){
			return FACILITATOR;
		}
		return null;
	}

}
