package com.edianniu.pscp.cs.bean.request.needs;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.edianniu.pscp.cs.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * 需求详情
 * @author zhoujianjian
 * 2017年9月17日下午11:37:43
 */
@JSONMessage(messageCode = 1002125)
public class DetailsRequest extends TerminalRequest{
	//需求编号
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE);
	}

}
