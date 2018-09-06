package com.edianniu.pscp.mis.bean.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;

public class GetElectricianInvitationResult extends Result{
	private static final long serialVersionUID = 1L;
	private Long invitationId;
	private Integer invitationStatus;
	private boolean admin=false;
	public Long getInvitationId() {
		return invitationId;
	}
	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	public Integer getInvitationStatus() {
		return invitationStatus;
	}
	public void setInvitationStatus(Integer invitationStatus) {
		this.invitationStatus = invitationStatus;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
