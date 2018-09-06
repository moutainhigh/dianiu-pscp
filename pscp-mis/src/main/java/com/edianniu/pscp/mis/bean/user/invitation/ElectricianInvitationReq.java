/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:02:43 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:02:43 
 * @version V1.0
 */
public class ElectricianInvitationReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long uid;
	private String mobile;//Y手机号码
	private String userName;//N电工姓名
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMobile() {
		return mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
