package com.edianniu.pscp.message.meter.domain;

import java.util.Date;

import com.edianniu.pscp.message.commons.BaseDo;

/**
 * 智能终端--客户设备告警
 */
public class Warning extends BaseDo{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long companyId;
	
	private Integer warningType;
	
	private String warningObject;
	
	private Date occurTime;
	
	private Date revertTime;
	
	private String description;
	
	private Integer dealStatus;
	
	private Integer readStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getWarningType() {
		return warningType;
	}

	public void setWarningType(Integer warningType) {
		this.warningType = warningType;
	}

	public String getWarningObject() {
		return warningObject;
	}

	public void setWarningObject(String warningObject) {
		this.warningObject = warningObject;
	}

	public Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public Date getRevertTime() {
		return revertTime;
	}

	public void setRevertTime(Date revertTime) {
		this.revertTime = revertTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

}
