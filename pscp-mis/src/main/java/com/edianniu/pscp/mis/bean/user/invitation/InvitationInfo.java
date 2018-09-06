/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月11日 上午10:55:41 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月11日 上午10:55:41 
 * @version V1.0
 */
public class InvitationInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long invitationId;
	private String mobile;
	private String name;
	private Integer type;//1/2
	public Long getInvitationId() {
		return invitationId;
	}
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public Integer getType() {
		return type;
	}
	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
