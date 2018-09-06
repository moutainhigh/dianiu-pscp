package com.edianniu.pscp.mis.domain;

/**
 * 租客仪表信息
 * @author zhoujianjian
 * @date 2018年3月28日 下午6:40:14
 */
public class RenterMeter extends BaseDo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long renterId;
	
	private Long buildingId;
	
	private String buildingName;
	
	private String address;
	
	private Long meterId;
	
	private Double rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRenterId() {
		return renterId;
	}

	public void setRenterId(Long renterId) {
		this.renterId = renterId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	
	
}
