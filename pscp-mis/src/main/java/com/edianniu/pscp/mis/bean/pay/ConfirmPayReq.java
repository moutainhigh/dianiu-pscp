/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import com.edianniu.pscp.mis.bean.user.BaseTerminalData;

/**
 * @author cyl
 *
 */
public class ConfirmPayReq extends BaseTerminalData{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private String orderId;
	private String resultStatus;
	private String result;
	private Integer payType;
	private Integer orderType;
	private String payMethod="APP";//支付方式
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
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}
