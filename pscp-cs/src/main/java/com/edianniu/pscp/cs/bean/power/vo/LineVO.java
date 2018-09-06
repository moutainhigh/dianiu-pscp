package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

public class LineVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public LineVO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public LineVO(Long id, String name, Long parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public LineVO() {}
	
	

}
