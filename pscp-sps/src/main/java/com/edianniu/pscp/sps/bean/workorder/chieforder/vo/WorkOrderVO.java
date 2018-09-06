package com.edianniu.pscp.sps.bean.workorder.chieforder.vo;

import java.io.Serializable;

/**
 * ClassName: WorkOrderVO
 * Author: tandingbo
 * CreateTime: 2017-05-16 15:04
 */
public class WorkOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 工单名称
     */
    private String name;
    /**
     * 工单编号
     */
    private String orderId;
    /**
     * 工单状态
     */
    private Integer status;
    /**
     * 工单详情
     */
    private String content;
    /**
     * 检修设备名称
     */
    private String devices;
    /**
     * 工作地点
     */
    private String address;
    /**
     * 检修时间
     */
    private String startTime;
    /**
     * 检修时间
     */
    private String endTime;
    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 是否招募社会电工
     */
    private Integer needSocialElectrician;
    /**
     * 危险有害因数辨识
     */
    private String hazardFactor;
    /**
     * 安全措施
     */
    private String safetyMeasures;
    /**
     * 携带机械或设备
     */
    private String carryingTools;
    /**
     * 预支费用(社会电工招募总费用)
     */
    private Double totalFee = 0D;
    /**
     * 安全措施-自定义内容
     */
    private String safetyMeasuresOther;
    /**
     * 危险有害因数-自定义内容
     */
    private String hazardFactorOther;
    /**
     * 发布时间(yyyy-mm-dd)
     */
    private String publishTime;

    private String latitude;
    private String longitude;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 工单类型名称
     */
    private String typeName;
    /**
     * 工单类型
     */
    private Integer type;

    /**
     * 项目负责人ID
     */
    private Long projectLeaderId;
    /**
     * 项目负责人
     */
    private String projectLeader;
    /**
     * 项目负责人联系电话
     */
    private String contactTel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getNeedSocialElectrician() {
        return needSocialElectrician;
    }

    public void setNeedSocialElectrician(Integer needSocialElectrician) {
        this.needSocialElectrician = needSocialElectrician;
    }

    public String getHazardFactor() {
        return hazardFactor;
    }

    public void setHazardFactor(String hazardFactor) {
        this.hazardFactor = hazardFactor;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getCarryingTools() {
        return carryingTools;
    }

    public void setCarryingTools(String carryingTools) {
        this.carryingTools = carryingTools;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
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

    public String getSafetyMeasuresOther() {
        return safetyMeasuresOther;
    }

    public void setSafetyMeasuresOther(String safetyMeasuresOther) {
        this.safetyMeasuresOther = safetyMeasuresOther;
    }

    public String getHazardFactorOther() {
        return hazardFactorOther;
    }

    public void setHazardFactorOther(String hazardFactorOther) {
        this.hazardFactorOther = hazardFactorOther;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(Long projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}
