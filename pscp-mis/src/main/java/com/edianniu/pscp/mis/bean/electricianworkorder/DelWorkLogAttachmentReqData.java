package com.edianniu.pscp.mis.bean.electricianworkorder;

import java.io.Serializable;

/**
 * ClassName: DelWorkLogAttachmentReqData
 * Author: tandingbo
 * CreateTime: 2017-04-17 10:58
 */
public class DelWorkLogAttachmentReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 工作记录附件ID
     */
    private Long workLogAttachmentId;

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

    public Long getWorkLogAttachmentId() {
        return workLogAttachmentId;
    }

    public void setWorkLogAttachmentId(Long workLogAttachmentId) {
        this.workLogAttachmentId = workLogAttachmentId;
    }
}
