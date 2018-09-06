package com.edianniu.pscp.mis.bean.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

@JSONMessage(messageCode = 2002049)
public class CheckAppUpdateResponse extends BaseResponse {
	private Long lastAppVer;
	private String lastAPPShowVer;
	private String updateDesc;
	private Integer updateType;
	private String updateUrl;
	private String updateMd5;
	private String lastAppSize;

	public Long getLastAppVer() {
		return lastAppVer;
	}

	public void setLastAppVer(Long lastAppVer) {
		this.lastAppVer = lastAppVer;
	}

	public String getLastAPPShowVer() {
		return lastAPPShowVer;
	}

	public void setLastAPPShowVer(String lastAPPShowVer) {
		this.lastAPPShowVer = lastAPPShowVer;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public String getLastAppSize() {
		return lastAppSize;
	}

	public void setLastAppSize(String lastAppSize) {
		this.lastAppSize = lastAppSize;
	}

	public String getUpdateMd5() {
		return updateMd5;
	}

	public void setUpdateMd5(String updateMd5) {
		this.updateMd5 = updateMd5;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
