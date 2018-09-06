package com.edianniu.pscp.mis.bean.company;

import com.edianniu.pscp.mis.bean.Result;

public class GetCompanyInfoResult extends Result {
	private static final long serialVersionUID = 1L;
	private CompanyInfo companyInfo;

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

}
