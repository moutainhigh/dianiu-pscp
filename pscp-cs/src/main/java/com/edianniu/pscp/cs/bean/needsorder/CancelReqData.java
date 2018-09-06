package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: CancelReqData
 * Author: tandingbo
 * CreateTime: 2017-09-25 15:25
 */
public class CancelReqData implements Serializable {
    private static final long serialVersionUID = -7916801337430688454L;

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
