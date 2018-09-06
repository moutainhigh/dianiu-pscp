package com.edianniu.pscp.mis.bean.company;

/**
 * 0(邀请，等待电工同意)
 * 1(已绑定，电工已同意)
 * 2(申请解绑，企业)
 * 3(申请解绑，电工)
 * -1(拒绝)
 * -2(已解绑)
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 下午4:54:31 
 * @version V1.0
 */
public enum CompanyElectricianStatus {
	
	INVITE(0,"邀请"),
	BOUND(1,"已绑定"),
	COMPANY_APPLY_UNBUND(2,"申请解绑-企业"),
	ELECTRICIAN_APPLY_UNBUND(3,"申请解绑-电工"),
	REJECT(-1,"拒绝"),
	UNBUNDED(-2,"已解绑");
	
	private int value;
	
	private String desc;
	
	CompanyElectricianStatus(int value,String desc){
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
