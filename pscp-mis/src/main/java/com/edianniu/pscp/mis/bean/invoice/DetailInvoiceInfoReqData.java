package com.edianniu.pscp.mis.bean.invoice;

import java.io.Serializable;

/**
 * ClassName: ListMessageReqData
 * Author: tandingbo
 * CreateTime: 2017-04-18 10:45
 */
public class DetailInvoiceInfoReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
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
