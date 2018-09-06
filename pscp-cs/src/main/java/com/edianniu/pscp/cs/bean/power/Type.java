package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

/**
 * 数据结构：Key-KeyName-Value
 * @author zhoujianjian
 * @date 2017年12月7日 下午7:00:30
 */
public class Type implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	
	private String keyName;
	// 取值范围
	private String value;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Type(Integer key, String keyName, String value) {
		this.key = key;
		this.keyName = keyName;
		this.value = value;
	}

	public Type(Integer key, String keyName) {
		this.key = key;
		this.keyName = keyName;
	}

	public Type() {}
	
	
	
	
}
