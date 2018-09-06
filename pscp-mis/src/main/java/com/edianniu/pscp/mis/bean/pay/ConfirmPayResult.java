package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.Result;

public class ConfirmPayResult  extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private String syncPayStatus;
	private String extendParams;
	public String getSyncPayStatus() {
		return syncPayStatus;
	}
	public void setSyncPayStatus(String syncPayStatus) {
		this.syncPayStatus = syncPayStatus;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
}
