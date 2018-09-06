package com.edianniu.pscp.mis.bean.company;

import java.io.Serializable;

public class CompanySaveOrAuthReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private String actionType;//save/auth
	private Integer memberType=CompanyMemberType.FACILITATOR.getValue();//1 服务商，2客户
	private CompanyInfo companyInfo;
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	
	
}
