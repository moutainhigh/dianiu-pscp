package com.edianniu.pscp.mis.bean.company;

/**
 * 公司认证状态
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午10:31:54 
 * @version V1.0
 */
public enum CompanyAuthStatus {
	
	NOTAUTH(0,"未认证"),AUTHING(1,"认证中"),SUCCESS(2,"认证成功"),FAIL(-1,"认证失败");
	
	private int value;
	
	private String desc;
	
	CompanyAuthStatus(int value,String desc){
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
