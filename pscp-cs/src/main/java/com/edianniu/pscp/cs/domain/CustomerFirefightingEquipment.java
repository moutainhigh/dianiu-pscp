package com.edianniu.pscp.cs.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 配电房消防设施
 * @author zhoujianjian
 * @date 2017年11月1日 下午4:13:27
 */
public class CustomerFirefightingEquipment extends BaseDo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long roomId;
	
	private Long companyId;
	
	private String name;
	
	private String boxNumber;
	
	private String serialNumber;
	
	private String placementPosition;
	
	private String specifications;
	
	private Integer indoorOrOutdoor;
	
	private Date productionDate;
	
	private Date expiryDate;
	
	private Integer viewCycle;
	
	private Integer viewTimeUnit;
	
	private Integer waringStatus;
	
	private Date initialTestDate;
	
	private Date nextTestDate;

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

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
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

}
