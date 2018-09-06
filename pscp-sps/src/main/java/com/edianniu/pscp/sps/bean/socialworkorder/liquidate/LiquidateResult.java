package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import com.edianniu.pscp.sps.bean.Result;

/**
 * ClassName: LiquidateResult
 * Author: tandingbo
 * CreateTime: 2017-05-25 11:24
 */
public class LiquidateResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 冻结金额支付状态：
     * success:支付成功
     * failure:支付失败
     */
    private String status;
    /**
     * 冻结金额
     */
    private String freezingAmount;
    /**
     * 支付金额
     */
    private String paymentAmount;
    /**
     *
     */
    private String orderId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
