package com.edianniu.pscp.cms.entity;

public class CompanyLineEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	// 父线路ID   默认0，表示是父节点，其他表示是子节点
	private Long parentId;
	// 线路名称
	private String name;
	// 绑定类型 0：主线   1：楼宇  2：设备  3：楼层
	private Integer bindType;
	// 绑定Id
	private Long bindId;
	// 楼宇ID
	private Long buildingId;
	// 客户企业ID
	private Long companyId;
	// 仪表ID
	private Long meterId;
	// 公司名称
	private String companyName;
	// 联系人
	private String contacts;
	// 手机号码
	private String mobile;
	// 楼宇名称
	private String buildingName;
	// 父线路名称
	private String parentName;
	// 绑定类型名称
	private String bindTypeName;
	// 绑定设备、楼宇名称
	private String bindName;
	// 采集点名称
	private String meterName;
	// 是否与配电房关联     1:关联(默认)   0:不关联
	private Integer isReferRoom;
	
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
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
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getBindTypeName() {
		return bindTypeName;
	}
	public void setBindTypeName(String bindTypeName) {
		this.bindTypeName = bindTypeName;
	}
	public String getBindName() {
		return bindName;
	}
	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	public String getMeterName() {
		return meterName;
	}
	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}
	public Integer getIsReferRoom() {
		return isReferRoom;
	}
	public void setIsReferRoom(Integer isReferRoom) {
		this.isReferRoom = isReferRoom;
	}
}
