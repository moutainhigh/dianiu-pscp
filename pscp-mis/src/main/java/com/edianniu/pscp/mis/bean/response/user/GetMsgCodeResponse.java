package com.edianniu.pscp.mis.bean.response.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.response.BaseResponse;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2001003)
public class GetMsgCodeResponse extends BaseResponse {
	private String msgcodeid;

	public String getMsgcodeid() {
		return this.msgcodeid;
	}

	public void setMsgcodeid(String msgcodeid) {
		this.msgcodeid = msgcodeid;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
