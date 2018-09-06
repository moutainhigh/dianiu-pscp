package com.edianniu.pscp.mis.bean.defectrecord.vo;

import java.io.Serializable;

/**
 * ClassName: DefectRecordVO
 * Author: tandingbo
 * CreateTime: 2017-09-12 11:24
 */
public class DefectRecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 缺陷ID.*/
    private Long id;
    /* 项目名称.*/
    private String projectName;
    /* 工单名称.*/
    private String workOrderName;
    /* 设备名称.*/
    private String deviceName;
    /* 状态.*/
    private Integer status;
    /* 发现日期.*/
    private String discoveryDate;
    /* 配电房名称.*/
    private String roomName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
