package com.edianniu.pscp.cs.bean.equipment.vo;

import java.io.Serializable;

public class EquipmentVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long roomId;
	
	private String roomName;
	
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
	
	private String productionDate;
	
	private String serialNumber;
	
	private String manufacturer;

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

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	

}
