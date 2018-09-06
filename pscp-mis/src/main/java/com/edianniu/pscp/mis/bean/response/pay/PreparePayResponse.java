package com.edianniu.pscp.mis.bean.response.pay;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.json.annotation.JSONMessage;

import com.edianniu.pscp.mis.bean.pay.AlipayPrepayInfo;
import com.edianniu.pscp.mis.bean.pay.UnionPayInfo;
import com.edianniu.pscp.mis.bean.pay.WalletPayInfo;
import com.edianniu.pscp.mis.bean.pay.WxpayPrepayInfo;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
@JSONMessage(messageCode = 1002059)
public class PreparePayResponse  extends BaseResponse {
	private AlipayPrepayInfo alipay;
	private WxpayPrepayInfo weixinpay;
	private UnionPayInfo unionpay;
	private WalletPayInfo  walletpay;
	private String orderId;
	private String extendParams;
	public AlipayPrepayInfo getAlipay() {
		return alipay;
	}
	public WxpayPrepayInfo getWeixinpay() {
		return weixinpay;
	}
	public UnionPayInfo getUnionpay() {
		return unionpay;
	}
	public WalletPayInfo getWalletpay() {
		return walletpay;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setAlipay(AlipayPrepayInfo alipay) {
		this.alipay = alipay;
	}
	public void setWeixinpay(WxpayPrepayInfo weixinpay) {
		this.weixinpay = weixinpay;
	}
	public void setUnionpay(UnionPayInfo unionpay) {
		this.unionpay = unionpay;
	}
	public void setWalletpay(WalletPayInfo walletpay) {
		this.walletpay = walletpay;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
