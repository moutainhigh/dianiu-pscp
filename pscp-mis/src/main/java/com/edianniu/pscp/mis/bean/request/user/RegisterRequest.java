package com.edianniu.pscp.mis.bean.request.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 1002002)
public final class RegisterRequest extends TerminalRequest {
	private String mobile;
	private String msgcodeid;
	private String msgcode;
	private String passwd;

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsgcodeid() {
		return this.msgcodeid;
	}

	public void setMsgcodeid(String msgcodeid) {
		this.msgcodeid = msgcodeid;
	}

	public String getMsgcode() {
		return this.msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
