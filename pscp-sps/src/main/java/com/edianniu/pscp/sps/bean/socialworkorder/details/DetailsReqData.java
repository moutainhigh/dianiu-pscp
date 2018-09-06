package com.edianniu.pscp.sps.bean.socialworkorder.details;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-05-21 17:43
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long uid;
    private String orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
