/**
 *
 */
package com.edianniu.pscp.mis.bean.pay;

import org.apache.commons.lang3.StringUtils;

import com.edianniu.pscp.mis.bean.Result;

/**
 * 微信退款查询 返回
 *
 * @author AbnerELk
 */
public class AliPayRefundQueryRespData extends Result {
	private static final long serialVersionUID = 1;
    private String tradeNo;
    private String outTradeNo;//": "20150320010101001",
    private String outRefundNo;//": "20150320010101001",
    private String refundReason;//": "用户退款请求",
    private String totalAmount;//": 100.2,
    private String refundAmount;//": 12.33
    /**
     * 退款是否成功
     * @return
     */
    public boolean isRefundSuccess(){
    	if(StringUtils.isNoneBlank(tradeNo)&&
    	   StringUtils.isNoneBlank(outTradeNo)&&
    	   StringUtils.isNoneBlank(outRefundNo)&&
    	   StringUtils.isNoneBlank(totalAmount)&&
    	   StringUtils.isNoneBlank(refundAmount)){
    		return true;
    	}
    	return false;
    }
	public String getTradeNo() {
		return tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	
	public String getRefundReason() {
		return refundReason;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
}
