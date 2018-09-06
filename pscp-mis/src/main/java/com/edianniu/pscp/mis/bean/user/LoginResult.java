package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;

public class LoginResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserInfo userInfo;
	private CompanyInfo companyInfo;
	private ElectricianInfo  electricianInfo;


	private String token;

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
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
