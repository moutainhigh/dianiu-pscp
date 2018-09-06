package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: EndWorkReqData
 * Author: tandingbo
 * CreateTime: 2017-04-16 18:09
 */
public class EndWorkReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电工ID
     */
    private Long uid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 结束时间
     */
    private String endTime;

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
