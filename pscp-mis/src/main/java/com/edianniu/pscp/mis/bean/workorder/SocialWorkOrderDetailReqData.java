package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

public class SocialWorkOrderDetailReqData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long uid;
	private String orderId;
	public Long getUid() {
		return uid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
