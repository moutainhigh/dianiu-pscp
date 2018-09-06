package com.edianniu.pscp.portal.entity;

/**
 * 企业采集点
 * @author zhoujianjian
 * @date 2017年12月19日 下午7:14:35
 */
public class CompanyMeterEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long companyId;
	
	private Long buildingId;

	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
