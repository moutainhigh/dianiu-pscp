package com.edianniu.pscp.sps.bean.workorder.evaluate;

import java.io.Serializable;

/**
 * ClassName: ListReqData
 * Author: tandingbo
 * CreateTime: 2017-05-15 15:25
 */
public class ListReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long workOrderId;
    private String orderId;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
