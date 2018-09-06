package com.edianniu.pscp.mis.bean.electricianresume;

import java.io.Serializable;

/**
 * ClassName: UpdateReqData
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:34
 */
public class UpdateReqData implements Serializable {
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
}
