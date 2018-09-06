package com.edianniu.pscp.cs.bean.response.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.needs.NeedsOrderInfo;
import com.edianniu.pscp.cs.bean.response.BaseResponse;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 服务商资质详情
 * @author zhoujianjian
 * 2017年9月18日下午4:34:46
 */
@JSONMessage(messageCode = 2002130)
public class RespondDetailsResponse extends BaseResponse{
	
	private CompanyInfo companyInfo;
	
	private NeedsOrderInfo needsOrderInfo;

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public NeedsOrderInfo getNeedsOrderInfo() {
		return needsOrderInfo;
	}

	public void setNeedsOrderInfo(NeedsOrderInfo needsOrderInfo) {
		this.needsOrderInfo = needsOrderInfo;
	}

	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
}
