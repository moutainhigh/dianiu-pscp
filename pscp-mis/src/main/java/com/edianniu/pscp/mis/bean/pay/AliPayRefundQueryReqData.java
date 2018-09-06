/**
 *
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * 
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 下午12:07:16 
 * @version V1.0
 */
public class AliPayRefundQueryReqData extends RefundQueryReqData{
	private static final long serialVersionUID = 1L;
	private String tradeNo;
	private String outTradeNo;
	private String outRefundNo;
	public String getTradeNo() {
		return tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
}
