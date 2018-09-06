package com.edianniu.pscp.cs.bean.equipment;

import java.io.Serializable;
import java.util.Date;

public class EquipmentInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long roomId;
	
	private Long companyId;
	
	private String name;
	
	private String model;
	
	private String ratedVoltage;
	
	private String ratedCurrent;
	
	private String ratedBreakingCurrent;
	
	private String ratedCapacity;
	
	private String maxWorkingVoltage;
	
	private String currentTransformerRatio;
	
	private String voltageTransformerRatio;
	
	private Date productionDate;
	
	private String serialNumber;
	
	private String manufacturer;
	
	private Date createTime;
	
	private String createUser;
	
	private Date modifiedTime;

	private String modifiedUser;
	
	private int deleted;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(String ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public String getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}

	public String getRatedBreakingCurrent() {
		return ratedBreakingCurrent;
	}

	public void setRatedBreakingCurrent(String ratedBreakingCurrent) {
		this.ratedBreakingCurrent = ratedBreakingCurrent;
	}

	public String getRatedCapacity() {
		return ratedCapacity;
	}

	public void setRatedCapacity(String ratedCapacity) {
		this.ratedCapacity = ratedCapacity;
	}

	public String getMaxWorkingVoltage() {
		return maxWorkingVoltage;
	}

	public void setMaxWorkingVoltage(String maxWorkingVoltage) {
		this.maxWorkingVoltage = maxWorkingVoltage;
	}

	public String getCurrentTransformerRatio() {
		return currentTransformerRatio;
	}

	public void setCurrentTransformerRatio(String currentTransformerRatio) {
		this.currentTransformerRatio = currentTransformerRatio;
	}

	public String getVoltageTransformerRatio() {
		return voltageTransformerRatio;
	}

	public void setVoltageTransformerRatio(String voltageTransformerRatio) {
		this.voltageTransformerRatio = voltageTransformerRatio;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	
}
