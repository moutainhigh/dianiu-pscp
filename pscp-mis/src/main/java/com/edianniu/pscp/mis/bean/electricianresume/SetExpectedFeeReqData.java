package com.edianniu.pscp.mis.bean.electricianresume;

import java.io.Serializable;

/**
 * ClassName: SetExpectedFeeReqData
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:17
 */
public class SetExpectedFeeReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 期望费用
     */
    private String expectedFee;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getExpectedFee() {
        return expectedFee;
    }

    public void setExpectedFee(String expectedFee) {
        this.expectedFee = expectedFee;
    }
}
