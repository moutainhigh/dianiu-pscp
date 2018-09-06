package com.edianniu.pscp.mis.bean.defectrecord.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DefectRecordDetailsVO
 * Author: tandingbo
 * CreateTime: 2017-09-12 17:01
 */
public class DefectRecordDetailsVO implements Serializable {
    private static final long serialVersionUID = -8067042941526721116L;

    private Long id;
    private String deviceName;
    private String defectContent;
    private Integer status;
    private String discoveryDate;
    private String discoverer;
    private String discoveryCompany;
    private String contactNumber;
    private String remark;

    private String solver;// 解决人
    private String solveDate;// 解决日期
    private String solveRemark;// 修复备注
    private String roomName;// 配电房名称
    private List<Map<String, Object>> attachmentList;  // 缺陷记录附件
    private List<Map<String, Object>> solveAttachmentList; // 整改附件

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

    public String getDefectContent() {
        return defectContent;
    }

    public void setDefectContent(String defectContent) {
        this.defectContent = defectContent;
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

    public String getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    public String getDiscoveryCompany() {
        return discoveryCompany;
    }

    public void setDiscoveryCompany(String discoveryCompany) {
        this.discoveryCompany = discoveryCompany;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(String solveDate) {
        this.solveDate = solveDate;
    }

    public String getSolveRemark() {
        return solveRemark;
    }

    public void setSolveRemark(String solveRemark) {
        this.solveRemark = solveRemark;
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

	public List<Map<String, Object>> getSolveAttachmentList() {
		return solveAttachmentList;
	}

	public void setSolveAttachmentList(List<Map<String, Object>> solveAttachmentList) {
		this.solveAttachmentList = solveAttachmentList;
	}
    
    
}
