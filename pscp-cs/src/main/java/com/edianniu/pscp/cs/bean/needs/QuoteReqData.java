package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * 需求询价
 * @author zhoujianjian
 * 2017年9月15日上午00:07:55
 */
public class QuoteReqData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long uid;
	
	private String orderId;
	
	private String responsedOrderIds;

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

	public String getResponsedOrderIds() {
		return responsedOrderIds;
	}

	public void setResponsedOrderIds(String responsedOrderIds) {
		this.responsedOrderIds = responsedOrderIds;
	}
	
	
	
}
