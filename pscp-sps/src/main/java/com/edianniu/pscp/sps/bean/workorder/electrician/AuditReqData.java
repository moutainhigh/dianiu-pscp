package com.edianniu.pscp.sps.bean.workorder.electrician;

import java.io.Serializable;

/**
 * ClassName: AuditReqData
 * Author: tandingbo
 * CreateTime: 2017-05-21 16:22
 */
public class AuditReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社会电工工单ID
     */
    private Long id;
    /**
     * 审核状态(pass：审核通过，fail:审核不通过)
     */
    private String auditStatus;
    /**
     * 审核不通过原因
     */
    private String failureReason;

    private Long uid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
