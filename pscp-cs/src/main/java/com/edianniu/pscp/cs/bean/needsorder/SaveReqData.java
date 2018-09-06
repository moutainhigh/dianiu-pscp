package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: SaveReqData
 * Author: tandingbo
 * CreateTime: 2017-09-25 10:35
 */
public class SaveReqData implements Serializable {
    private static final long serialVersionUID = -2021291946629427192L;

    /* 登录用户ID.*/
    private Long uid;
    /* 需求编号.*/
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
