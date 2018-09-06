package com.edianniu.pscp.mis.query;

import java.util.List;

import com.edianniu.pscp.mis.commons.BaseQuery;

public class CarQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	private Double latitude;
	private Double longitude;
	private int offset; 	 
	private int status;
	private Long uid;
	private Long stationId;
	private Long companyId;
	List<Long> stationIds;
	List<Long> carIds;
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public int getOffset() {
		return offset;
	}
	public int getStatus() {
		return status;
	}
	public Long getUid() {
		return uid;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public List<Long> getStationIds() {
		return stationIds;
	}
	public List<Long> getCarIds() {
		return carIds;
	}
	public void setStationIds(List<Long> stationIds) {
		this.stationIds = stationIds;
	}
	public void setCarIds(List<Long> carIds) {
		this.carIds = carIds;
	}
}
