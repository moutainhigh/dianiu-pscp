package com.edianniu.pscp.cms.entity;

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
	// 采集点编号   即采集网关设备所对应的仪表ID
	private String meterId;
	// 仪表状态   0:下线(默认)  1:上线   2:其他状态
	private Integer status;
	// 与线路的绑定状态状态   0:未绑定(默认)  1:绑定
	private Integer bindStatus;
	// 倍率
	private Integer multiple;
	// 客户名称
	private String companyName;
	// 联系人
	private String contacts;
	// 手机号码
	private String mobile;
	// 线路名称
	private String lineName;
	// 楼宇名称
	private String buildingName;
	// 设备名称
	private String equipmentName;
	
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
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	
	
}
