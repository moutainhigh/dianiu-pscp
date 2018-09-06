package com.edianniu.pscp.sps.bean.socialworkorder.resume.VO;

import java.io.Serializable;

/**
 * ClassName: ResumeVO
 * Author: tandingbo
 * CreateTime: 2017-05-24 14:50
 */
public class ResumeVO implements Serializable {
    private static final long serialVersionUID = -670077396554119763L;

    /**
     * 所在城市
     */
    private String city;
    /**
     * 最高学历
     */
    private String diploma;
    /**
     * 工作年限
     */
    private Integer workingYears;
    /**
     * 期望费用
     */
    private String expectedFee;
    /**
     * 个人简介
     */
    private String synopsis;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public Integer getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }

    public String getExpectedFee() {
        return expectedFee;
    }

    public void setExpectedFee(String expectedFee) {
        this.expectedFee = expectedFee;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
