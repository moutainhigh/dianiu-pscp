package com.edianniu.pscp.mis.bean.request.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 1002059)
public class PreparePayRequest extends TerminalRequest{
	private String orderId;
	private Integer needInvoice=0;
	private String orderIds;//订单编号 逗号隔开 
	private String amount;//支付金额
	private Integer payType;//支付方式0 余额1 支付宝2 微信支付3 银联支付
	private Integer orderType;//1 充值 2 社会工单支付3 社会工单结算4 响应需求订单缴纳保证金5 项目结算支付6 电费结算
	private String extendParams;//扩展参数
	private String returnUrl;//h5/pc支付支付返回url
	public String getOrderIds() {
		return orderIds;
	}
	public String getAmount() {
		return amount;
	}
	public Integer getPayType() {
		return payType;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public String getOrderId() {
		return orderId;
	}
	public Integer getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
