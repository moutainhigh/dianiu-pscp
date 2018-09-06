package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import java.io.Serializable;

/**
 * ClassName: LiquidateDetailsBackgroundReqData
 * Author: tandingbo
 * CreateTime: 2017-07-20 17:57
 */
public class LiquidateDetailsBackgroundReqData implements Serializable {
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
