package com.edianniu.pscp.cs.bean.power;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业线路
 * @author zhoujianjian
 * @date 2017年12月23日 下午5:23:37
 */
public class CompanyLineInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 父线路ID   默认0，表示是父节点，其他表示是子节点
	private Long parentId;
	
	private String name;
	// 绑定类型 0：默认  1：楼宇  2：设备  3：楼层
	private Integer bindType;
	// 绑定Id
	private Long bindId;
	// 楼宇ID
	private Long buildingId;
	// 客户企业ID
	private Long companyId;
	// 仪表ID 主键
	private Long meterId;
	// 仪表编号
	private String meterNo;
	// 公司名称
	private String companyName;
	// 联系人
	private String contacts;
	// 手机号码
	private String mobile;
	// 楼宇名称
	private String buildingName;
	
	private Date createTime;
	
	private Date modifiedTime;
	
	private String createUser;
	
	private String modifiedUser;
	
	private Integer deleted;
	// 倍率
	private Integer multiple;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	public Long getBindId() {
		return bindId;
	}

	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
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

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	
	
	
}
