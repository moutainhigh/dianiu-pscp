package com.edianniu.pscp.cs.bean.firefightingequipment;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 配电房消防设施保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:35:12
 */
public class SaveReqData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//用户ID
	private Long uid;
	
	private Long roomId;
	
	private String name;
	
	private String boxNumber;
	
	private String serialNumber;
	
	private String placementPosition;
	
	private String specifications;
	
	private Integer indoorOrOutdoor;
	
	private String productionDate;
	
	private String expiryDate;
	
	private Integer viewCycle;
	
	private Integer viewTimeUnit;
	
	private String initialTestDate;
	
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

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPlacementPosition() {
		return placementPosition;
	}

	public void setPlacementPosition(String placementPosition) {
		this.placementPosition = placementPosition;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public Integer getIndoorOrOutdoor() {
		return indoorOrOutdoor;
	}

	public void setIndoorOrOutdoor(Integer indoorOrOutdoor) {
		this.indoorOrOutdoor = indoorOrOutdoor;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getViewCycle() {
		return viewCycle;
	}

	public void setViewCycle(Integer viewCycle) {
		this.viewCycle = viewCycle;
	}

	public Integer getViewTimeUnit() {
		return viewTimeUnit;
	}

	public void setViewTimeUnit(Integer viewTimeUnit) {
		this.viewTimeUnit = viewTimeUnit;
	}

	public String getInitialTestDate() {
		return initialTestDate;
	}

	public void setInitialTestDate(String initialTestDate) {
		this.initialTestDate = initialTestDate;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
