package com.edianniu.pscp.cs.bean.needs;

import java.io.Serializable;

/**
 * ClassName: NeedsMarketDetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-09-21 15:12
 */
public class NeedsMarketDetailsReqData implements Serializable {
    private static final long serialVersionUID = 7699528308783932074L;

    /* 登录.*/
    private Long uid;
    /* 需求编号.*/
    private String orderId;
    /* 服务商响应订单编号.*/
    private String responsedOrderId;

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

    public String getResponsedOrderId() {
        return responsedOrderId;
    }

    public void setResponsedOrderId(String responsedOrderId) {
        this.responsedOrderId = responsedOrderId;
    }
}
