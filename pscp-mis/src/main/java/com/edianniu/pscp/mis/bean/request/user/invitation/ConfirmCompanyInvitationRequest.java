/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 同意/拒绝企业邀请接口request
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1002069)
public final class ConfirmCompanyInvitationRequest extends TerminalRequest {
	private Long invitationId;//邀请ID
	private String actionType;//agree:同意reject:拒绝
	public Long getInvitationId() {
		return invitationId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
