package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业设备
 * @author zhoujianjian
 * @date 2017年12月19日 下午3:47:21
 */
public class CompanyEquipmentInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	// 设备位置
	private String address;
	// 1:动力   2:照明     3:空调     4:特殊
	private Integer type;
	// 企业客户ID
	private Long companyId;
	// 与线路的绑定状态  未绑定0（默认）   已绑定1
	private Integer bindStatus;
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
	// 仪表编号
	private String meterId;
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
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
	
	
	
}
