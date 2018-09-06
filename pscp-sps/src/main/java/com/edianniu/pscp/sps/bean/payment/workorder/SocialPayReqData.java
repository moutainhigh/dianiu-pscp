package com.edianniu.pscp.sps.bean.payment.workorder;

import java.io.Serializable;

/**
 * ClassName: SocialPayReqData
 * Author: tandingbo
 * CreateTime: 2017-06-01 16:18
 */
public class SocialPayReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会工单ID
     */
    private Long id;
    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 支付类型
     */
    private Integer payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
