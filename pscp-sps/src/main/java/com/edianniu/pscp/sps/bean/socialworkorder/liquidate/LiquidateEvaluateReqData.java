package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import java.io.Serializable;

/**
 * ClassName: LiquidateEvaluateReqData
 * Author: tandingbo
 * CreateTime: 2017-08-09 11:47
 */
public class LiquidateEvaluateReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 登录用户ID.*/
    private Long uid;
    /* 电工工单编号.*/
    private String orderId;
    /* 电工ID.*/
    private Long electricianId;

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

    public Long getElectricianId() {
        return electricianId;
    }

    public void setElectricianId(Long electricianId) {
        this.electricianId = electricianId;
    }
}
