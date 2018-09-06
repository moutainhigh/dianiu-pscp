package com.edianniu.pscp.cs.bean.safetyequipment.vo;

import java.io.Serializable;

public class SafetyEquipmentVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String nextTestDate;
	
	private String roomName;
	
	private Long roomId;
	
	private String serialNumber;
	
	private String modelNumber;
	
	private String voltageLevel;
	
	private String manufacturer;
	
	private Integer testCycle;
	
	private Integer testTimeUnit;
	
	private String initialTestDate;
	
	private String lastTestDate;
	
	private String remark;
	
	private Integer clickStatus;

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

	public String getInitialTestDate() {
		return initialTestDate;
	}

	public void setInitialTestDate(String initialTestDate) {
		this.initialTestDate = initialTestDate;
	}

	public String getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getClickStatus() {
		return clickStatus;
	}

	public void setClickStatus(Integer clickStatus) {
		this.clickStatus = clickStatus;
	}

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

	public String getNextTestDate() {
		return nextTestDate;
	}

	public void setNextTestDate(String nextTestDate) {
		this.nextTestDate = nextTestDate;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	
	
	
}
