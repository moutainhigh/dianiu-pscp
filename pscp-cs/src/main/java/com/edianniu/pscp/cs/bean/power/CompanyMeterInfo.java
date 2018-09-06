package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;
import java.util.Date;

/**
 * 采集点
 * @author zhoujianjian
 * @date 2017年12月27日 上午9:39:38
 */
public class CompanyMeterInfo implements Serializable{

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
	// 客户名称
	private String companyName;
	// 联系人
	private String contacts;
	// 手机号码
	private String mobile;
	// 仪表绑定的线路ID
	private Long lineId;
	// 仪表绑定的线路名称
	private String lineName;
	// 楼宇ID
	private Long buildingId;
	// 楼宇名称
	private String buildingName;
	// 设备名称
	private String equipmentName;
	// 设备地址
	private String equipmentAddress;
	// 设备类型
	private Integer equipmentType;
	
	private Date createTime;
	
	private Date modifiedTime;
	
	private String createUser;
	
	private String modifiedUser;
	
	private Integer deleted;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getEquipmentAddress() {
		return equipmentAddress;
	}

	public void setEquipmentAddress(String equipmentAddress) {
		this.equipmentAddress = equipmentAddress;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	
	

}
