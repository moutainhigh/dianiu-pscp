package com.edianniu.pscp.das.meter.domain;

import com.edianniu.pscp.das.common.BaseDo;

public class MeterConfig  extends BaseDo{
	private static final long serialVersionUID = 1L;
    private Integer key;
    private String value;
    private Integer type;
	public Integer getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
