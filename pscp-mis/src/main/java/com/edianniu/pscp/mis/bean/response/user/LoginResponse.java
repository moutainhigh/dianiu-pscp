package com.edianniu.pscp.mis.bean.response.user;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.user.UserInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002001)
public final class LoginResponse extends BaseResponse {
	private String token;
	private UserInfo memberInfo;
	
	private CompanyInfo companyInfo;
	
	private ElectricianInfo  electricianInfo;

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

	

	public UserInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(UserInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public ElectricianInfo getElectricianInfo() {
		return electricianInfo;
	}

	public void setElectricianInfo(ElectricianInfo electricianInfo) {
		this.electricianInfo = electricianInfo;
	}

	
	
}
