package com.edianniu.pscp.cs.bean.power.vo;

import java.io.Serializable;

public class WarningVO implements Serializable{

	private static final long serialVersionUID = 1L;
	// 告警ID
	private Long id;
	// 客户companyId
	private Long companyId;
	// 客户companyName
	private String companyName;
	// 告警类型
	private String type;
	// 告警对象
	private String object;
	// 发生时间
	private String occurTime;
	// 恢复时间（如未恢复，返回“未恢复”）
	private String revertTime;
	// 持续时长    单位：小时
	private String continueTime;
	// 	告警描述
	private String description;
	// 0:未处理（默认）1:已处理
	private Integer dealStatus;
	// 0:未读（默认）>0:已读
	private Integer readStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getRevertTime() {
		return revertTime;
	}
	public void setRevertTime(String revertTime) {
		this.revertTime = revertTime;
	}
	public String getContinueTime() {
		return continueTime;
	}
	public void setContinueTime(String continueTime) {
		this.continueTime = continueTime;
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
