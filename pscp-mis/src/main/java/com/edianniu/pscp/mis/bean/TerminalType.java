/**
 * 
 */
package com.edianniu.pscp.mis.bean;

/**
 * @author cyl
 *
 */
public enum TerminalType {

	MOBILE(1,"手机端"), PC(2,"PC"), WEIXIN(3,"微信");
	private int value;
	private String desc;

	TerminalType(int value,String desc) {
		this.value = value;
		this.desc=desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

}
