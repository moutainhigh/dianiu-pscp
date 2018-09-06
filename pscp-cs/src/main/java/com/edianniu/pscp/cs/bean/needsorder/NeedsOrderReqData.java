package com.edianniu.pscp.cs.bean.needsorder;

import java.io.Serializable;

/**
 * ClassName: NeedsOrderReqData
 * Author: tandingbo
 * CreateTime: 2017-09-26 16:47
 */
public class NeedsOrderReqData implements Serializable {
    private static final long serialVersionUID = 4746566605299308952L;
    /* 当前登录用户ID.*/
    private Long uid;
    /* 当前登录用户公司ID(可为空).*/
    private Long companyId;

    /* 响应订单ID.*/
    private Long id;
    /* 响应订单编号.*/
    private String orderId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
