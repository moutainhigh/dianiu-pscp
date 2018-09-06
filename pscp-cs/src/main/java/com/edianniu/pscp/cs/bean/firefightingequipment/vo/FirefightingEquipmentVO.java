package com.edianniu.pscp.cs.bean.firefightingequipment.vo;

import java.io.Serializable;

/**
 * 消防设施清单
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:54:19
 */
public class FirefightingEquipmentVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String roomName;
	
	private Long roomId;
	
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
	
	private String lastTestDate;
	
	private String nextTestDate;
	
	private Integer clickStatus;
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public String getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
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
	
	

}
