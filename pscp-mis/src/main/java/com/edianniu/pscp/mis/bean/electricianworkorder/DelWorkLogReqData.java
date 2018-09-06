package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: DelWorkLogReqData
 * Author: tandingbo
 * CreateTime: 2017-04-17 10:13
 */
public class DelWorkLogReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 日志ID
     */
    private Long workLogId;

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

    public Long getWorkLogId() {
        return workLogId;
    }

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }
}
