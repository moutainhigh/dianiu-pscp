package com.edianniu.pscp.mis.bean.electricianresume;

import com.edianniu.pscp.mis.bean.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DetailResult
 * Author: tandingbo
 * CreateTime: 2017-04-19 16:26
 */
public class DetailResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 基本信息
     */
    private Map<String, Object> essentialInfo;
    /**
     * 工作经历
     */
    private List<Map<String, Object>> experiences;
    /**
     * 期望费用
     */
    private String expectedFee;
    /**
     * 个人简介
     */
    private String synopsis;

    /**
     * 证书信息
     */
    private List<Map<String, Object>> certificateList;
    /**
     * 证书图片信息
     */
    private List<Map<String, Object>> certificateImageList;
    /**
     * 电工资质
     */
    private List<Map<String, Object>> qualificationsList;

    public Map<String, Object> getEssentialInfo() {
        return essentialInfo;
    }

    public void setEssentialInfo(Map<String, Object> essentialInfo) {
        this.essentialInfo = essentialInfo;
    }

    public List<Map<String, Object>> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Map<String, Object>> experiences) {
        this.experiences = experiences;
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

    public List<Map<String, Object>> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Map<String, Object>> certificateList) {
        this.certificateList = certificateList;
    }

    public List<Map<String, Object>> getCertificateImageList() {
        return certificateImageList;
    }

    public void setCertificateImageList(List<Map<String, Object>> certificateImageList) {
        this.certificateImageList = certificateImageList;
    }

    public List<Map<String, Object>> getQualificationsList() {
        return qualificationsList;
    }

    public void setQualificationsList(List<Map<String, Object>> qualificationsList) {
        this.qualificationsList = qualificationsList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
