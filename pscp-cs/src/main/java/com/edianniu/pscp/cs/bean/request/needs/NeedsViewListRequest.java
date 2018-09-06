package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求列表  后台使用
 * @author zhoujianjian
 * 2017年9月21日下午11:38:42
 */
@JSONMessage(messageCode = 1002155)
public class NeedsViewListRequest extends TerminalRequest{

	//需求状态(后台)："not_audit"（未审核） "audit_succeed"（审核通过）   "audit_failed"（审核未通过）
	private String status;
	
	private Integer offset;
	//用户名
	private String memberName;
	//登入ID
	private String loginId; 
	//需求标题
	private String needsName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getNeedsName() {
		return needsName;
	}

	public void setNeedsName(String needsName) {
		this.needsName = needsName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
