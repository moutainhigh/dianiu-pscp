package com.edianniu.pscp.mis.bean.company;

/**
 * 0(邀请，等待客户同意)
 * 1(已绑定，客户已同意)
 * -1(拒绝)
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 下午4:54:31 
 * @version V1.0
 */
public enum CompanyCustomerStatus {
	
	INVITE(0,"邀请"),BOUND(1,"已绑定"),REJECT(-1,"拒绝");
	
	private int value;
	
	private String desc;
	
	CompanyCustomerStatus(int value,String desc){
		this.value=value;
		this.desc=desc;
	}

	public int getValue() {
		return value;
	}

	
	public String getDesc() {
		return desc;
	}

	
	
	
}
