/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 设置开关闸操作密码
 * @author zhoujianjian
 * @date 2018年4月12日 下午4:54:37
 */
@JSONMessage(messageCode = 1002199)
public final class SetSwitchPwdRequest extends TerminalRequest {
	private Long uid;
	private String token;
	private String pwd;
	private String rePwd;
	private String msgcodeid;
	private String msgcode;

	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRePwd() {
		return rePwd;
	}

	public void setRePwd(String rePwd) {
		this.rePwd = rePwd;
	}

	public String getMsgcodeid() {
		return msgcodeid;
	}

	public void setMsgcodeid(String msgcodeid) {
		this.msgcodeid = msgcodeid;
	}

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
