package com.edianniu.pscp.cms.bean;

import java.io.Serializable;

public class Attachment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String fid;

	private String urlAndFid;

	private Integer orderNum;

	private Integer type;

	private Integer isOpen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getUrlAndFid() {
		return urlAndFid;
	}

	public void setUrlAndFid(String urlAndFid) {
		this.urlAndFid = urlAndFid;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

}
