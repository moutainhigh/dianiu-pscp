package com.edianniu.pscp.mis.bean.defectrecord;

import java.io.Serializable;

/**
 * ClassName: RemoveReqData
 * Author: tandingbo
 * CreateTime: 2017-09-13 09:54
 */
public class RemoveReqData implements Serializable {
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
