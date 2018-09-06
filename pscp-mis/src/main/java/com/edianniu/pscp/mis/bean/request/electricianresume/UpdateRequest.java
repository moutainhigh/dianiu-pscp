package com.edianniu.pscp.mis.bean.request.electricianresume;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * ClassName: UpdateRequest
 * Author: tandingbo
 * CreateTime: 2017-04-19 17:10
 */
@JSONMessage(messageCode = 1002052)
public final class UpdateRequest extends TerminalRequest {

    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 最高学历
     */
    private Integer diploma;
    /**
     * 工作年限
     */
    private Integer workingYears;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 个人简介
     */
    private String synopsis;

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getDiploma() {
        return diploma;
    }

    public void setDiploma(Integer diploma) {
        this.diploma = diploma;
    }

    public Integer getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
