package com.edianniu.pscp.cs.bean.needsorder;

import com.edianniu.pscp.cs.bean.Result;

/**
 * ClassName: SaveResult
 * Author: tandingbo
 * CreateTime: 2017-09-25 10:34
 */
public class SaveResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 服务商需求响应订单编号.*/
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
