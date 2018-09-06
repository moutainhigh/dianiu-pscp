/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午8:07:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午8:07:48 
 * @version V1.0
 */
public enum SubEnergyCode {
	POWER("01C00","动力"),AIR("01B00","空调"),SPECIAL("01D00","特殊"),LIGHTING("01A00","照明"),OTHER("01X00","其他");
    private String code;
    private String desc;
    SubEnergyCode(String code,String desc){
    	this.code=code;
    	this.desc=desc;
    }
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
