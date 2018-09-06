package com.edianniu.pscp.sps.bean.user;

import java.io.Serializable;
import java.util.Date;

public class UserAuditInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//User类中的属性
		private Long id;
		private String mobile;
		private Integer sex;
		private String sexName;
		private Integer status;
		//会员类型
		private Integer userType;
		private Long companyId;
		
		
		//UserAuth类中的属性
		private String username;
		private Long authId;
		private String idCardNo;
		private Integer authStatus;
		private String nation;
		private Date birthDate;
		private Date idCardStartDate;
		private Date idCardExpDate;
		private String idCardAddress;
		private String driverCardNo;
		private Integer driverCardType;
		private Date driverCardExpDate;
		private Date driverCardStartDate;
		private String birthDates;
		private String driverCardAdress;
		private String companyName;
		private Float amount;
		private Float freezingAmount;
		private Long walleId;
		private String cardNo;
		private String idCardPic1;
		private String driverCardPic1;
		private String memo;
		private Date createTime;
		private Date authCreateTime;
		private Integer cardType;		
		private Long orgId;		
		private Integer companyType;
	
		//审核权限
		private Integer approvalRight;
		private String opUser;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public Integer getSex() {
			return sex;
		}

		public void setSex(Integer sex) {
			this.sex = sex;
		}

		public String getSexName() {
			return sexName;
		}

		public void setSexName(String sexName) {
			this.sexName = sexName;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Integer getUserType() {
			return userType;
		}

		public void setUserType(Integer userType) {
			this.userType = userType;
		}

		public Long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Long getAuthId() {
			return authId;
		}

		public void setAuthId(Long authId) {
			this.authId = authId;
		}

		public String getIdCardNo() {
			return idCardNo;
		}

		public void setIdCardNo(String idCardNo) {
			this.idCardNo = idCardNo;
		}

		public Integer getAuthStatus() {
			return authStatus;
		}

		public void setAuthStatus(Integer authStatus) {
			this.authStatus = authStatus;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}

		public Date getIdCardStartDate() {
			return idCardStartDate;
		}

		public void setIdCardStartDate(Date idCardStartDate) {
			this.idCardStartDate = idCardStartDate;
		}

		public Date getIdCardExpDate() {
			return idCardExpDate;
		}

		public void setIdCardExpDate(Date idCardExpDate) {
			this.idCardExpDate = idCardExpDate;
		}

		public String getDriverCardNo() {
			return driverCardNo;
		}

		public void setDriverCardNo(String driverCardNo) {
			this.driverCardNo = driverCardNo;
		}

		public Integer getDriverCardType() {
			return driverCardType;
		}

		public void setDriverCardType(Integer driverCardType) {
			this.driverCardType = driverCardType;
		}

		public Date getDriverCardExpDate() {
			return driverCardExpDate;
		}

		public void setDriverCardExpDate(Date driverCardExpDate) {
			this.driverCardExpDate = driverCardExpDate;
		}

		public Date getDriverCardStartDate() {
			return driverCardStartDate;
		}

		public void setDriverCardStartDate(Date driverCardStartDate) {
			this.driverCardStartDate = driverCardStartDate;
		}

		public String getBirthDates() {
			return birthDates;
		}

		public void setBirthDates(String birthDates) {
			this.birthDates = birthDates;
		}

		public String getDriverCardAdress() {
			return driverCardAdress;
		}

		public void setDriverCardAdress(String driverCardAdress) {
			this.driverCardAdress = driverCardAdress;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}

		public Float getFreezingAmount() {
			return freezingAmount;
		}

		public void setFreezingAmount(Float freezingAmount) {
			this.freezingAmount = freezingAmount;
		}

		public Long getWalleId() {
			return walleId;
		}

		public void setWalleId(Long walleId) {
			this.walleId = walleId;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getIdCardPic1() {
			return idCardPic1;
		}

		public void setIdCardPic1(String idCardPic1) {
			this.idCardPic1 = idCardPic1;
		}

		public String getDriverCardPic1() {
			return driverCardPic1;
		}

		public void setDriverCardPic1(String driverCardPic1) {
			this.driverCardPic1 = driverCardPic1;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getAuthCreateTime() {
			return authCreateTime;
		}

		public void setAuthCreateTime(Date authCreateTime) {
			this.authCreateTime = authCreateTime;
		}

		public Integer getCardType() {
			return cardType;
		}

		public void setCardType(Integer cardType) {
			this.cardType = cardType;
		}

		public Long getOrgId() {
			return orgId;
		}

		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}	
		public Integer getCompanyType() {
			return companyType;
		}

		public void setCompanyType(Integer companyType) {
			this.companyType = companyType;
		}

		public Integer getApprovalRight() {
			return approvalRight;
		}

		public void setApprovalRight(Integer approvalRight) {
			this.approvalRight = approvalRight;
		}

		public String getIdCardAddress() {
			return idCardAddress;
		}

		public void setIdCardAddress(String idCardAddress) {
			this.idCardAddress = idCardAddress;
		}

		public String getOpUser() {
			return opUser;
		}

		public void setOpUser(String opUser) {
			this.opUser = opUser;
		}
		
		
}
