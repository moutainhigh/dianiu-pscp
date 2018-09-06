package com.edianniu.pscp.mis.bean.response.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;
/**
 * 获取电工邀请response
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 下午12:04:24 
 * @version V1.0
 */
@JSONMessage(messageCode = 2002070)
public final class GetElectricianInvitationResponse extends BaseResponse {
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
