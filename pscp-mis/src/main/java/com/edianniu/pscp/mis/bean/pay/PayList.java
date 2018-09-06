package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

public class PayList implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String type;
	
	private String name;
	
	private String desc;
	
	private Integer status;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
