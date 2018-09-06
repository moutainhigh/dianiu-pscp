package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * 服务商资质详情
 * @author zhoujianjian
 * 2017年9月18日下午9:16:15
 */
public class RespondDetailsReqData implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long uid;
	//服务商响应订单号
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
