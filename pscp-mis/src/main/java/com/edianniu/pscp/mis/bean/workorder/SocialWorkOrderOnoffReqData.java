package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

public class SocialWorkOrderOnoffReqData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long uid;
	private String status;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
