package com.edianniu.pscp.cs.bean;

/**
 * 服务商客户状态
 * @author zhoujianjian
 * 2017年10月9日下午6:15:04
 */
public enum CompanyCustomerStatus {
	
	INVITATION(0,"邀请"),
	AGREE(1,"已绑定"),
	REJECT(-1,"拒绝");
	
	private Integer value;
	private String desc;
	
	CompanyCustomerStatus(Integer value, String desc){
		this.desc = desc;
		this.value = value;
	}

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

}
