package com.edianniu.pscp.sps.bean.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;


@JSONMessage(messageCode = 1002114)
public class CheckAppUpdateRequest extends TerminalRequest {
	private String appPkg;
	private Long appVer;
	private Long uid;
	private String token;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getAppPkg() {
		return appPkg;
	}

	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}

	public Long getAppVer() {
		return appVer;
	}
	public void setAppVer(Long appVer) {
		this.appVer = appVer;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
