package com.edianniu.pscp.sps.bean.socialworkorder.liquidate;

import java.io.Serializable;

/**
 * ClassName: LiquidateDetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-06-30 10:42
 */
public class LiquidateDetailsReqData implements Serializable {
    private static final long serialVersionUID = -6371864213935513224L;

    private Long uid;
    private String orderId;
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
