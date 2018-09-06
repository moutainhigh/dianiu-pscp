/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月11日 下午5:46:47 
 * @version V1.0
 */
package com.edianniu.pscp.message.msg.domain;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月11日 下午5:46:47 
 * @version V1.0
 */
public class MessageExt implements Serializable {
	private static final long serialVersionUID = 1L;
    private String actionType;
    private String invitationId;
	public String getActionType() {
		return actionType;
	}
	public String getInvitationId() {
		return invitationId;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}
}
