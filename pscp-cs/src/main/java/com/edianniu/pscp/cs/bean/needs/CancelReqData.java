package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * 取消需求
 * @author zhoujianjian
 * 2017年9月14日下午10:41:17
 */
public class CancelReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	
	private String orderId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
