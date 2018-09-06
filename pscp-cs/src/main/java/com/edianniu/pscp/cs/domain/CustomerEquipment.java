package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 配电房设备
 * @author zhoujianjian
 * 2017年9月28日下午3:37:16
 */
public class CustomerEquipment extends BaseDo implements Serializable{

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
	
	
	
	

}
