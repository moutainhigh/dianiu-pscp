package com.edianniu.pscp.mis.bean.pay;

import com.edianniu.pscp.mis.bean.Result;
/**
 * 200 正常支付
 * 201 全部重复支付
 * 202 部分重复支付
 * 203 重复通知
 * 204
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月6日 下午3:32:02 
 * @version V1.0
 */
public class CheckPayOrderResult  extends Result {
	private static final long serialVersionUID = 1L;
    private Double refundAmount=0.00D;
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
}
