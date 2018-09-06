package com.edianniu.pscp.sps.bean.customer;

import java.io.Serializable;

/**
 * ClassName: DetailsReqData
 * Author: tandingbo
 * CreateTime: 2017-08-18 11:34
 */
public class DetailsReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long customerId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
