package com.edianniu.pscp.mis.bean.response.company;

import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 2002008)
public class GetCompanyResponse extends BaseResponse{

	public CompanyInfo companyInfo;

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	

}
