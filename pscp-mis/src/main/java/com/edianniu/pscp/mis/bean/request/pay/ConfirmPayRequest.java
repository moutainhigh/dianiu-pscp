package com.edianniu.pscp.mis.bean.request.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 1002060)
public class ConfirmPayRequest extends TerminalRequest{
	private String orderId;
	private String resultStatus;
	private String result;
	private Integer payType;
	private Integer orderType;
	public String getOrderId() {
		return orderId;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public String getResult() {
		return result;
	}
	public Integer getPayType() {
		return payType;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
