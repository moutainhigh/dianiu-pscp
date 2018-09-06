/**
 * 
 */
package com.edianniu.pscp.sps.bean;

/**
 * 0，不更新
 * 1:建议更新
 * 2:强制更新
 * 3:静默更新
 * @author cyl
 *
 */
public enum UpdateType {
	NOT_UPDATE(0,"不更新"),SUGGEST_UPDATE(1,"建议更新"),FORCE_UPDATE(2,"强制更新"),SILENT_UPDATE(3,"静默更新");
	private int value;
	private String desc;
	UpdateType(int value,String desc){
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
