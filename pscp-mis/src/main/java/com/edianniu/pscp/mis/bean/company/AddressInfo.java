package com.edianniu.pscp.mis.bean.company;

import java.io.Serializable;

public class AddressInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long provinceId;
	private String provinceName;
	private Long cityId;
	private String cityName;
	private Long areaId;
	private String areaName;
	private String address;
	public Long getProvinceId() {
		return provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public Long getCityId() {
		return cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public Long getAreaId() {
		return areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public String getAddress() {
		return address;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
