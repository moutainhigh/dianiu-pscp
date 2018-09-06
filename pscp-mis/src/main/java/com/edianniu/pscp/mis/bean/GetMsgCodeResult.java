package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

public class GetMsgCodeResult extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msgCode;
	private String msgCodeId;

	public String getMsgCode() {
		return this.msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgCodeId() {
		return this.msgCodeId;
	}

	public void setMsgCodeId(String msgCodeId) {
		this.msgCodeId = msgCodeId;
	}
}
