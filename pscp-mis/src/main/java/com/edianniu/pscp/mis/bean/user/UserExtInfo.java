package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.electrician.ElectricianInfo;

public class UserExtInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private ElectricianInfo electricianInfo;
    private CompanyInfo companyInfo;
	public ElectricianInfo getElectricianInfo() {
		return electricianInfo;
	}
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setElectricianInfo(ElectricianInfo electricianInfo) {
		this.electricianInfo = electricianInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
}
