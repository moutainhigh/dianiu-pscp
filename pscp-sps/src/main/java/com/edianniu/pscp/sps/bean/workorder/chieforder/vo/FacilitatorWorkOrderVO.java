package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

/**
 * ClassName: FacilitatorWorkOrderVO
 * Author: tandingbo
 * CreateTime: 2017-07-26 17:38
 */
public class FacilitatorWorkOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 工单ID.*/
    private Long id;
    /* 工单编号.*/
    private String orderId;
    /* 工单名称.*/
    private String name;
    /* 工作时间(yyyy-mm-dd~yyyy-mm-dd).*/
    private String workTime;
    /* 工单描述.*/
    private String content;
    /* 工作地点.*/
    private String address;
    /* 检修项目.*/
    private String checkOption;
    /* 工单发布时间.*/
    private String publishTime;
    /* 状态.*/
    private Integer status;
    /* 项目名称.*/
    private String projectName;
    /* 项目负责人.*/
    private String projectLeader;
    /* 项目负责人ID.*/
    private Long projectLeaderId;
    /* 项目负责人联系电话.*/
    private String contactTel;
    /* 经度.*/
    private String latitude;
    /* 纬度.*/
    private String longitude;
    /* 安全措施.*/
    private String safetyMeasures;
    /* 安全措施-自定义内容.*/
    private String safetyMeasuresOther;
    /* 危险有害因数.*/
    private String hazardFactor;
    /* 危险有害因数-自定义内容.*/
    private String hazardFactorOther;
    /* 携带机械或设备.*/
    private String carryingTools;
    /**
     * 工单类型
     */
    private Integer type;
    /* 工单类型名称.*/
    private String typeName;
    /* 实际工单开始时间(yyyy-mm-dd).*/
    private String actualStartTime;
    /* 实际工单结束时间(yyyy-mm-dd).*/
    private String actualEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Long getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(Long projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public String getHazardFactor() {
        return hazardFactor;
    }

    public void setHazardFactor(String hazardFactor) {
        this.hazardFactor = hazardFactor;
    }

    public String getHazardFactorOther() {
        return hazardFactorOther;
    }

    public void setHazardFactorOther(String hazardFactorOther) {
        this.hazardFactorOther = hazardFactorOther;
    }

    public String getCarryingTools() {
        return carryingTools;
    }

    public void setCarryingTools(String carryingTools) {
        this.carryingTools = carryingTools;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
}
