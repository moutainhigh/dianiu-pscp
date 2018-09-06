package com.edianniu.pscp.sps.bean;
/**
 * 附件业务类型
 * @author zhoujianjian
 * 2017年9月25日下午11:11:21
 */
public enum BusinessType {
	
	NEEDS_ATTACHMENT(1,"需求附件业务类型"),
	COOPERATION_ATTACHMENT(2,"合作附件业务类型"),
	QUOTE_ATTACHMENT(3,"报价附件业务类型"),
	ACTUAL_PRICE_ATTACHMENT(4,"实际结算金额附件业务类型");
	
	private Integer value;
	private String desc;
	
	BusinessType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
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
