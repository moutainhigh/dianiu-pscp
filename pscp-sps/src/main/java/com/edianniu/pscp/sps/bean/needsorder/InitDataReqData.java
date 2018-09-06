package com.edianniu.pscp.sps.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: InitDataReqData
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:24
 */
public class InitDataReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;

    /* 需求响应订单编号(仅限用于需求勘察订单).*/
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
