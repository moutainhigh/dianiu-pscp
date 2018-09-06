/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:01:31 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:01:31 
 * @version V1.0
 */
public class ConfirmCompanyInvitationReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long invitationId;//邀请ID
	private String actionType;//agree:同意reject:拒绝
	public Long getInvitationId() {
		return invitationId;
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
	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
