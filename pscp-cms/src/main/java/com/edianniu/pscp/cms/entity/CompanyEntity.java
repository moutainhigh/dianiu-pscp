package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;



/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
public class CompanyEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String identifier;
	
	private Long memberId;
	
	private Integer memberType;//1服务商，2客户
	
	private String loginName;
	
	private String memberMobile;
	
	private String userName;
	
	private String idCardNo;
	
	private String idCardFrontImg;
	
	private String idCardBackImg;
	
	private String name;
	
	private String organizationCodeImg;
	
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
	
	private Integer status;
	
	private String remark;
	//审核时间
	private Date auditTime;
	//审核用户
	private String auditUser;
	
	private String areaName;
	
	private String cityName;
	
	private String provinceName;
	/**
	 * 是否认证成功
	 * @return
	 */
	public boolean isAuthSuccess(){
		if(getStatus()==null){
			return false;
		}
		if(getStatus()==CompanyAuthStatus.SUCCESS.getValue()){
		  return true;	
		}
		return false;
	}
	/*
	 * 电工证图片列表
	 */
	private List<ImageDo> enterpriseQualificationImgList=new ArrayList<>();
		
	private ImageDo idCardFrontImgDo=new ImageDo();
	private ImageDo idCardBackImgDo=new ImageDo();
	private ImageDo organizationCodeImgDo=new ImageDo();
	private ImageDo applicationLetterFidImgDo=new ImageDo();
	private ImageDo businessLicenceImgDo=new ImageDo();

	public List<ImageDo> getEnterpriseQualificationImgList() {
		return enterpriseQualificationImgList;
	}
	public void setEnterpriseQualificationImgList(List<ImageDo> enterpriseQualificationImgList) {
		this.enterpriseQualificationImgList = enterpriseQualificationImgList;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdCardNo() {
		return idCardNo;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdCardBackImg(String idCardBackImg) {
		this.idCardBackImg = idCardBackImg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdCardBackImg() {
		return idCardBackImg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getLegalPerson() {
		return legalPerson;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBusinessLicence() {
		return businessLicence;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBusinessLicenceImg(String businessLicenceImg) {
		this.businessLicenceImg = businessLicenceImg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBusinessLicenceImg() {
		return businessLicenceImg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getContacts() {
		return contacts;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getProvinceId() {
		return provinceId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getCityId() {
		return cityId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getAreaId() {
		return areaId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setApplicationLetterFid(String applicationLetterFid) {
		this.applicationLetterFid = applicationLetterFid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getApplicationLetterFid() {
		return applicationLetterFid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getRemark() {
		return remark;
	}
	public ImageDo getIdCardFrontImgDo() {
		return idCardFrontImgDo;
	}
	public void setIdCardFrontImgDo(ImageDo idCardFrontImgDo) {
		this.idCardFrontImgDo = idCardFrontImgDo;
	}
	public ImageDo getIdCardBackImgDo() {
		return idCardBackImgDo;
	}
	public void setIdCardBackImgDo(ImageDo idCardBackImgDo) {
		this.idCardBackImgDo = idCardBackImgDo;
	}
	public String getOrganizationCodeImg() {
		return organizationCodeImg;
	}

	public void setOrganizationCodeImg(String organizationCodeImg) {
		this.organizationCodeImg = organizationCodeImg;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public ImageDo getOrganizationCodeImgDo() {
		return organizationCodeImgDo;
	}
	public void setOrganizationCodeImgDo(ImageDo organizationCodeImgDo) {
		this.organizationCodeImgDo = organizationCodeImgDo;
	}
	public ImageDo getApplicationLetterFidImgDo() {
		return applicationLetterFidImgDo;
	}
	public void setApplicationLetterFidImgDo(ImageDo applicationLetterFidImgDo) {
		this.applicationLetterFidImgDo = applicationLetterFidImgDo;
	}
	public ImageDo getBusinessLicenceImgDo() {
		return businessLicenceImgDo;
	}
	public void setBusinessLicenceImgDo(ImageDo businessLicenceImgDo) {
		this.businessLicenceImgDo = businessLicenceImgDo;
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
	public String getLoginName() {
		return loginName;
	}
	public String getMemberMobile() {
		return memberMobile;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	
}
