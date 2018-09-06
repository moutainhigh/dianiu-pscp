package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.company.CompanyAuthStatus;
import com.edianniu.pscp.mis.bean.electrician.ElectricianAuthStatus;

public class MutiUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private String nickName;
    private String loginName;
    private String mobile;
    private Integer status;

    private Integer isCustomer;
    private Integer isFacilitator;
    private Integer isElectrician;
    private Integer isAdmin;
    private Long companyId;
    /**
     * 用户真实姓名
     */
    private String realName;
    private Integer electricianAuthStatus=ElectricianAuthStatus.NOTAUTH.getValue();//电工认证状态
    private Integer companyMemberType=0;//企业类型 1 服务商，2客户
    private Integer companyAuthStatus=CompanyAuthStatus.NOTAUTH.getValue();//企业认证状态
   

    public Long getUid() {
		return uid;
	}


	public String getNickName() {
		return nickName;
	}


	public String getLoginName() {
		return loginName;
	}


	public String getMobile() {
		return mobile;
	}


	public Integer getStatus() {
		return status;
	}


	public Integer getIsCustomer() {
		return isCustomer;
	}


	public Integer getIsFacilitator() {
		return isFacilitator;
	}


	public Integer getIsElectrician() {
		return isElectrician;
	}


	public Integer getIsAdmin() {
		return isAdmin;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public String getRealName() {
		return realName;
	}


	public Integer getElectricianAuthStatus() {
		return electricianAuthStatus;
	}


	public Integer getCompanyMemberType() {
		return companyMemberType;
	}


	public Integer getCompanyAuthStatus() {
		return companyAuthStatus;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
	}


	public void setIsFacilitator(Integer isFacilitator) {
		this.isFacilitator = isFacilitator;
	}


	public void setIsElectrician(Integer isElectrician) {
		this.isElectrician = isElectrician;
	}


	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public void setElectricianAuthStatus(Integer electricianAuthStatus) {
		this.electricianAuthStatus = electricianAuthStatus;
	}


	public void setCompanyMemberType(Integer companyMemberType) {
		this.companyMemberType = companyMemberType;
	}


	public void setCompanyAuthStatus(Integer companyAuthStatus) {
		this.companyAuthStatus = companyAuthStatus;
	}


	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }

}
