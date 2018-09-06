/**
 *
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * 微信退款查询 请求参数
 * @author AbnerElk
 */
public class WxPayRefundQueryReqData extends RefundQueryReqData{
	private static final long serialVersionUID = 1L;
	// 商户号
    private String mchId;
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
}
