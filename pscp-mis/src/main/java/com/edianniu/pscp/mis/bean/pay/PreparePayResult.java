package com.edianniu.pscp.mis.bean.pay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.Result;



public class PreparePayResult  extends Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private AlipayPrepayInfo alipay;
	private WxpayPrepayInfo weixinpay;
	private UnionPayInfo unionpay;
	private WalletPayInfo  walletpay;
	private String orderId;
	private String extendParams;
	public AlipayPrepayInfo getAlipay() {
		return alipay;
	}
	public void setAlipay(AlipayPrepayInfo alipay) {
		this.alipay = alipay;
	}
	public WxpayPrepayInfo getWeixinpay() {
		return weixinpay;
	}
	public void setWeixinpay(WxpayPrepayInfo weixinpay) {
		this.weixinpay = weixinpay;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public UnionPayInfo getUnionpay() {
		return unionpay;
	}
	public WalletPayInfo getWalletpay() {
		return walletpay;
	}
	public void setUnionpay(UnionPayInfo unionpay) {
		this.unionpay = unionpay;
	}
	public void setWalletpay(WalletPayInfo walletpay) {
		this.walletpay = walletpay;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
}
