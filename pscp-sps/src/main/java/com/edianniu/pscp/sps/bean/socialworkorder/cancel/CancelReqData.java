package com.edianniu.pscp.sps.bean.socialworkorder.cancel;

import java.io.Serializable;

/**
 * ClassName: CancelReqData
 * Author: tandingbo
 * CreateTime: 2017-05-21 15:26
 */
public class CancelReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会工单ID
     */
    private Long id;
    /**
     * 登录用户信息
     */
    private Long uid;
    /**
     * 社会工单编号
     */
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
