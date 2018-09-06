package com.edianniu.pscp.mis.bean.response.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.pay.PayOrderInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
@JSONMessage(messageCode = 2002201)
public class QueryResultPayResponse  extends BaseResponse {
	private PayOrderInfo orderInfo;
	public PayOrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(PayOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
