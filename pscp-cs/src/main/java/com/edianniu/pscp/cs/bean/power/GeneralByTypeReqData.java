package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;

public class GeneralByTypeReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long uid;
	
	private Integer offset;
	
	private Integer limit;
	
	private Integer type;
	
	private String date;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
