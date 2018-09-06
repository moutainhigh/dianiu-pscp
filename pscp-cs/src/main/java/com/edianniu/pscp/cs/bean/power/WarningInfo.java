package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户设备告警
 * @author zhoujianjian
 * @date 2017年12月28日 上午11:50:21
 */
public class WarningInfo implements Serializable{

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
	
	private Date createTime;
	
	private Date modifiedTime;
	
	private String createUser;
	
	private String modifiedUser;
	
	private Integer deleted;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
