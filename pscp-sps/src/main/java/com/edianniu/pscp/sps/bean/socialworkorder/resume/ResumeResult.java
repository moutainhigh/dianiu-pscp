package com.edianniu.pscp.sps.bean.socialworkorder.resume;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.VO.ResumeVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ResumeResult
 * Author: tandingbo
 * CreateTime: 2017-05-24 14:47
 */
public class ResumeResult extends Result {
    private static final long serialVersionUID = 1L;

    /**
     * 简历信息
     */
    private ResumeVO resumeInfo;
    /**
     * 工作经历
     */
    private List<Map<String, Object>> experiences;
    /**
     * 证书信息
     */
    private List<Map<String, Object>> certificateList;
    /**
     * 证书图片信息
     */
    private List<Map<String, Object>> certificateImageList;
    /**
     * 电工信息
     */
    private Map<String, Object> electricianInfo;
    /**
     * 电工资质
     */
    private List<Map<String, Object>> qualificationsList;

    public ResumeVO getResumeInfo() {
        return resumeInfo;
    }

    public void setResumeInfo(ResumeVO resumeInfo) {
        this.resumeInfo = resumeInfo;
    }

    public List<Map<String, Object>> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Map<String, Object>> experiences) {
        this.experiences = experiences;
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

    public Map<String, Object> getElectricianInfo() {
        return electricianInfo;
    }

    public void setElectricianInfo(Map<String, Object> electricianInfo) {
        this.electricianInfo = electricianInfo;
    }

    public List<Map<String, Object>> getQualificationsList() {
        return qualificationsList;
    }

    public void setQualificationsList(List<Map<String, Object>> qualificationsList) {
        this.qualificationsList = qualificationsList;
    }
}
