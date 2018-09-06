package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求询价
 * @author zhoujianjian
 * 2017年9月14日下午11:53:25
 */
@JSONMessage(messageCode = 1002127)
public class QuoteRequest extends TerminalRequest{
	
	//需求编号
	private String orderId;
	//被选择询价的服务商响应的订单ID
	private String responsedOrderIds;
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
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}
}
