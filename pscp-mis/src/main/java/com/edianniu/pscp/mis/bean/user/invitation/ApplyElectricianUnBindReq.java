/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:01:56 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:01:56 
 * @version V1.0
 */
public class ApplyElectricianUnBindReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long invitationId;
	private String appType;
	public Long getUid() {
		return uid;
	}
	public Long getInvitationId() {
		return invitationId;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}

}
