package com.edianniu.pscp.cs.bean.needs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.Result;
import com.edianniu.pscp.mis.bean.company.CompanyInfo;

/**
 * 服务商资质下详情
 * @author zhoujianjian
 * 2017年9月18日下午9:10:21
 */
public class RespondDetailsResult extends Result{
	
	private static final long serialVersionUID = 1L;
	
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
