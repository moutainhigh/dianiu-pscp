package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: SettlementReqData
 * Author: tandingbo
 * CreateTime: 2017-05-10 15:55
 */
public class SettlementReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long uid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 支付总额
     */
    private String amount;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
