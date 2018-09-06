package com.edianniu.pscp.cs.bean.workorder;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-08-08 12:02
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

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
