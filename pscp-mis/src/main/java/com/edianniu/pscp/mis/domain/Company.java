package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;

public class Company extends BaseDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String identifier;
	private Long memberId;
	private Integer memberType;
	private String userName;
	private String idCardNo;
	private String idCardFrontImg;
	private String idCardBackImg;
	private String name;
	private Integer type;
	private String legalPerson;
	private String businessLicence;
	private String businessLicenceImg;
	private String contacts;
	private String mobile;
	private String email;
	private String phone;
	private Long provinceId;
	private Long cityId;
	private Long areaId;
	private String address;
	private String website;
	private String applicationLetterFid;
	
	private String organizationCodeImg;
	
	private Integer status;
	private String remark;	
	private Date auditTime;
	private String auditUser;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}
	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}
	public String getIdCardBackImg() {
		return idCardBackImg;
	}
	public void setIdCardBackImg(String idCardBackImg) {
		this.idCardBackImg = idCardBackImg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getBusinessLicence() {
		return businessLicence;
	}
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	public String getBusinessLicenceImg() {
		return businessLicenceImg;
	}
	public void setBusinessLicenceImg(String businessLicenceImg) {
		this.businessLicenceImg = businessLicenceImg;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getApplicationLetterFid() {
		return applicationLetterFid;
	}
	public void setApplicationLetterFid(String applicationLetterFid) {
		this.applicationLetterFid = applicationLetterFid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getAuditTime() {
		return auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getOrganizationCodeImg() {
		return organizationCodeImg;
	}
	public void setOrganizationCodeImg(String organizationCodeImg) {
		this.organizationCodeImg = organizationCodeImg;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	

}
