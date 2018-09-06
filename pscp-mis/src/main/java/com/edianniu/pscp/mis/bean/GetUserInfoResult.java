package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.user.UserInfo;

public class GetUserInfoResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserInfo memberInfo;
	
	private CompanyInfo companyInfo;
	
	private ElectricianInfo  electricianInfo;

	public UserInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(UserInfo memberInfo) {
		this.memberInfo = memberInfo;
	}


	public ElectricianInfo getElectricianInfo() {
		return electricianInfo;
	}

	public void setElectricianInfo(ElectricianInfo electricianInfo) {
		this.electricianInfo = electricianInfo;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	

	
	
}
