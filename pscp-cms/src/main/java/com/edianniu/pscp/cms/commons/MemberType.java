/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 上午10:53:32 
 * @version V1.0
 */
package com.edianniu.pscp.cms.commons;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月20日 上午10:53:32 
 * @version V1.0
 */
public enum MemberType {
	FACILITATOR(1,"facilitator"),CUSTOMER(2,"customer");
	private int value;
	private String name;
	MemberType(int value,String name){
		this.value=value;
		this.name=name;
	}
	public static  boolean existName(String name){
		if(StringUtils.isBlank(name)){
			return false;
		}
		for(MemberType memberType:MemberType.values()){
			if(memberType.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	public static MemberType parse(String name){
		for(MemberType memberType:MemberType.values()){
			if(memberType.getName().equals(name)){
				return memberType;
			}
		}
		return FACILITATOR;
	}
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
}
