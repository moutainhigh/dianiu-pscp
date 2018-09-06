package com.edianniu.pscp.cs.bean.request.equipment;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 配电房设备新增和编辑
 * @author zhoujianjian
 * 2017年9月28日下午3:43:22
 */
@JSONMessage(messageCode = 1002146)
public class SaveRequest extends TerminalRequest{
	
	private Long roomId;
	//设备主键（新增时为空，编辑时不为空）
	private Long id;
	
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
