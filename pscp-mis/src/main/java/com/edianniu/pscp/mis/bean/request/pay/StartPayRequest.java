package com.edianniu.pscp.mis.bean.request.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 1002061)
public class StartPayRequest extends TerminalRequest{

	private Integer orderType;//1 充值2 工单支付3 工单结算||4 响应需求保证金5 项目结算
	/**
	 * 订单编号orderType=1时，orderIds留空
	 * orderType=2|3时，社会工单订单编号可以多个以逗号隔开
	 * orderType=4|5|6时，订单号可以多个，以逗号隔开
	 */
	private String orderIds;	
	public Integer getOrderType() {
		return orderType;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
