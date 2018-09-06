package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: StartWorkReqData
 * Author: tandingbo
 * CreateTime: 2017-04-16 17:23
 */
public class StartWorkReqData implements Serializable {
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
     * 开始时间
     * yyyy-MM-dd hh:MM:ss
     * 如果用户是工单负责人，需要填写
     */
    private String startTime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
