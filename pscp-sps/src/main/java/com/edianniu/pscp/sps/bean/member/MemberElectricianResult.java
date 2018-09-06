package com.edianniu.pscp.sps.bean.member;

import com.edianniu.pscp.sps.bean.Result;
import com.edianniu.pscp.sps.bean.socialworkorder.resume.VO.ResumeVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberElectricianResult
 * Author: tandingbo
 * CreateTime: 2017-08-21 15:28
 */
public class MemberElectricianResult extends Result {
    private static final long serialVersionUID = 1L;

    /* 用户登录名称.*/
    private String userName;
    /* 用户登录ID.*/
    private String userId;
    /* 角色（企业，社会）.*/
    private String role;
    /* 认证状态(未认证，审核中，认证成功，认证失败).*/
    private String certifiedStatus;
    /* 身份证号码(格式化).*/
    private String idNumber;
    /* 身份证正反面图片.*/
    private List<String> idImagesList;
    /* 电工证书.*/
    private List<String> certificateList;
    /* 电工资质.*/
    private List<Map<String, Object>> qualificationList;
    /* 电工姓名.*/
    private String ElectricianName;

    /* 公司名称.*/
    private String companyName;
    /* 公司地址.*/
    private String companyAddr;

    /* 电工简历.*/
    private ResumeVO resume;
    /* 工作经历.*/
    List<Map<String, Object>> experiences;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCertifiedStatus() {
        return certifiedStatus;
    }

    public void setCertifiedStatus(String certifiedStatus) {
        this.certifiedStatus = certifiedStatus;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public List<String> getIdImagesList() {
        return idImagesList;
    }

    public void setIdImagesList(List<String> idImagesList) {
        this.idImagesList = idImagesList;
    }

    public List<String> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<String> certificateList) {
        this.certificateList = certificateList;
    }

    public List<Map<String, Object>> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<Map<String, Object>> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public ResumeVO getResume() {
        return resume;
    }

    public void setResume(ResumeVO resume) {
        this.resume = resume;
    }

    public String getElectricianName() {
        return ElectricianName;
    }

    public void setElectricianName(String electricianName) {
        ElectricianName = electricianName;
    }

    public List<Map<String, Object>> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Map<String, Object>> experiences) {
        this.experiences = experiences;
    }
}
