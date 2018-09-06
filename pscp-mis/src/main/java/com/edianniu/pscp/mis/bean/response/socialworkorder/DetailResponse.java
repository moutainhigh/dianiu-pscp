package com.edianniu.pscp.mis.bean.response.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.company.CompanyDetail;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.workorder.SocialWorkOrderDetail;

@JSONMessage(messageCode = 2002015)
public final class DetailResponse extends BaseResponse {
	
	private SocialWorkOrderDetail orderDetail;
	
    private CompanyDetail companyInfo;
    
	public CompanyDetail getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyDetail companyInfo) {
		this.companyInfo = companyInfo;
	}
	public SocialWorkOrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(SocialWorkOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
