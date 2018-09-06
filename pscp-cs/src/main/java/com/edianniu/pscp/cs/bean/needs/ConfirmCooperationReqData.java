package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;
/**
 * 确认合作
 * @author zhoujianjian
 * 2017年9月15日下午1:01:59
 */
public class ConfirmCooperationReqData implements Serializable{

	private static final long serialVersionUID = 1L;
	//用户ID
	private Long uid;
	//需求编号
	private String orderId;
	//被选择的服务商的响应订单ID
	private String responsedOrderId;
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
	public String getResponsedOrderId() {
		return responsedOrderId;
	}
	public void setResponsedOrderId(String responsedOrderId) {
		this.responsedOrderId = responsedOrderId;
	}
	

}
