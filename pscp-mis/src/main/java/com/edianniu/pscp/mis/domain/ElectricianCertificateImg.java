package com.edianniu.pscp.mis.domain;

public class ElectricianCertificateImg  extends BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long electricianId;
	
	private String fileId;
	
	
	private  Integer status;
	
	private Integer orderNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Long getElectricianId() {
		return electricianId;
	}

	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	

	
	
	

	

}
