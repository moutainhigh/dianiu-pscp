package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: InitDataReqData
 * Author: tandingbo
 * CreateTime: 2017-10-11 15:48
 */
public class InitDataReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 用户ID.*/
    private Long uid;
    /* 需求订单编号.*/
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
