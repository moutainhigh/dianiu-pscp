package com.edianniu.pscp.mis.bean.request.electricianresume;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: SetExpectedFeeRequest
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:19
 */
@JSONMessage(messageCode = 1002053)
public final class SetExpectedFeeRequest extends TerminalRequest {

    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 期望费用
     */
    private String expectedFee;

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
