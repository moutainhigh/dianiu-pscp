package com.edianniu.pscp.mis.bean.response.pay;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.pay.PayTypeInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
@JSONMessage(messageCode = 2002061)
public class StartPayResponse  extends BaseResponse {
	private String orderId;//支付订单编号
	private String orderContent;//支付订单内容
	private String payAmount;//支付金额
	private String walletAmount;//钱包余额
	private List<PayTypeInfo> payTypeInfos;
	
	public String getPayAmount() {
		return payAmount;
	}
	public String getWalletAmount() {
		return walletAmount;
	}
	
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public void setWalletAmount(String walletAmount) {
		this.walletAmount = walletAmount;
	}
	
	public List<PayTypeInfo> getPayTypeInfos() {
		return payTypeInfos;
	}
	public void setPayTypeInfos(List<PayTypeInfo> payTypeInfos) {
		this.payTypeInfos = payTypeInfos;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getOrderContent() {
		return orderContent;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
