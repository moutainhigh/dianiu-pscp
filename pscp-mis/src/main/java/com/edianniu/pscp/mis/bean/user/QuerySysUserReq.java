package com.edianniu.pscp.mis.bean.user;

import java.io.Serializable;

public class QuerySysUserReq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer needsAuditNotice;
	
	private Integer memberAuditNotice;
	
	private Integer offLineNotice;
	
	private Integer status;

	public Integer getNeedsAuditNotice() {
		return needsAuditNotice;
	}

	public void setNeedsAuditNotice(Integer needsAuditNotice) {
		this.needsAuditNotice = needsAuditNotice;
	}

	public Integer getMemberAuditNotice() {
		return memberAuditNotice;
	}

	public void setMemberAuditNotice(Integer memberAuditNotice) {
		this.memberAuditNotice = memberAuditNotice;
	}

	public Integer getOffLineNotice() {
		return offLineNotice;
	}

	public void setOffLineNotice(Integer offLineNotice) {
		this.offLineNotice = offLineNotice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
