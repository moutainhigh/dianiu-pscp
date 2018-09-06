package com.edianniu.pscp.sps.bean.socialworkorder.electrician;

import java.io.Serializable;

/**
 * ClassName: ElectricianReqData
 * Author: tandingbo
 * CreateTime: 2017-06-29 15:50
 */
public class ElectricianReqData implements Serializable {
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
