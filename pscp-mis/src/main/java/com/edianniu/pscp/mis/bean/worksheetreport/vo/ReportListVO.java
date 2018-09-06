package com.edianniu.pscp.mis.bean.worksheetreport.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ReportListVO
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:07
 */
public class ReportListVO implements Serializable {
    private static final long serialVersionUID = -4376539845662974849L;

    private Long id;
    /* 设备名称.*/
    private String deviceName;
    /* 工作内容.*/
    private String workContent;
    /* 工作日期(yyyy-mm-dd).*/
    private String workDate;
    /* 工单名称.*/
    private String workOrderName;
    /* 项目名称.*/
    private String projectName;
    /* 配电名称.*/
    private String roomName;

    private List<Map<String, Object>> attachmentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
