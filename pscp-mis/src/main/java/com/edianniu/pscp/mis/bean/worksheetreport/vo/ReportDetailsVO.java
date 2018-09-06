package com.edianniu.pscp.mis.bean.worksheetreport.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ReportDetailsVO
 * Author: tandingbo
 * CreateTime: 2017-09-13 11:11
 */
public class ReportDetailsVO implements Serializable {
    private static final long serialVersionUID = -8438479142899511359L;

    private Long id;
    /* 设备名称.*/
    private String deviceName;
    /* 配电房名称.*/
    private String roomName;
    /* 工作内容.*/
    private String workContent;
    /* 工作日期(yyyy-mm-dd).*/
    private String workDate;
    /* 工作负责人.*/
    private String leadingOfficial;
    /* 单位名称.*/
    private String companyName;
    /* 联系电话.*/
    private String contactNumber;
    /* 验收人.*/
    private String receiver;
    /* 备注.*/
    private String remark;
    /**
     * 报告附件
     */
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getLeadingOfficial() {
        return leadingOfficial;
    }

    public void setLeadingOfficial(String leadingOfficial) {
        this.leadingOfficial = leadingOfficial;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Map<String, Object>> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Map<String, Object>> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
