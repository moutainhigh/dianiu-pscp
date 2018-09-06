package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

public class CheckAppUpdateResult extends Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long lastAppVer;
	private String lastAPPShowVer;
	private String lastAppSize;
	private String updateDesc;
	private Integer updateType;
	private String updateUrl;
	private String updateMd5;

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
}
