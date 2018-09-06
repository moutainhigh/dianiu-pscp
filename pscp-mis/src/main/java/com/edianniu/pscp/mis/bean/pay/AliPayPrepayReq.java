package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

public class AliPayPrepayReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private String payMethod;
	private String orderId;
	private String totalAmount;
	private String subject;
	private String body;
	private String returnUrl;
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
    
}
