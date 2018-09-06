package com.edianniu.pscp.mis.bean.workexperience;

import java.io.Serializable;

/**
 * ClassName: DelWorkExperienceReqData
 * Author: tandingbo
 * CreateTime: 2017-04-19 15:44
 */
public class DelWorkExperienceReqData implements Serializable {

    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 简历ID
     */
    private Long resumeId;
    /**
     * 工作经历ID
     */
    private Long experienceId;

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

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }
}
