package com.edianniu.pscp.mis.bean.request.workexperience;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: DelWorkExperienceRequest
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:42
 */
@JSONMessage(messageCode = 1002055)
public class DelWorkExperienceRequest extends TerminalRequest {

    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 工作经历ID
     */
    private Long experienceId;

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
