package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 确认合作
 * @author zhoujianjian
 * 2017年9月15日上午11:44:49
 */
@JSONMessage(messageCode = 1002128)
public class ConfirmCooperationRequest extends TerminalRequest{
	
	//需求编号
	private String orderId;
	//被选择的服务商的响应订单ID
	private String responsedOrderId;

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
	
	public String ToString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
