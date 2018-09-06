package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单缺陷记录
 */
public class WorkOrderDefectRecord extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //$column.comments
    private Long id;
    //$column.comments
    private Long projectId;
    //$column.comments
    private Long workOrderId;
    //$column.comments
    private Integer status;
    //$column.comments
    private String deviceName;
    //$column.comments
    private String defectContent;
    //$column.comments
    private Date discoveryDate;
    //$column.comments
    private String discoverer;
    //$column.comments
    private String discoveryCompany;
    //$column.comments
    private String discoveryOrderName;
    //$column.comments
    private String contactNumber;
    //$column.comments
    private String solver;
    //$column.comments
    private Long solveOrderId;
    //$column.comments
    private String remark;

    private Long roomId;
    private Date solveDate;
    private String solveRemark;
    private List<CommonAttachment> attachmentList;
    private List<CommonAttachment> solveAttachmentList;

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
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getProjectId() {
        return projectId;
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

    /**
     * 设置：${column.comments}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：${column.comments}
     */
    public Integer getStatus() {
        return status;
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
    public void setDefectContent(String defectContent) {
        this.defectContent = defectContent;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDefectContent() {
        return defectContent;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDiscoveryDate(Date discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    /**
     * 获取：${column.comments}
     */
    public Date getDiscoveryDate() {
        return discoveryDate;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDiscoverer() {
        return discoverer;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDiscoveryCompany(String discoveryCompany) {
        this.discoveryCompany = discoveryCompany;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDiscoveryCompany() {
        return discoveryCompany;
    }

    /**
     * 设置：${column.comments}
     */
    public void setDiscoveryOrderName(String discoveryOrderName) {
        this.discoveryOrderName = discoveryOrderName;
    }

    /**
     * 获取：${column.comments}
     */
    public String getDiscoveryOrderName() {
        return discoveryOrderName;
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
    public void setSolver(String solver) {
        this.solver = solver;
    }

    /**
     * 获取：${column.comments}
     */
    public String getSolver() {
        return solver;
    }

    /**
     * 设置：${column.comments}
     */
    public void setSolveOrderId(Long solveOrderId) {
        this.solveOrderId = solveOrderId;
    }

    /**
     * 获取：${column.comments}
     */
    public Long getSolveOrderId() {
        return solveOrderId;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(Date solveDate) {
        this.solveDate = solveDate;
    }

    public String getSolveRemark() {
        return solveRemark;
    }

    public void setSolveRemark(String solveRemark) {
        this.solveRemark = solveRemark;
    }

	public List<CommonAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<CommonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<CommonAttachment> getSolveAttachmentList() {
		return solveAttachmentList;
	}

	public void setSolveAttachmentList(List<CommonAttachment> solveAttachmentList) {
		this.solveAttachmentList = solveAttachmentList;
	}
    
    
}
