/**
 * 
 */
package com.edianniu.pscp.mis.bean.pay;

/**
 * @author cyl
 *
 */
public class WxPayRefundReqData  extends RefundReqData{
	private static final long serialVersionUID = 1L;
	private String mchId;
	private String refundFeeType;
	public String getMchId() {
		return mchId;
	}
	public String getRefundFeeType() {
		return refundFeeType;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}
	
}
