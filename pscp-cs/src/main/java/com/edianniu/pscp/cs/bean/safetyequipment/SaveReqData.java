package com.edianniu.pscp.cs.bean.safetyequipment;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 配电房安全用具新增
 * @author zhoujianjian
 * @date 2017年10月31日 下午1:04:48
 */
public class SaveReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private Long roomId;
	
	private String name;
	
	private String serialNumber;
	
	private String modelNumber;
	
	private String voltageLevel;
	
	private String manufacturer;
	
	private Integer testCycle;
	
	private Integer testTimeUnit;
	
	private String initialTestDate;
	
	private String remark;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public String getInitialTestDate() {
		return initialTestDate;
	}

	public void setInitialTestDate(String initialTestDate) {
		this.initialTestDate = initialTestDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
