package com.edianniu.pscp.mis.bean.response.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.user.UserInfo;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002007)
public final class GetUserResponse extends BaseResponse {
	private UserInfo memberInfo;

	private CompanyInfo companyInfo;

	private ElectricianInfo electricianInfo;

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
