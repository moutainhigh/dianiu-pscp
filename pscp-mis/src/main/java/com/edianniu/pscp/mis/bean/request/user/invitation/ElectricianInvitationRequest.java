/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user.invitation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 电工邀请request
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月22日 上午11:57:19 
 * @version V1.0
 */
@JSONMessage(messageCode = 1002063)
public final class ElectricianInvitationRequest extends TerminalRequest {
	private String mobile;//Y手机号码
	private String userName;//N电工姓名
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
