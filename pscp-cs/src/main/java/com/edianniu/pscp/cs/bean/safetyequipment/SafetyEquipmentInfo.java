package com.edianniu.pscp.cs.bean.safetyequipment;

import java.io.Serializable;
import java.util.Date;

/**
 * 配电房安全用具
 * @author zhoujianjian
 * @date 2017年10月31日 上午11:59:29
 */
public class SafetyEquipmentInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long roomId;
	
	private Long companyId;
	
	private String name;
	
	private String serialNumber;
	
	private String modelNumber;
	
	private String voltageLevel;
	
	private String manufacturer;
	
	private Integer testCycle;
	
	private Integer testTimeUnit;
	
	private Integer waringStatus;
	
	private Date initialTestDate;
	
	private Date nextTestDate;
	
	private String remark;
	
	private Date createTime;
	
	private String createUser;
	
	private Date modifiedTime;
	
	private String modifiedUser;
	
	private Integer deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getVoltageLevel() {
		return voltageLevel;
	}

	public void setVoltageLevel(String voltageLevel) {
		this.voltageLevel = voltageLevel;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getTestCycle() {
		return testCycle;
	}

	public void setTestCycle(Integer testCycle) {
		this.testCycle = testCycle;
	}

	public Integer getTestTimeUnit() {
		return testTimeUnit;
	}

	public void setTestTimeUnit(Integer testTimeUnit) {
		this.testTimeUnit = testTimeUnit;
	}

	public Integer getWaringStatus() {
		return waringStatus;
	}

	public void setWaringStatus(Integer waringStatus) {
		this.waringStatus = waringStatus;
	}

	public Date getInitialTestDate() {
		return initialTestDate;
	}

	public void setInitialTestDate(Date initialTestDate) {
		this.initialTestDate = initialTestDate;
	}

	public Date getNextTestDate() {
		return nextTestDate;
	}

	public void setNextTestDate(Date nextTestDate) {
		this.nextTestDate = nextTestDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
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
