package com.edianniu.pscp.cs.bean.needs.vo;

import java.io.Serializable;

/**
 * 需求消息  后台显示
 * @author zhoujianjian
 * 2017年9月21日下午6:22:15
 */
public class NeedsViewVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String memberName;
	
	private String loginId;
	//需求编号
	private String orderId;
	
	private String needsName;
	
	private String publishTime;
	
	private String publishCutoffTime;
	//数据库需求状态
	private Integer needsStatus;
	//后台需求显示状态  not_audit：未审核      audit_succeed：审核通过       audit_failed：审核失败
	private String needsShowStatus;
	
	private String failReason;

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Integer getNeedsStatus() {
		return needsStatus;
	}

	public void setNeedsStatus(Integer needsStatus) {
		this.needsStatus = needsStatus;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNeedsName() {
		return needsName;
	}

	public void setNeedsName(String needsName) {
		this.needsName = needsName;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishCutoffTime() {
		return publishCutoffTime;
	}

	public void setPublishCutoffTime(String publishCutoffTime) {
		this.publishCutoffTime = publishCutoffTime;
	}

	public String getNeedsShowStatus() {
		return needsShowStatus;
	}

	public void setNeedsShowStatus(String needsShowStatus) {
		this.needsShowStatus = needsShowStatus;
	}

	
}
