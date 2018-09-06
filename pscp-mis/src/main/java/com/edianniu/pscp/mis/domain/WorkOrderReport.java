package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单报告
 */
public class WorkOrderReport extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long workOrderId;
    private Long roomId;
    //$column.comments
    private Integer type;
    //$column.comments
    private String workOrderName;
    //$column.comments
    private String projectName;
    //$column.comments
    private String deviceName;
    //$column.comments
    private String workContent;
    //$column.comments
    private Date workDate;
    //$column.comments
    private String leadingOfficial;
    //$column.comments
    private String companyName;
    //$column.comments
    private String contactNumber;
    //$column.comments
    private String receiver;
    //$column.comments
    private String remark;
    
    private List<CommonAttachment> attachmentList;

    /**
     * 设置：${column.comments}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getWorkOrderId() {
        return workOrderId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * 设置：${column.comments}
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getWorkOrderName() {
        return workOrderName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    /**
     * 获取：${column.comments}
     */
    public String getWorkContent() {
        return workContent;
    }

    /**
     * 设置：${column.comments}
     */
    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getWorkDate() {
        return workDate;
    }

    /**
     * 设置：${column.comments}
     */
    public void setLeadingOfficial(String leadingOfficial) {
        this.leadingOfficial = leadingOfficial;
    }

    /**
     * 获取：${column.comments}
     */
    public String getLeadingOfficial() {
        return leadingOfficial;
    }

    /**
     * 设置：${column.comments}
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置：${column.comments}
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取：${column.comments}
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置：${column.comments}
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取：${column.comments}
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置：${column.comments}
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：${column.comments}
     */
    public String getRemark() {
        return remark;
    }

	public List<CommonAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<CommonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

}
