package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: ConfirmReqData
 * Author: tandingbo
 * CreateTime: 2017-04-16 16:27
 */
public class ConfirmReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long uid;
    /**
     * 电工工单ID
     */
    private String orderId;

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
}
